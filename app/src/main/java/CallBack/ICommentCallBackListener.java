package CallBack;

import java.util.List;

import Model.CommentModel;

public interface ICommentCallBackListener {
    void onCommentLoadSuccess (List<CommentModel>commentModels);
    void onCommentLoadFailed(String message);

}
