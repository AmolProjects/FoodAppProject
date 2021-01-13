package COMMON;

import Model.CategoryModel;
import Model.FoodModel;
import Model.UserModel;

public class Common {
    public static final String USER_REFERENCES="USER"; //category in firebase
    public static final String POPULAR_CATEGORY_REF ="MostPopular" ;//
    public static final int DEFAULT_COLUMN_COUNT =0 ;
    public static final int FULL_WIDTH_COLUMN =1 ;
    public static final String CATEGORY_REF = "Category";
    public static final String COMMENT_REF = "Comments";
    public static String Best_Deal_REF="BestDeals";
    public static UserModel currentUser;
    public static CategoryModel categorySelected;
    public static FoodModel selectedFood;

}
