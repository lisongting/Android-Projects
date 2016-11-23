package com.test.administrator.webviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText edit;
    private WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit=(EditText)findViewById(R.id.editText);
        web = (WebView)findViewById(R.id.webview);
        web.canGoForward();
    }
    public void showWeb(View v){
        String urlStr = edit.getText().toString();
        web.loadUrl(urlStr);
    }

}
