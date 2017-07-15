package com.paperfish.espresso.mvp;

import android.view.View;

/**
 * Created by lisongting on 2017/7/15.
 */

public interface BaseView<T> {
    void initViews(View view);

    void setPresenter(T presenter);

}
