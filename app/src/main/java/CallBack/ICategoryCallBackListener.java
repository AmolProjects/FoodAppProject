package CallBack;

import java.util.List;

import Model.BestDealModel;
import Model.CategoryModel;

public interface ICategoryCallBackListener {
    void onCategoryLoadSuccess(List<CategoryModel> categoryModelList);
    void onCategoryLoadFailed(String message);
}
