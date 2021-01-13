package CallBack;

        import java.util.List;

        import Model.BestDealModel;
        import Model.PopularCategoryModel;

public interface IBestDealCallBackListener {
    void onBestDealLoadSuccess(List<BestDealModel> bestDealModels);
    void onBestDealLoadFailed(String message);
}
