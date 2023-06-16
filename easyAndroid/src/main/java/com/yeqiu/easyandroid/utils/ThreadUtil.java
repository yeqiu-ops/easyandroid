package com.yeqiu.easyandroid.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/11
 * @describe:
 * @fix:
 */
public class ThreadUtil {


    private static Handler handler = new Handler(Looper.getMainLooper());


    /**
     * 切换到主线程运行
     *
     * @param runnable
     */
    public static void runOnMainThread(Runnable runnable) {


        handler.post(runnable);

    }

    /**
     * 延时切换到主线程运行
     *
     * @param runnable
     */
    public static void runOnMainThreadDelayed(Runnable runnable, long time) {

        handler.postDelayed(runnable, time);

    }


    /**
     * 在子线程运行
     *
     * @param runnable
     */
    public static void runOnChildThread(Runnable runnable) {

        new Thread(runnable).start();

    }


    public static void runOnChildThreadDelayed(Runnable runnable, long time) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(time);
                runnable.run();
            }
        }).start();

    }

}
