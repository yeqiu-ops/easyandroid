package com.yeqiu.easyandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import com.yeqiu.easyandroid.EasyAndroid;


/**
 * @project：HailHydra
 * @author：小卷子
 * @date 2018/9/7
 * @describe：
 * @fix：
 */
public class ScreenUtils {


    /**
     * 横竖屏
     *
     * @return true 竖屏 false 横屏
     */
    public static boolean isScreenPortrait() {

        Context context = EasyAndroid.getInstance().getContext();
        return context.getResources().getConfiguration().orientation == Configuration
                .ORIENTATION_PORTRAIT;
    }


    /**
     * 获得状态栏的高度
     * 单位 px
     *
     * @return
     */
    public static int getStatusHeight() {

        Context context = EasyAndroid.getInstance().getContext();
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }


    /**
     * 获得屏幕高度
     * 单位 px
     *
     * @return
     */
    public static int getScreenHeight() {

        Context context = EasyAndroid.getInstance().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;

    }

    /**
     * 获得屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth() {

        Context context = EasyAndroid.getInstance().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }


    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    public static int dp2px(float dp) {

        Context context = EasyAndroid.getInstance().getContext();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param sp
     * @return
     */
    public static int sp2px(float sp) {

        Context context = EasyAndroid.getInstance().getContext();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                sp, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param px
     * @return
     */
    public static float px2dp(float px) {
        Context context = EasyAndroid.getInstance().getContext();
        final float scale = context.getResources().getDisplayMetrics().density;
        return (px / scale);
    }

    /**
     * px转sp
     *
     * @param px
     * @return
     */
    public static float px2sp(float px) {

        Context context = EasyAndroid.getInstance().getContext();
        return (px / context.getResources().getDisplayMetrics().scaledDensity);
    }


    /**
     * 获取当前屏幕截图，包含状态栏 但状态栏是空白
     */
    public static Bitmap getScreenShotWithStatusBar(Activity activity) {

        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     */
    public static Bitmap getScreenShotWithoutStatusBar(Activity activity) {

        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }


    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = EasyAndroid.getInstance().getContext().getResources().getIdentifier
                ("status_bar_height",
                        "dimen",
                        "android");
        if (resourceId > 0) {
            result = EasyAndroid.getInstance().getContext().getResources()
                    .getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取导航栏高度
     *
     * @return
     */
    public static int getDaoHangHeight() {
        int result = 0;
        int resourceId = 0;
        int rid = EasyAndroid.getInstance().getContext().getResources().getIdentifier("config_showNavigationBar", "bool",
                "android");
        if (rid != 0) {
            resourceId = EasyAndroid.getInstance().getContext().getResources().getIdentifier("navigation_bar_height", "dimen",
                    "android");
            return EasyAndroid.getInstance().getContext().getResources().getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }

    }
}
