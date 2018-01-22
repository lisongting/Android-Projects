package cn.lst.jolly.timeline;

import android.support.annotation.NonNull;

import java.util.List;

import cn.lst.jolly.BasePresenter;
import cn.lst.jolly.BaseView;
import cn.lst.jolly.data.GuokrHandpickNewsResult;

/**
 * Created by lisongting on 2018/1/16.
 */

public interface GuokrHandpickContract {

    interface View extends BaseView<Presenter> {

        boolean isActive();

        void setLoadingIndicator(boolean active);

        void showResult(@NonNull List<GuokrHandpickNewsResult> list);

    }

    interface Presenter extends BasePresenter {

        void load(boolean forceUpdate, boolean clearCache, int offset, int limit);

    }

}
