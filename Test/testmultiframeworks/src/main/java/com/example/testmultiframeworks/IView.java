package com.example.testmultiframeworks;

import android.view.View;

/**
 * Created by lisongting on 2017/6/25.
 */

public interface IView<T> {
    void init(View v);

    void setPresenter(T presenter);
}
