package com.yeqiu.easyandroid.network;

import com.lzy.okgo.request.base.Request;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/11
 * @describe: 处理网络请求数据是否正确的逻辑
 * @fix:
 */
public interface NetDataHandler {


    /**
     * 请求开始前，可以在这里设置公共参数
     * @param request
     */
    void beforeStart(Request request);


    /**
     * 网络请求成功，分析数据是否合法
     * @param data
     * @param <T>
     * @return
     */
    <T>NetDataHandlerResult isSucceed(T data);

    /**
     * 失败的默认处理
     * @param code
     * @param msg
     */
    void onErrorDefaultHandler(long code, String msg);

}
