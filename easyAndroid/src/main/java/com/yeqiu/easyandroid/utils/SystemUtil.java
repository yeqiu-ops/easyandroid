package com.yeqiu.easyandroid.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.text.TextUtils;

import com.yeqiu.easyandroid.EasyAndroid;

import java.util.List;

/**
 * @project: LearningMachine
 * @author: 小卷子
 * @date: 2022/7/27
 * @describe:
 * @fix:
 */
public class SystemUtil {

    /**
     * 判断网络是否可用
     *
     * @return
     */
    public static boolean isNetWorkConnected() {

        Context context = EasyAndroid.getInstance().getContext();

        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
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



    public static void rebootApp(Context context,long delayed){

        String packageName = APPInfoUtil.getPackageName();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //设置重启延时时间
        long triggerAtDelayed = SystemClock.elapsedRealtime() + delayed;

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtDelayed,pendingIntent);
        ActivityManager.getInstance().finishAllActivity();


    }



}
