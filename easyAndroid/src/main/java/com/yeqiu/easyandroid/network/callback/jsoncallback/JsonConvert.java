/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yeqiu.easyandroid.network.callback.jsoncallback;

import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.Converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/11
 * @describe:
 * @fix:
 */
public class JsonConvert<T> implements Converter<T> {


    private AbsCallback<T> callback;

    public JsonConvert(AbsCallback<T> callback) {
        this.callback = callback;
    }

    @Override
    public T convertResponse(Response response) throws Exception {

        Type genericType = getGenericType(callback);
        ResponseBody body = response.body();
        if (body == null) {
            return null;
        }
        JsonReader jsonReader = new JsonReader(body.charStream());
        T t = Convert.fromJson(jsonReader, genericType);
        response.close();
        return t;

    }





    /**
     * 获取泛型类型，通过创建的callback实例泛型参数
     * @param callback
     * @return
     */
    public  Type getGenericType(AbsCallback<T> callback) {
        if (callback == null) {
            return Void.class;
        }
        // 获取接口上面的泛型
        Type[] types = callback.getClass().getGenericInterfaces();
        if (types.length > 0) {
            // 如果这个对象是直接实现了接口，并且携带了泛型
            return ((ParameterizedType) types[0]).getActualTypeArguments()[0];
        }

        // 获取父类上面的泛型
        Type genericSuperclass = callback.getClass().getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            return Void.class;
        }

        Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        if (actualTypeArguments.length == 0) {
            return Void.class;
        }
        // 如果这个对象是通过类继承，并且携带了泛型
        return actualTypeArguments[0];
    }


}
