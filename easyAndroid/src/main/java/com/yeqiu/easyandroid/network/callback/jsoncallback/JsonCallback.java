package com.yeqiu.easyandroid.network.callback.jsoncallback;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.base.Request;
import com.yeqiu.easyandroid.network.NetDataHandler;
import com.yeqiu.easyandroid.network.NetDataHandlerResult;
import com.yeqiu.easyandroid.network.NetWorkConfig;
import com.yeqiu.easyandroid.utils.LogUtil;
import com.yeqiu.easyandroid.utils.SystemUtil;

import java.net.ConnectException;
import java.net.UnknownHostException;

import okhttp3.Response;


/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/11
 * @describe:
 * @fix:
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {


    private Object tag;

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        NetDataHandler netDataHandler = NetWorkConfig.getInstance().getNetDataHandler();
        if (netDataHandler != null) {
            netDataHandler.beforeStart(request);
        }

        tag = request.getTag();

        super.onStart(request);
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     */
    @Override
    public T convertResponse(Response response) {

        try {
            JsonConvert<T> jsonConvert = new JsonConvert<>(this);
            return jsonConvert.convertResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
//            onError(103,"数据解析异常");
            Log.e("jsonCallBack", Log.getStackTraceString(e));
            throw new JsonSyntaxException("网络请求错误");
        }

//        return null;
    }


    @Override
    public final void onSuccess(com.lzy.okgo.model.Response<T> response) {

        if (response == null) {
            return;
        }
        T data = response.body();

        if (data == null) {
//            onError(-101, "响应体为空！");
            return;
        }

        //调用宿主是否还活着
        if (tag instanceof LifecycleOwner){
            if (!isLifecycleActive((LifecycleOwner) tag)) {
                LogUtil.i(LogUtil.easyAndroidTag,"调用宿主已销毁，不回调结果");
                return;
            }
        }

        //判断数据是否成功
        if (NetWorkConfig.getInstance().getNetDataHandler() == null) {
            //未配置自定义数据处理，默认按照成功返回
            onSuccess(data);
        } else {
            //用户自定义数据是否成功
            NetDataHandlerResult result = NetWorkConfig.getInstance().getNetDataHandler().isSucceed(data);
            if (result.isSucceed()) {
                onSuccess(data);
            } else {
                onError(result.getCode(), result.getErrorMsg());
            }
        }
    }


    /**
     * 网络请求成功回调，保证data!=null
     *
     * @param response
     */
    public abstract void onSuccess(T response);


    @Override
    public final void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);

        if (tag instanceof LifecycleOwner) {
            if (!isLifecycleActive((LifecycleOwner) tag)) {
                LogUtil.i(LogUtil.easyAndroidTag,"调用宿主已销毁，不回调结果");
                return;
            }
        }

        Throwable exception = response.getException();
        if (response.code() == 504) {
            onError(response.code(), "网络连接超时");
        } else if (exception instanceof JsonSyntaxException) {
            onError(-102, "数据不合法");
        }else if (exception instanceof ConnectException){
            if (!SystemUtil.isNetWorkConnected()){
                onError(0, "当前无网络");
            }else{
                onError(response.code(), "服务器异常，请稍后重试");
            }
        }else if (exception instanceof UnknownHostException){
            if (!SystemUtil.isNetWorkConnected()){
                onError(0, "当前无网络");
            }else{
                onError(response.code(), "服务器异常，请稍后重试");
            }
        }else{
            onError(response.code(), "服务器异常，请稍后重试");
        }
    }


    /**
     * 网络请求失败回调 这里是指网络请求失败了，包括响应体解析失败
     *
     * @param code -100：响应数据为空
     *             -101：响应体为空
     *             -102：数据解析异常
     * @param msg
     */
    public void onError(long code, String msg) {

        if (NetWorkConfig.getInstance().getNetDataHandler() != null) {
            NetWorkConfig.getInstance().getNetDataHandler().onErrorDefaultHandler(code, msg);
        }
    }


    /**
     * 判断宿主是否处于活动状态
     */
    public static boolean isLifecycleActive(LifecycleOwner lifecycleOwner) {

        return lifecycleOwner != null && lifecycleOwner.getLifecycle().getCurrentState() != Lifecycle.State.DESTROYED;
    }


}
