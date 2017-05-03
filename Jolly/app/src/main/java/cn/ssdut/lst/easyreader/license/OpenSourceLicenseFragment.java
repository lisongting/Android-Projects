package cn.ssdut.lst.easyreader.license;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import cn.ssdut.lst.easyreader.R;

/**
 * Created by lisongting on 2017/4/30.
 */

public class OpenSourceLicenseFragment extends Fragment implements OpenSourceLicenseContract.View{

    private WebView webView;


    public OpenSourceLicenseFragment() {

    }

    public static OpenSourceLicenseFragment newInstacnce() {
        return new OpenSourceLicenseFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_source_license, container, false);
        initViews(view);
        setHasOptionsMenu(true);
        loadLicense("file://android_assets/license.html");

        return view;
    }
    @Override
    public void setPresenter(OpenSourceLicenseContract.Presenter presenter) {

    }

    @Override
    public void initViews(View view) {
        AppCompatActivity activity = (OpenSourceLicenseActivity) getActivity();
        activity.setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView = (WebView) view.findViewById(R.id.web_view);
    }

    @Override
    public void loadLicense(String path) {
        webView.loadUrl(path);
    }
}
