package com.example.androideatitv2client.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface CartDAO {
    @Query("SELECT * FROM Cart WHERE uid=:uid")
    Flowable<List<CartItem>>getAllCart(String uid);

    @Query("SELECT COUNT(*) from Cart WHERE uid=:uid")
    Single<Integer> countItemCart(String uid);

    @Query("SELECT SUM(foodPrice*foodQuantity)+(foodExtraPrice*foodQuantity)FROM Cart WHERE uid=:uid")
    Single<Long> sumPriceInCart(String uid);

    @Query("SELECT * FROM Cart WHERE foodId=:foodId AND uid=:uid")
    Single<CartItem>getItemInCart(String foodId,String uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertOrReplaceAll(CartItem...cartItems);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Single<Integer>updateCartItems(CartItem cartItem);

    @Delete
    Single<Integer>deleteCartItem(CartItem cartItem);

    @Query("DELETE FROM Cart WHERE uid=:uid")
    Single<Integer> cleanCart(String uid);
}
