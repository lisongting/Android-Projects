package cn.ssdut.lst.mvp.presenter;

/**
 * Created by Administrator on 2017/3/12.
 */

public interface ILoginPresenter {
    void clear();

    void doLogin(String name, String password);

    void setProgressBarvisibity(int visibility);
}
