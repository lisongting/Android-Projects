package com.paperfish.espresso.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import io.reactivex.Observable;

/**
 * Created by lisongting on 2017/8/11.
 */

public class DensityUtil {

    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();

        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        return displayMetrics.heightPixels;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();

        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getScreenHeightWithDecorations(Context context) {
        WindowManager windowManager = ((Activity) context).getWindowManager();

        Display display = windowManager.getDefaultDisplay();
        Point realSize = new Point();

        display.getRealSize(realSize);
        return realSize.y;
    }

    public static final int dip2px(Context context, float dpValue) {
        //得到dpi的实际值
        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dpValue * scale + 0.5f);

    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelOffset(resourceId);
        return height;
    }

    public static int dip2sp(Context context, float dp) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, context.getResources().getDisplayMetrics());
    }

    public static final int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);

        Observable<String> observable = Observable.
    }

}
