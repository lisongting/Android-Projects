package com.test.administrator.activity_fragment;

import android.app.Fragment;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class PreferenceActivityTest extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        if(hasHeaders()){
            Button button = new Button(this);
            button.setText("设置操作");
            setListFooter(button);//将按钮添加到该界面上
        }
    }
    //重写该方法，负责加载界面布局文件
    @Override
    public void onBuildHeaders(List<Header> target){
        loadHeadersFromResource(R.xml.preference_headers,target);
    }

    //重写该方法，验证preferenceFragment是否有效
    @Override
    public boolean isValidFragment(String fragmentName){
        return true;
    }

    //定义两个内部类
    public static class Prefs1Fragment extends PreferenceFragment {
        public  void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }

    public static class Prefs2Fragment extends PreferenceFragment{
        public void onCreate(Bundle savedInstaceState){
            super.onCreate(savedInstaceState);
            addPreferencesFromResource(R.xml.display_prefs);
            String website = getArguments().getString("website");
            Toast.makeText(getActivity(),"网站域名是"+website,Toast.LENGTH_SHORT).show();
        }

    }
}
