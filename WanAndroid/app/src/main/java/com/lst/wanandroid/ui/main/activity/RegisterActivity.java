package com.lst.wanandroid.ui.main.activity;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.lst.wanandroid.R;
import com.lst.wanandroid.app.Constants;
import com.lst.wanandroid.base.activity.BaseActivity;
import com.lst.wanandroid.contract.main.RegisterContract;
import com.lst.wanandroid.core.bean.main.login.LoginData;
import com.lst.wanandroid.presenter.main.RegisterPresenter;
import com.lst.wanandroid.utils.CommonUtil;
import com.lst.wanandroid.utils.StatusBarUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class RegisterActivity extends BaseActivity<RegisterPresenter>
        implements RegisterContract.View{
    @BindView(R.id.common_toolbar)
    Toolbar toolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.register_account_edit)
    EditText mAccountEdit;
    @BindView(R.id.register_password_edit)
    EditText mPasswordEdit;
    @BindView(R.id.register_confirm_password_edit)
    EditText mConfirmPasswordEdit;
    @BindView(R.id.register_btn)
    Button mRegisterBtn;

    @Override
    protected void initEventAndData() {
        initToolbar();
        InputMethodManager inputMethodManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            mAccountEdit.requestFocus();
            inputMethodManager.showSoftInput(mAccountEdit, 0);
        }

        //一秒内连续点击只选取第一次点击事件
        mPresenter.addRxBindingSubscribe(RxView.clicks(mRegisterBtn)
            .throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
            .subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object o) throws Exception {
                    register();
                }
            }));
    }

    private void register(){
        String account = mAccountEdit.getText().toString().trim();
        String passWord = mPasswordEdit.getText().toString().trim();
        String rePassWord = mConfirmPasswordEdit.getText().toString().trim();
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(passWord) || TextUtils.isEmpty(rePassWord)) {
            CommonUtil.showSnackMessage(this, getString(R.string.account_password_null_tint));
            return;
        }
        if (!passWord.equals(rePassWord)) {
            CommonUtil.showSnackMessage(this, getString(R.string.password_not_same));
            return;
        }
        mPresenter.getRegisterData(account,passWord,rePassWord);
    }

    private void initToolbar(){
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this,toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.register_bac));
        mTitleTv.setText(R.string.register);
        mTitleTv.setTextColor(ContextCompat.getColor(this, R.color.white));
        mTitleTv.setTextSize(20);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
    }

    @Override
    public void showRegisterData(LoginData loginData) {
        CommonUtil.showSnackMessage(this,getString(R.string.register_success));
        onBackPressedSupport();
    }




    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
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
}
