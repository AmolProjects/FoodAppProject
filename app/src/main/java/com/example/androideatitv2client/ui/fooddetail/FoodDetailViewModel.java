package com.example.androideatitv2client.ui.fooddetail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import COMMON.Common;
import Model.CommentModel;
import Model.FoodModel;

public class FoodDetailViewModel extends ViewModel {

    private static MutableLiveData<FoodModel>mutableLiveDataFood;
    private MutableLiveData<CommentModel>mutableLiveDataComment;

    public static void setFoodModel(FoodModel foodModel) {
        if (mutableLiveDataFood !=null)
        mutableLiveDataFood.setValue(foodModel);
    }

    public void setCommentModel(CommentModel commentModel)
    {
        if (mutableLiveDataComment != null)
            mutableLiveDataComment.setValue(commentModel);
    }

    public MutableLiveData<CommentModel> getMutableLiveDataComment() {
        return mutableLiveDataComment;
    }

    public FoodDetailViewModel() {
        mutableLiveDataComment=new MutableLiveData<>();
    }

    public MutableLiveData<FoodModel> getMutableLiveDataFood() {
        if (mutableLiveDataFood ==null)
            mutableLiveDataFood=new MutableLiveData<>();
        mutableLiveDataFood.setValue(Common.selectedFood);
        return mutableLiveDataFood;
    }


}
