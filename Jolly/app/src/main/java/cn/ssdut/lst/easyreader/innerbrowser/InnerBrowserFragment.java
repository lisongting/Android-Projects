package cn.ssdut.lst.easyreader.innerbrowser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.ssdut.lst.easyreader.R;
import cn.ssdut.lst.easyreader.util.NetworkState;

/**
 * Created by lisongting on 2017/5/9.
 */

public class InnerBrowserFragment extends Fragment {
    private ProgressBar progressBar;
    private WebView webView;
    private Toolbar toolbar;
    private TextView textView;
    private ImageView imageView;
    private String url;

    public InnerBrowserFragment() {

    }

    public static InnerBrowserFragment getInstance() {
        return new InnerBrowserFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getActivity().getIntent().getStringExtra("url");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inner_browser, container, false);

        initViews(view);
        initWebSettings(webView);

        setHasOptionsMenu(true);

        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int newProgress) {
                changeToolbarAndProgressbar(newProgress);
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view1, WebResourceRequest request, WebResourceError error) {
                webView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (NetworkState.networkConnected(getContext())){
                            webView.loadUrl(url);
                            webView.setVisibility(View.VISIBLE);
                            imageView.setVisibility(View.GONE);
                            textView.setVisibility(View.GONE);
                        }
                    }
                });
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });


        // 设置在本WebView内可以通过按下返回上一个html页面
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
                        webView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });

        webView.loadUrl(url);

        return view;
    }

    private void initWebSettings(WebView webView) {

        //能够和js交互
        webView.getSettings().setJavaScriptEnabled(true);
        //缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        webView.getSettings().setBuiltInZoomControls(false);
        //缓存
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //开启DOM storage API功能
        webView.getSettings().setDomStorageEnabled(true);
        //开启application Cache功能
        webView.getSettings().setAppCacheEnabled(false);


    }

    private void initViews(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        webView = (WebView) view.findViewById(R.id.web_view);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView = (TextView) view.findViewById(R.id.text_view);
        imageView = (ImageView) view.findViewById(R.id.image_view);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_browser, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            getActivity().onBackPressed();
        } else if (id == R.id.action_open_in_browser){
            startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(webView.getUrl())));
        } else if (id == R.id.action_refresh) {
            webView.reload();
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeToolbarAndProgressbar(int newProgress) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setMax(100);
        progressBar.setProgress(newProgress);
        toolbar.setTitle(webView.getTitle());
    }
}
