package cn.lst.facerecog.recognize;


import cn.lst.facerecog.BasePresenter;
import cn.lst.facerecog.BaseView;

/**
 * Created by lisongting on 2017/12/14.
 */

public interface RecogContract {

    interface Presenter extends BasePresenter {

        void recognize(String strBitmap);

        void destroy();
    }

    interface View extends BaseView<Presenter> {

        void showRecognitionSuccess(String userName);

        void showError(String s);
    }


}
