package com.yeqiu.easyandroid.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.yeqiu.easyandroid.EasyAndroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @project：EasyAndroid
 * @author：小卷子
 * @date 2018/9/7
 * @describe：
 * @fix：
 */
public class ResourceUtil {

    /**
     * 获得字符串
     *
     * @param strId 字符串id
     * @return 字符串
     */
    public static String getString(int strId) {
        Context context = EasyAndroid.getInstance().getContext();
        return context.getResources().getString(strId);
    }

    /**
     * 获得颜色
     *
     * @param colorId 颜色id
     * @return 颜色
     */
    public static int getColor(int colorId) {
        Context context = EasyAndroid.getInstance().getContext();
        return ContextCompat.getColor(context, colorId);
    }

    /**
     * 获得Dimension
     *
     * @param dimension
     * @return
     */
    public static int getDimension(int dimension) {
        Context context = EasyAndroid.getInstance().getContext();
        return (int) context.getResources().getDimension(dimension);
    }


    /**
     * 获得Drawable
     *
     * @param drawableId Drawable的id
     * @return Drawable
     */
    public static Drawable getDrawable(int drawableId) {
        Context context = EasyAndroid.getInstance().getContext();
        return ContextCompat.getDrawable(context, drawableId);
    }

    /**
     * 从Assets里读取字符串
     *
     * @param fileName
     * @return
     */
    public static String getStringFromAssets(String fileName) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Context context = EasyAndroid.getInstance().getContext();
        AssetManager assetManager = context.getAssets();
        BufferedReader bf = new BufferedReader(new InputStreamReader(
                assetManager.open(fileName)));
        String line;
        while ((line = bf.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }


}
