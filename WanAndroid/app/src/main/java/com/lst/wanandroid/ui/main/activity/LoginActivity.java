package com.lst.wanandroid.ui.main.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding2.view.RxView;
import com.lst.wanandroid.R;
import com.lst.wanandroid.app.Constants;
import com.lst.wanandroid.base.activity.BaseActivity;
import com.lst.wanandroid.component.RxBus;
import com.lst.wanandroid.contract.main.LoginContract;
import com.lst.wanandroid.core.bean.main.login.LoginData;
import com.lst.wanandroid.core.event.LoginEvent;
import com.lst.wanandroid.presenter.main.LoginPresenter;
import com.lst.wanandroid.utils.CommonUtil;
import com.lst.wanandroid.utils.StatusBarUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity<LoginPresenter>
    implements LoginContract.View{
    @BindView(R.id.login_group)
    RelativeLayout mLoginGroup;
    @BindView(R.id.login_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.login_account_edit)
    EditText mAccountEdit;
    @BindView(R.id.login_password_edit)
    EditText mPasswordEdit;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.login_register_btn)
    Button mRegisterBtn;

    @Override
    protected void initEventAndData() {
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
        mPresenter.addRxBindingSubscribe(RxView.clicks(mLoginBtn)
            .throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
            .subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object o) throws Exception {
                    String account = mAccountEdit.getText().toString().trim();
                    String password = mPasswordEdit.getText().toString().trim();
                    if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                        CommonUtil.showSnackMessage(LoginActivity.this,
                                getString(R.string.account_password_null_tint));
                        return;
                    }
                    mPresenter.getLoginData(account,password);
                }
            }));
    }

    //只有登录成功才会调用showLoginData()
    @Override
    public void showLoginData(LoginData loginData) {
        mPresenter.setLoginAccount(loginData.getUsername());
        mPresenter.setLoginPassword(loginData.getPassword());
        mPresenter.setLoginState(true);
        RxBus.getDefault().post(new LoginEvent(true));
        CommonUtil.showSnackMessage(this, getString(R.string.login_success));
        onBackPressedSupport();
    }

    @Override
    public void showRegisterData(LoginData loginData) {
        mPresenter.getLoginData(loginData.getUsername(), loginData.getPassword());
    }

    @OnClick(R.id.login_register_btn)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_register_btn:
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(mRegisterBtn,
                        mRegisterBtn.getWidth() / 2, mRegisterBtn.getHeight() / 2, 0, 0);
                startActivity(new Intent(this, RegisterActivity.class),options.toBundle());
                break;
            default:break;
        }
    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void reload() {

    }

    @Override
    public void showLoginView() {

    }

    @Override
    public void showLogoutView() {

    }

    @Override
    public void showCollectSuccess() {

    }

    @Override
    public void showCancelCollectSuccess() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


}
