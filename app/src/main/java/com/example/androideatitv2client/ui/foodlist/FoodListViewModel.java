package com.example.androideatitv2client.ui.foodlist;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import COMMON.Common;
import Model.FoodModel;

public class FoodListViewModel extends ViewModel {

    private MutableLiveData<List<FoodModel>>mutableLiveDataFoodList;

    public FoodListViewModel()
    {

    }

    public MutableLiveData<List<FoodModel>> getMutableLiveDataFoodList() {
        if (mutableLiveDataFoodList ==null)
            mutableLiveDataFoodList=new MutableLiveData<>();
        mutableLiveDataFoodList.setValue(Common.categorySelected.getFoods());
        return mutableLiveDataFoodList;
    }
}