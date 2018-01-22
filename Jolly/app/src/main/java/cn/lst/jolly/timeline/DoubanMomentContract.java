
package cn.lst.jolly.timeline;

import android.support.annotation.NonNull;

import java.util.List;

import cn.lst.jolly.BasePresenter;
import cn.lst.jolly.BaseView;
import cn.lst.jolly.data.DoubanMomentNewsPosts;


public interface DoubanMomentContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        boolean isActive();

        void showResult(@NonNull List<DoubanMomentNewsPosts> list);

    }

    interface Presenter extends BasePresenter {

        void load(boolean forceUpdate, boolean clearCache, long date);

    }

}
