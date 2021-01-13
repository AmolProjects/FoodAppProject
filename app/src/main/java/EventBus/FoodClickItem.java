package EventBus;

import Model.FoodModel;

public class FoodClickItem {
    private boolean success;
    private FoodModel foodModel;

    public FoodClickItem() {
    }

    public FoodClickItem(boolean success, FoodModel foodModel) {
        this.success = success;
        this.foodModel = foodModel;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public FoodModel getFoodModel() {
        return foodModel;
    }

    public void setFoodModel(FoodModel foodModel) {
        this.foodModel = foodModel;
    }
}
