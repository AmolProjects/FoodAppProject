package com.example.androideatitv2client.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cart")
public class CartItem {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "foodId")
    public String foodId;

    @ColumnInfo(name = "foodName")
    public String foodName;

    @ColumnInfo(name = "foodImage")
    public String foodImage;

    @ColumnInfo(name = "foodPrice")
    public Double foodPrice;

    @ColumnInfo(name = "foodQuantity")
    public int foodQuantity;

    @ColumnInfo(name = "userPhone")
    public String userPhone;

    @ColumnInfo(name = "foodExtraPrice")
    public Double foodExtraPrice;

    @ColumnInfo(name = "foodAddon")
    public String foodAddon;

    @ColumnInfo(name = "foodSize")
    public String foodSize;

    @ColumnInfo(name = "uid")
    public String uid;

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public Double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(Double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Double getFoodExtraPrice() {
        return foodExtraPrice;
    }

    public void setFoodExtraPrice(Double foodExtraPrice) {
        this.foodExtraPrice = foodExtraPrice;
    }

    public String getFoodAddon() {
        return foodAddon;
    }

    public void setFoodAddon(String foodAddon) {
        this.foodAddon = foodAddon;
    }

    public String getFoodSize() {
        return foodSize;
    }

    public void setFoodSize(String foodSize) {
        this.foodSize = foodSize;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}


