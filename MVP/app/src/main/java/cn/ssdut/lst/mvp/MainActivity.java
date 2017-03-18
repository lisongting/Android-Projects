package cn.ssdut.lst.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import cn.ssdut.lst.mvp.presenter.ILoginPresenter;
import cn.ssdut.lst.mvp.presenter.LoginPresenterImpl;
import cn.ssdut.lst.mvp.view.ILoginView;

/**
 * 2017.3.12
 * 这是一个基于MVP架构的应用程序
 * M-Model
 * V-View
 * P-Presenter
 */
public class MainActivity extends AppCompatActivity implements ILoginView, View.OnClickListener {

    private EditText editUser;
    private EditText editPass;
    private Button btnLogin;
    private Button   btnClear;
    private ProgressBar progressBar;
    //注意：MainActivity属于View层，持有Presenter层的引用
    ILoginPresenter loginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editUser = (EditText) this.findViewById(R.id.id_et_name);
        editPass = (EditText) this.findViewById(R.id.id_et_password);
        btnLogin = (Button) this.findViewById(R.id.id_bt_login);
        btnClear = (Button) this.findViewById(R.id.id_bt_clear);
        progressBar = (ProgressBar) this.findViewById(R.id.id_pb_progress);

        btnLogin.setOnClickListener(this);
        btnClear.setOnClickListener(this);

        //MainActivity实现了ILoginView接口，因此可以直接用来构造LoginPresenter
        loginPresenter = new LoginPresenterImpl(this);
        loginPresenter.setProgressBarvisibity(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_bt_login:
                loginPresenter.setProgressBarvisibity(View.VISIBLE);
                loginPresenter.doLogin(editUser.getText().toString(),
                        editPass.getText().toString());
                break;
            case R.id.id_bt_clear:
                loginPresenter.clear();
                break;
        }
    }

    @Override
    public void onClearText() {
        editPass.setText("");
        editUser.setText("");
    }

    @Override
    public void onLoginResult(boolean result, int code) {
        loginPresenter.setProgressBarvisibity(View.INVISIBLE);
        if (result) {
            Toast.makeText(MainActivity.this,"Login Seccess!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this,"Login Failed! code:"+code,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }
}
