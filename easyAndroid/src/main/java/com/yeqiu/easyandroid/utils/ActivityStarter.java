package com.yeqiu.easyandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Pair;

import java.io.Serializable;
import java.util.List;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/14
 * @describe:
 * @fix:
 */
public class ActivityStarter {

    private ActivityStarter() {
    }


    @SafeVarargs
    public static void start(Context context, Class<? extends Activity> destination, Pair<String, Object>... params) {

        start(context, createIntent(context, destination, params));

    }


    public static void start(Context context, Intent intent) {

        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
    }


    private static Intent createIntent(Context context, Class<? extends Activity> destination,
                                       Pair<String, Object>... params) {

        Intent intent = new Intent(context, destination);

        if (params == null) {
            return intent;
        }

        for (Pair<String, Object> param : params) {
            String key = param.first;
            Object value = param.second;
            putValueToIntent(intent, key, value);
        }

        return intent;
    }


    /**
     * 往intent里存值
     *
     * @param intent
     * @param key
     * @param value
     */
    private static void putValueToIntent(Intent intent, String key, Object value) {

        if (TextUtils.isEmpty(key) || value == null) {
            return;
        }
        if (value instanceof String) {
            intent.putExtra(key, (String) value);
            return;
        }
        if (value instanceof Long) {
            intent.putExtra(key, (Long) value);
            return;
        }
        if (value instanceof Integer) {
            intent.putExtra(key, (Integer) value);
            return;
        }
        if (value instanceof Boolean) {
            intent.putExtra(key, (Boolean) value);
            return;
        }
        if (value instanceof Serializable) {
            intent.putExtra(key, (Serializable) value);
            return;
        }

        if (value instanceof Parcelable) {
            intent.putExtra(key, (Parcelable) value);
            return;
        }

        if (value instanceof List) {
            //数组转json
            intent.putExtra(key, (String) value);
        }

        throw new IllegalArgumentException("value type cannot recognition！");
    }




    /**
     * 跳转浏览器
     *
     * @param context
     * @param url
     */
    public static void startToBrowser(Context context, String url) {

        if (CommonUtil.isNotEmpty(url)) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            start(context, intent);
        }

    }

    /**
     * 跳转到拨号页面
     * @param context
     * @param number
     */
    public static void startToDial(Context context, String number) {

        if (CommonUtil.isNotEmpty(number)) {
            Uri uri = Uri.parse("tel:"+number);
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            start(context, intent);
        }

    }


    /**
     * 跳转到系统App设置
     * @param context
     */
    public static void startToAppSetting(Context context) {

        String action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        Intent intent = new Intent(action, uri);
        start(context, intent);

    }


    /**
     * 跳转到系统设置
     * @param context
     */
    public static void startToSystemSetting(Context context) {

        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        start(context, intent);

    }

    /**
     * 跳转到wifi
     * @param context
     */
    public static void startToWifi(Context context) {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        start(context, intent);
    }


    /**
     * 跳转关于设备
     * @param context
     */
    public static void startToAboutDevice(Context context) {
        Intent intent = new Intent(Settings.ACTION_DEVICE_INFO_SETTINGS);
        start(context, intent);
    }

    /**
     * 跳转到定位设置(不是打开定位权限，是管理已授权的应用)
     * @param context
     */
    public static void startToLocationSetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        start(context, intent);
    }


}
