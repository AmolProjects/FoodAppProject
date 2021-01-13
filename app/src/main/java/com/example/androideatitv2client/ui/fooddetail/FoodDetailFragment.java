package com.example.androideatitv2client.ui.fooddetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.andremion.counterfab.CounterFab;
import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.androideatitv2client.R;
import com.example.androideatitv2client.ui.comments.CommentFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import COMMON.Common;
import Model.CommentModel;
import Model.FoodModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class FoodDetailFragment extends Fragment {

    private FoodDetailViewModel mViewModel;
    private Unbinder unbinder;
    private android.app.AlertDialog waitingDialog;

    @BindView(R.id.img_food)
    ImageView img_food;
    @BindView(R.id.btn_cart)
    CounterFab btn_cart;
    @BindView(R.id.btn_rating)
    FloatingActionButton btn_rating;
    @BindView(R.id.food_name)
    TextView food_name;
    @BindView(R.id.food_description)
    TextView food_description;
    @BindView(R.id.food_price)
    TextView food_price;
    @BindView(R.id.number_button)
    ElegantNumberButton number_Button;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.btnShowComment)
    Button btnShowComment;


    @OnClick(R.id.btn_rating)
    void onRatingButtonClick()
    {
        showDialogRating();
    }

   @OnClick(R.id.btnShowComment)
   void onShowCommentButtonOnClick(){
       CommentFragment commentFragment=CommentFragment.getInstance();
       commentFragment.show(getActivity().getSupportFragmentManager(),"CommentFragment");
   }


    private void showDialogRating() {
        androidx.appcompat.app.AlertDialog.Builder builder=new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setMessage("Rating Food");
        builder.setTitle("Please Fill Information");

        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.layout_rating,null);

        RatingBar ratingBar =(RatingBar)itemView.findViewById(R.id.rating_bar);
        EditText edt_comment=(EditText)itemView.findViewById(R.id.edt_comment);

        builder.setView(itemView);

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();

        });

        builder.setPositiveButton("OK", (dialog, which) -> {
            CommentModel commentModel=new CommentModel();
            commentModel.setName(Common.currentUser.getName());
            commentModel.setUid(Common.currentUser.getUid());
            commentModel.setComment(edt_comment.getText().toString());
            commentModel.setRatingValue(ratingBar.getRating());
            Map<String,Object>serverTimeStamp=new HashMap<>();
            serverTimeStamp.put("timestamp", ServerValue.TIMESTAMP);
            commentModel.setCommentTimeStamp(serverTimeStamp);

            mViewModel.setCommentModel(commentModel);

        });

        AlertDialog dialog=builder.create();
        dialog.show();
    }


    public static FoodDetailFragment newInstance() {
        return new FoodDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel=
                ViewModelProviders.of(this).get(FoodDetailViewModel.class);
        View root= inflater.inflate(R.layout.food_detail_fragment, container, false);

        unbinder= ButterKnife.bind(this,root);
        initViews();


        mViewModel.getMutableLiveDataFood().observe(getViewLifecycleOwner(), FoodModel -> {
                displayInfo(FoodModel);
        });

        mViewModel.getMutableLiveDataComment().observe(getViewLifecycleOwner(),commentModel -> {
                submitRatingToFirebase(commentModel);
        });

        return root;
    }

    private void initViews() {
        waitingDialog=new SpotsDialog.Builder().setCancelable(false).setContext(getContext()).build();
    }

    private void submitRatingToFirebase(CommentModel commentModel) {
        waitingDialog.show();
        //First we will submit to Comments Ref
        FirebaseDatabase.getInstance()
                .getReference(Common.COMMENT_REF)
                .child(Common.selectedFood.getId())
                .push()
                .setValue(commentModel)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                    {
                        //After submit to commentRef we wil update value in foood

                        addRatingToFood(commentModel.getRatingValue());

                    }
                    else{
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                    waitingDialog.dismiss();
                });


    }

    private void addRatingToFood(float ratingValue) {
        FirebaseDatabase.getInstance()
                .getReference(Common.CATEGORY_REF)
                .child(Common.categorySelected.getMenu_id()) //Select category
        .child("foods") //select array list 'foods' of this category
        .child(Common.selectedFood.getKey())//bcz fooditem is array listso key is indexof array list
    .addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists())
            {
                FoodModel foodModel=dataSnapshot.getValue(FoodModel.class);
                foodModel.setKey(Common.selectedFood.getKey());

                //Apply rating
                if (foodModel.getRatingValue() ==null)
                    foodModel.setRatingValue(0d); //d= lowercase
                if (foodModel.getRatingCount() ==null)
                    foodModel.setRatingValue((double) 0l);

                double sumRating=foodModel.getRatingValue()+ratingValue;
               // double sumRating = (foodModel.getRatingValue() * foodModel.getRatingCount())+ratingValue;
               long ratingCount=foodModel.getRatingCount() +1;
                double result=sumRating/ratingCount;

                Map<String,Object>updateData=new HashMap<>();
                updateData.put("ratingValue",result);
                updateData.put("ratingCount",ratingCount);

                //update data in variable
                foodModel.setRatingValue(result);
                foodModel.setRatingCount(ratingCount);

                dataSnapshot.getRef()
                        .updateChildren(updateData)
                        .addOnCompleteListener(task -> {
                            waitingDialog.dismiss();
                            if (task.isSuccessful())
                            {
                                Toast.makeText(getContext(), "Thank You", Toast.LENGTH_SHORT).show();
                                Common.selectedFood=foodModel;
                                FoodDetailViewModel.setFoodModel(foodModel); //call referesh
                            }
                        });



            }
            else
            {
                waitingDialog.dismiss();
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
                waitingDialog.dismiss();
            Toast.makeText(getContext(), ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
    }

    private void displayInfo(FoodModel foodModel) {
        Glide.with(getContext()).load(foodModel.getImage()).into(img_food);
        food_name.setText(new StringBuilder(foodModel.getName()));
        food_description.setText(new StringBuilder(foodModel.getDescription()));
        food_price.setText(new StringBuilder(foodModel.getPrice().toString()));

        if (foodModel.getRatingValue() !=null)
        ratingBar.setRating(foodModel.getRatingValue().floatValue());

        ((AppCompatActivity)getActivity())
                .getSupportActionBar()
                .setTitle(Common.selectedFood.getName());

    }


}
