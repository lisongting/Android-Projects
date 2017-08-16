package com.paperfish.hotfixtest;

import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Created by lisongting on 2017/8/16.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initSophix();
    }

    //初始化Sophix
    private void initSophix() {
        String appVersion;

        try{
            //获取当前版本号
            appVersion = this.getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            Log.i("tag", "versionName:" + appVersion);
            Log.i("tag", "versionCode:" + getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);

        }catch (PackageManager.NameNotFoundException e) {
            appVersion = "1.0.0.0";
        }

        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                        }
                    }
                }).initialize();
        //加载新的补丁包
        SophixManager.getInstance().queryAndLoadNewPatch();
    }

    public interface msgDisplayListener{

        void handle(String msg);
    }


}
