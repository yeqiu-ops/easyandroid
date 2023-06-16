package com.yeqiu.easyandroid;

import android.app.Application;
import android.content.Context;

import com.yeqiu.easyandroid.crashkit.CrashKit;
import com.yeqiu.easyandroid.network.NetDataHandler;
import com.yeqiu.easyandroid.network.NetWorkConfig;
import com.yeqiu.easyandroid.utils.LogUtil;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/11
 * @describe:
 * @fix:
 */
public class EasyAndroid {

    private static EasyAndroid instance;
    private Context context;
    private Application app;

    private EasyAndroid() {
    }

    public static synchronized EasyAndroid getInstance() {
        if (instance == null) {
            instance = new EasyAndroid();
        }
        return instance;
    }


    public EasyAndroid init(Application app) {

        if (app == null) {
            throw new NullPointerException("com.bszhihui.login.app can't be null !!!");
        }
        this.app = app;
        this.context = app.getApplicationContext();
        //默认开启 CrashKit
        CrashKit.getInstance().init(app);

        LogUtil.i(LogUtil.easyAndroidTag,"EasyAndroid init");

        return instance;
    }


    /**
     * 开启网络请求
     *
     * @param netDataHandler
     * @return
     */
    public EasyAndroid enableNet(NetDataHandler netDataHandler) {

        if (app == null) {
            throw new NullPointerException("context can't be null,please call init() !!! ");
        }
        NetWorkConfig.getInstance().init(app, netDataHandler);
        return instance;
    }


    public Context getContext() {
        if (context == null) {
            throw new NullPointerException("context can't be null,Please check init() ");
        }
        return context;
    }


}
