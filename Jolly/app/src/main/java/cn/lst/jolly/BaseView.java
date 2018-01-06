package cn.lst.jolly;

import android.view.View;

/**
 * Created by lisongting on 2018/1/2.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);

    void initViews(View view);

}
