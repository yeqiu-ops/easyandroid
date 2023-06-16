package com.yeqiu.easyandroid.crashkit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.yeqiu.easyandroid.BuildConfig;
import com.yeqiu.easyandroid.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class CrashKit implements Thread.UncaughtExceptionHandler {


    private static CrashKit crashKit;
    private Application app;
    private List<Activity> activities = new ArrayList<>();
    private boolean enable = BuildConfig.DEBUG;
    private OnCrashHappenListener onCrashHappenListener;



    private CrashKit() {
    }

    public static synchronized CrashKit getInstance() {
        if (crashKit == null) {
            crashKit = new CrashKit();
        }
        return crashKit;
    }

    public void init(Application app) {

        if (crashKit == null) {
            return;
        }
        this.app = app;
        registerActivityListener(app);
        Thread.setDefaultUncaughtExceptionHandler(crashKit);
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setOnCrashHappenListener(OnCrashHappenListener onCrashHappenListener) {
        this.onCrashHappenListener = onCrashHappenListener;
    }

    @Override
    public void uncaughtException(Thread t, Throwable throwable) {
        //打印异常信息
        LogUtil.logException(throwable);

        if (onCrashHappenListener!=null){
            onCrashHappenListener.OnCrashHappen(throwable);
        }

        if (!enable) {
            return;
        }

        CrashModel model = CrashUtils.parseCrash(throwable);
        handleException(model);

        for (Activity activity : activities) {
            if (activity.getClass().equals(CrashActivity.class)) {
                continue;
            }
            activity.finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());

    }

    private void handleException(CrashModel model) {


        Intent intent = new Intent(getContext(), CrashActivity.class);
        intent.putExtra(CrashActivity.CRASH_MODEL, model);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        app.startActivity(intent);
    }


    public Context getContext() {
        if (app == null) {
            throw new NullPointerException("Please call init method in Application onCreate");
        }
        return app;
    }


    private void registerActivityListener(Application application) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    /**
                     *  监听到 Activity创建事件 将该 Activity 加入list
                     */
                    activities.add(activity);
                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    if (null == activities && activities.isEmpty()) {
                        return;
                    }
                    if (activities.contains(activity)) {
                        /**
                         *  监听到 Activity销毁事件 将该Activity 从list中移除
                         */
                        activities.remove(activity);
                    }
                }
            });
        }
    }
}