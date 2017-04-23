package cn.ssdut.lst.easyreader.detail;

import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

/**
 * Created by lisongting on 2017/4/22.
 */

public class DetailFragment extends Fragment implements DetailContract.View{
    private ImageView imageView;
    private WebView webView;
    private NestedScrollView scrollingView;

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {

    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showLoadingError() {

    }

    @Override
    public void showSharingError() {

    }

    @Override
    public void showResult(String result) {

    }

    @Override
    public void showResultWithoutBody(String url) {

    }

    @Override
    public void showCover(String url) {

    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setImageMode(boolean showImage) {

    }

    @Override
    public void showBrowserNotFoundError() {

    }

    @Override
    public void showTextCopied() {

    }

    @Override
    public void showCopyTextError() {

    }

    @Override
    public void showAddedToBookmarks() {

    }

    @Override
    public void showDeletedFromBookmarks() {

    }
}
