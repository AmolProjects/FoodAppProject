package com.example.androideatitv2client.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import COMMON.Common;
import CallBack.IBestDealCallBackListener;
import CallBack.IPopularCallbackListener;
import Model.BestDealModel;
import Model.PopularCategoryModel;

public class HomeViewModel extends ViewModel implements IPopularCallbackListener, IBestDealCallBackListener {
    private MutableLiveData<List<PopularCategoryModel>> popularList;
    private MutableLiveData<List<BestDealModel>> bestDealList;
    private MutableLiveData<String> messageError;
    private IPopularCallbackListener popularCallbackListener;
    private IBestDealCallBackListener bestDealCallBackListener;



    public HomeViewModel() {
        popularCallbackListener=this;
        bestDealCallBackListener=this;

    }

    public MutableLiveData<List<BestDealModel>> getBestDealList() {
        if (bestDealList == null)
        {
            bestDealList=new MutableLiveData<>();
            messageError=new MutableLiveData<>();
            loadBestDeal();
        }
        return bestDealList;
    }

    private void loadBestDeal() {
        List<BestDealModel>tempList=new ArrayList<>();
        DatabaseReference bestDealRef= FirebaseDatabase.getInstance().getReference(Common.Best_Deal_REF);
        bestDealRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot itemsnapshot:dataSnapshot.getChildren())
                {
                    BestDealModel model =itemsnapshot.getValue(BestDealModel.class);
                    tempList.add(model);
                }
                bestDealCallBackListener.onBestDealLoadSuccess(tempList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                bestDealCallBackListener.onBestDealLoadFailed(databaseError.getMessage());

            }
        });
    }

    public MutableLiveData<List<PopularCategoryModel>> getPopularList() {
        if (popularList == null)
        {
            popularList=new MutableLiveData<>();
            messageError=new MutableLiveData<>();
            loadPopularList();
        }
        return popularList;
    }

    private void loadPopularList() {
        List<PopularCategoryModel>tempList=new ArrayList<>();
        DatabaseReference popularRef= FirebaseDatabase.getInstance().getReference(Common.POPULAR_CATEGORY_REF);
        popularRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot itemsnapshot:dataSnapshot.getChildren())
                {
                    PopularCategoryModel model =itemsnapshot.getValue(PopularCategoryModel.class);
                    tempList.add(model);
                }
                    popularCallbackListener.onPopularLoadSuccess(tempList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                popularCallbackListener.onPopularLoadFailed(databaseError.getMessage());

            }
        });
    }

    public MutableLiveData<String> getMessageError() {
        return messageError;
    }

    @Override
    public void onPopularLoadSuccess(List<PopularCategoryModel> popularCategoryModels) {
        popularList.setValue(popularCategoryModels);
    }

    @Override
    public void onPopularLoadFailed(String message) {
        messageError.setValue(message);

    }

    @Override
    public void onBestDealLoadSuccess(List<BestDealModel> bestDealModels) {
        bestDealList.setValue(bestDealModels);
    }

    @Override
    public void onBestDealLoadFailed(String message) {
        messageError.setValue(message);

    }
}

