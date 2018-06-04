package com.lst.wanandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lst.wanandroid.R;
import com.lst.wanandroid.app.WanAndroidApp;
import com.lst.wanandroid.app.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

public class CommonUtil {

    //将dp转换为像素
    public static int dp2px(float dpValue) {
        final float scale = WanAndroidApp.getInstance().getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    public static void showMessage(Activity activity, String msg) {
        LogHelper.i("showMessage:" + msg);
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackMessage(Activity activity,String msg){
        LogHelper.i("showSnackMessage:" + msg);
        Snackbar snackbar = Snackbar.make(activity.getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(ContextCompat.getColor(activity, R.color.white));
        snackbar.show();
    }

    public static boolean isEquals(Object a,Object b){
        return a == null ? b == null : a.equals(b);
    }

    public static boolean isNetworkConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                WanAndroidApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!=null;
    }

    public static String getProcessName(int pid){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName =reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        }catch (Throwable t){
            t.printStackTrace();
        }finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public static int randomColor(){
        Random random = new Random();
        //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        int red =random.nextInt(150);
        //0-190
        int green =random.nextInt(150);
        //0-190
        int blue =random.nextInt(150);
        //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red,green, blue);
    }

    public static int randomTagColor() {
        int randomNum = new Random().nextInt();
        int position = randomNum % Constants.TAB_COLORS.length;
        if (position < 0) {
            position = -position;
        }
        return Constants.TAB_COLORS[position];
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object o) {
        return (T) o;
    }
}
