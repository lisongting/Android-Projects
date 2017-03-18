package cn.ssdut.lst.mvp.presenter;

import android.os.Handler;
import android.os.Looper;

import cn.ssdut.lst.mvp.model.IUser;
import cn.ssdut.lst.mvp.model.UserModel;
import cn.ssdut.lst.mvp.view.ILoginView;

/**
 * Created by Administrator on 2017/3/12.
 */

public class LoginPresenterImpl implements ILoginPresenter {

    ILoginView iLoginView;
    IUser user;
    Handler handler;

    public LoginPresenterImpl(ILoginView view) {
        this.iLoginView = view;
        initUser();
        handler = new Handler(Looper.getMainLooper());
    }
    @Override
    public void clear() {
        iLoginView.onClearText();
    }

    @Override
    public void doLogin(String name, String password) {
        Boolean isLoginSuccess = true;
        final int code = user.checkUserValidity(name, password);
        if (code != 1) {
            isLoginSuccess = false;
        }else{
            isLoginSuccess = true;
        }
        final boolean result = isLoginSuccess;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iLoginView.onLoginResult(result, code);
            }
        },500);
    }

    @Override
    public void setProgressBarvisibity(int visibility) {
        iLoginView.onSetProgressBarVisibility(visibility);
    }

    private void initUser() {
        user = new UserModel("mvp","mvp");
    }
}
