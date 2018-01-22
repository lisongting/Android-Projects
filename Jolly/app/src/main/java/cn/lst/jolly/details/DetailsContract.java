package cn.lst.jolly.details;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import java.util.List;

import cn.lst.jolly.BasePresenter;
import cn.lst.jolly.BaseView;
import cn.lst.jolly.data.ContentType;
import cn.lst.jolly.data.DoubanMomentContent;
import cn.lst.jolly.data.DoubanMomentNewsThumbs;
import cn.lst.jolly.data.GuokrHandpickContentResult;
import cn.lst.jolly.data.ZhihuDailyContent;

/**
 * Created by lisongting on 2018/1/16.
 */

public class DetailsContract {
    interface View extends BaseView<Presenter> {

        void showMessage(@StringRes int stringRes);

        boolean isActive();

        void showZhihuDailyContent(@NonNull ZhihuDailyContent content);

        void showDoubanMomentContent(@NonNull DoubanMomentContent content, @Nullable List<DoubanMomentNewsThumbs> list);

        void showGuokrHandpickContent(@NonNull GuokrHandpickContentResult content);

        void share(@Nullable String link);

        void copyLink(@Nullable String link);

        void openWithBrowser(@Nullable String link);

    }

    interface Presenter extends BasePresenter {

        void favorite(ContentType type, int id, boolean favorite);

        void loadDoubanContent(int id);

        void loadZhihuDailyContent(int id);

        void loadGuokrHandpickContent(int id);

        void getLink(ContentType type, int requestCode, int id);

    }

}
