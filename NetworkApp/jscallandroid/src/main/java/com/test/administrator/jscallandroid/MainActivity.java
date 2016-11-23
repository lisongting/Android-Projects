package com.test.administrator.jscallandroid;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView)findViewById(R.id.webView);
        webView.loadUrl(new String("http://10.21.221.81:8080/test/test.html"));
        WebSettings settings = webView.getSettings();
        //开启javascript调用
        settings.setJavaScriptEnabled(true);
        //将myObject对象暴露给javascript脚本
        webView.addJavascriptInterface(new MyObject(this),"myObj");
    }
    class MyObject{
        Context mContext;
        MyObject(Context con){
            mContext = con;
        }
        //将该方法暴露给javascript调用
        @JavascriptInterface
        public void showToast(String name){
            Toast.makeText(MainActivity.this, name+"你好!", Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void showList(){
            new AlertDialog.Builder(mContext)
                    .setTitle("图书列表")
                    .setIcon(R.drawable.list)
                    .setItems(new String[]{"塞纳河畔","左岸的咖啡","我手一杯","品尝你的美"},null)
                    .setPositiveButton("确定",null)
                    .create()
                    .show();
        }

    }
}
