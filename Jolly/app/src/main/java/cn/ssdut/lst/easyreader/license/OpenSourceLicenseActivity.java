package cn.ssdut.lst.easyreader.license;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.ssdut.lst.easyreader.R;

/**
 * Created by lisongting on 2017/4/30.
 */

public class OpenSourceLicenseActivity extends AppCompatActivity{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);

        OpenSourceLicenseFragment fragment = OpenSourceLicenseFragment.newInstacnce();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment)
                .commit();
    }
}
