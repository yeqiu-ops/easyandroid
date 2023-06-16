package com.yeqiu.easyandroid.utils;


import android.util.Pair;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/11
 * @describe:
 * @fix:
 */
public class JsonUtil {

    private static Gson gson;

    public static Gson getGson() {

        if (gson == null) {
            gson = new Gson();
        }

        return gson;
    }


    /**
     * json转对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (CommonUtil.isEmpty(json)){
            return null;
        }

        T t = getGson().fromJson(json, clazz);
        return t;
    }

    public static <T> List<T> fromJsonToArray(String json, Class<T> clazz) {
        if (CommonUtil.isEmpty(json)){
            return null;
        }
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }


    public static <T> T fromJson(String json, Type type) {
        T t = getGson().fromJson(json, type);
        return t;
    }


    public static <T> String toJson(T t) {

        return getGson().toJson(t);
    }

    public static JSONObject generateJson(Pair... fields) {

        JSONObject jsonObject = new JSONObject();
        try {
            for (Pair pair : fields) {
                jsonObject.put((String) pair.first, pair.second);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


    public static Map<String, String> jsonToMap(JSONObject jsonObject) {

        Map<String, String> map = new HashMap();
        try {
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = jsonObject.get(key).toString();
                map.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }


    public static Type getGenericType(Object object) {
        if (object == null) {
            return Void.class;
        }
        // 获取接口上面的泛型
        Type[] types = object.getClass().getGenericInterfaces();
        if (types.length > 0) {
            // 如果这个对象是直接实现了接口，并且携带了泛型
            return ((ParameterizedType) types[0]).getActualTypeArguments()[0];
        }

        // 获取父类上面的泛型
        Type genericSuperclass = object.getClass().getGenericSuperclass();
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
