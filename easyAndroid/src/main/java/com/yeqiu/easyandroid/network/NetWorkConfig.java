package com.yeqiu.easyandroid.network;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.safframework.http.interceptor.AndroidLoggingInterceptor;
import com.yeqiu.easyandroid.network.capture.LogCaptureInterceptor;
import com.yeqiu.easyandroid.utils.APPInfoUtil;

import org.litepal.LitePal;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import cn.netdiscovery.http.interceptor.LoggingInterceptor;
import okhttp3.OkHttpClient;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/11
 * @describe:
 * @fix:
 */
public class NetWorkConfig {

    public static volatile NetWorkConfig instance;
    private NetDataHandler netDataHandler;

    public static final String netTag = "netWork_tag";


    private NetWorkConfig() {
    }

    public static NetWorkConfig getInstance() {
        synchronized (NetWorkConfig.class) {
            if (instance == null) {
                instance = new NetWorkConfig();
            }
            return instance;
        }
    }

    public void init(Application app, NetDataHandler netDataHandler) {

        this.netDataHandler = netDataHandler;
        init(app);
    }

    public void init(Application app) {
        //okgo 默认日志
        HttpLoggingInterceptor okgoLogInterceptor = new HttpLoggingInterceptor("OkGo");
        okgoLogInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        okgoLogInterceptor.setColorLevel(Level.INFO);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .writeTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getX509TrustManager())//配置证书
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())//配置主机名验证
                .addInterceptor(getLoggingInterceptor())
                .addInterceptor(okgoLogInterceptor);

        //设置日志拦截器
        //配置数据库
        LitePal.initialize(app);
        builder.addInterceptor(new LogCaptureInterceptor());

        //初始化okgo
        OkGo.getInstance().init(app)
                .setOkHttpClient(builder.build())
                .setRetryCount(3);


    }



    private LoggingInterceptor getLoggingInterceptor() {

        //是否打印日志
        boolean isPrintLog = true;

        String appName = APPInfoUtil.getAppName();
        String requestTag = netTag + " ==========> 请求开始";
        String responseTag = netTag + " <========== 请求结束";

        LoggingInterceptor loggingInterceptor = AndroidLoggingInterceptor.build(isPrintLog, true,
                requestTag, responseTag);

        return loggingInterceptor;

    }

    public NetDataHandler getNetDataHandler() {
        return netDataHandler;
    }



}

