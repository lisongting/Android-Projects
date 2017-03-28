package cn.ssdut.lst.easyreader;

import android.view.View;

/**
 * Created by Administrator on 2017/3/28.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);

    void initViews(View view);

}
