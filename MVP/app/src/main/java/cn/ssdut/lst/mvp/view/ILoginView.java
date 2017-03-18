package cn.ssdut.lst.mvp.view;

/**
 * Created by Administrator on 2017/3/12.
 */

public interface ILoginView {
    public void onClearText();
    public  void onLoginResult(boolean result,int code);
    public void onSetProgressBarVisibility(int visibility);
}
