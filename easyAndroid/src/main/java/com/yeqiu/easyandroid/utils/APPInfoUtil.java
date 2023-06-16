package com.yeqiu.easyandroid.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import androidx.core.app.NotificationManagerCompat;

import com.yeqiu.easyandroid.EasyAndroid;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/11
 * @describe:
 * @fix:
 */
public class APPInfoUtil {

    /**
     * 获取app名
     *
     * @return
     */
    public static String getAppName() {

        Context context = EasyAndroid.getInstance().getContext();

        try {
            PackageManager e = context.getPackageManager();
            PackageInfo packageInfo = e.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
            return "unKnown";

        }
    }


    /**
     * 获取渠道名
     *
     * @return
     */
    public static String getChannelName() {


        Context context = EasyAndroid.getInstance().getContext();
        String channelName = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,
                // 因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.
                        getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelName = String.valueOf(applicationInfo.metaData.get("CHANNEL_NAME"));
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }

    /**
     * 获取版本名
     */
    public static String getVersionName() {

        Context context = EasyAndroid.getInstance().getContext();

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            if (packInfo != null) {
                return packInfo.versionName;
            } else {
                return "unKnown";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "unKnown";
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public static int getVersionCode() {
        Context context = EasyAndroid.getInstance().getContext();
        int versionCodeInt = 0;
        try {
            PackageInfo packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            versionCodeInt = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCodeInt;
    }


    /**
     * 获取应用图标
     *
     * @return
     */
    public static Drawable getAppIcon() {
        Context context = EasyAndroid.getInstance().getContext();
        PackageManager pm = context.getPackageManager();
        Drawable appIcon = null;
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo(getPackageName(), 0);
            appIcon = applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appIcon;
    }

    /**
     * 通知是否打开
     *
     * @return
     */
    public static boolean isNotificationOpen() {
        Context context = EasyAndroid.getInstance().getContext();
        return NotificationManagerCompat.from(context).areNotificationsEnabled();

    }


    /**
     * 获取包名
     *
     * @return
     */
    public static String getPackageName() {
        Context context = EasyAndroid.getInstance().getContext();
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0)
                    .packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * 获取应用第一次安装日期
     *
     * @return
     */
    public static long getFirstInstallTime() {
        Context context = EasyAndroid.getInstance().getContext();
        long lastUpdateTime = 0;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(getPackageName()
                    , 0);
            lastUpdateTime = packageInfo.firstInstallTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return lastUpdateTime;
    }



    /**
     * 获取应用大小
     *
     * @return
     */
    public static long getAppSize() {
        Context context = EasyAndroid.getInstance().getContext();
        long appSize = 0;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo
                    (getPackageName(), 0);
            appSize = new File(applicationInfo.sourceDir).length();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appSize;
    }

    /**
     * 提取应用apk文件,返回文件路径
     *
     * @return
     */
    public static String getAppApk() {
        Context context = EasyAndroid.getInstance().getContext();
        String sourceDir = null;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo
                    (getPackageName(), 0);
            sourceDir = applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return sourceDir;
    }




    /**
     * 检查指定应用是否指定安装
     *
     * @param packageName
     * @return
     */
    public static boolean isInstalled(String packageName) {
        Context context = EasyAndroid.getInstance().getContext();
        boolean installed = false;
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        List<ApplicationInfo> installedApplications = context.getPackageManager()
                .getInstalledApplications(0);
        for (ApplicationInfo in : installedApplications) {
            if (packageName.equals(in.packageName)) {
                installed = true;
                break;
            } else {
                installed = false;
            }
        }
        return installed;
    }



    /**
     * 获取包名最后一个节点名
     *
     * @return
     */
    public static String getPackageNameLast() {

        String packageName = EasyAndroid.getInstance().getContext().getPackageName();

        String[] split = packageName.split("\\.");

        if (split.length > 0) {
            String s = split[split.length - 1];
            return s;

        }
        return "";
    }


    /**
     * 结束进程
     */
    public static void killProcesses() {

        Context context = EasyAndroid.getInstance().getContext();
        android.app.ActivityManager activityManager = (ActivityManager) context.getSystemService(Context
                .ACTIVITY_SERVICE);
        String packageName;
        try {
            if (!getPackageName().contains(":")) {
                packageName = getPackageName();
            } else {
                packageName = getPackageName().split(":")[0];
            }
            activityManager.killBackgroundProcesses(packageName);
            Method forceStopPackage = activityManager.getClass().getDeclaredMethod
                    ("forceStopPackage", String.class);
            forceStopPackage.setAccessible(true);
            forceStopPackage.invoke(activityManager, packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取手机系统版本号
     * 获取系统版本常量 Build.VERSION_CODES.O
     * @return
     */
    public static int getSystemVersion() {
        int systemVersion;
        try {
            systemVersion = android.os.Build.VERSION.SDK_INT;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            systemVersion = 0;
        }
        return systemVersion;
    }



    public static boolean isDebug() {
        try {
            ApplicationInfo info = EasyAndroid.getInstance().getContext().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }


}
