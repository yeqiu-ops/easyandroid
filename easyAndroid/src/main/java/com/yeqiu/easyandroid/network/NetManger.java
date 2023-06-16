package com.yeqiu.easyandroid.network;

import android.util.Pair;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.yeqiu.easyandroid.network.callback.jsoncallback.JsonCallback;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2021/12/29
 * @describe:
 * @fix:
 */
public class NetManger<T> {



    public  void get(Object tag, String url, JsonCallback<T> callback) {

        OkGo.<T>get(url)
                .tag(tag)
                .execute(callback);
    }


    public  void post(Object tag,String url, JsonCallback<T> callback) {

        OkGo.<T>post(url)
                .tag(tag)
                .execute(callback);
    }

    public  void post(Object tag,String url, String json, JsonCallback<T> callback) {

        OkGo.<T>post(url)
                .tag(tag)
                .upJson(json)
                .execute(callback);
    }

    public  void post(Object tag,String url, JsonCallback<T> callback, Pair<String, Object>... params)  {

        JSONObject jsonObject = new JSONObject();
        try {
            for (Pair<String, Object> pair : params) {
                jsonObject.put(pair.first, pair.second);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("组成请求参数json错误，请查看参数是否合法");
        }

        OkGo.<T>post(url)
                .tag(tag)
                .upJson(jsonObject)
                .execute(callback);

    }


    @SafeVarargs
    public static  void addCommonHeaders(Pair<String,String>... heads){

        for (Pair<String, String> head : heads) {
            HttpHeaders commonHeaders = new HttpHeaders(head.first,head.second);
            OkGo.getInstance().addCommonHeaders(commonHeaders);
        }

    }

    @SafeVarargs
    public static void addCommonParams(Pair<String,String>... params){

        for (Pair<String, String> param : params) {
            HttpParams httpParams = new HttpParams(param.first,param.second);
            OkGo.getInstance().addCommonParams(httpParams);
        }
    }




    public static void cancel(Object tag) {
        OkGo.getInstance().cancelTag(tag);
    }


}
