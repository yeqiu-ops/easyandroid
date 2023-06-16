package com.yeqiu.easyandroid.utils;

import android.content.Context;
import android.content.Intent;

import com.yeqiu.easyandroid.EasyAndroid;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/11
 * @describe:
 * @fix:
 */
public class AppUtil {

    /**
     * 打开指定应用
     * 注意 android11以上需要添加查询包权限
     * <manifest xmlns:android="http://schemas.android.com/apk/res/android" package="com.unity3d.player" xmlns:tools="http://schemas.android.com/tools" android:installLocation="preferExternal">
     *   <!--其他配置-->
     *   <queries>
     *     <!-- 声明将要查询的包名 -->
     *     <package android:name="com.VRFab.VRSpaceCycling" />
     *   </queries>
     * </manifest>
     * 详情见：https://www.pudn.com/news/62a09bee85c2c256130c37d6.html#_42
     * @param packageName
     */
    public static void openOtherApp(String packageName) {
        Context context = EasyAndroid.getInstance().getContext();
        context.startActivity(new Intent(context.getPackageManager().getLaunchIntentForPackage
                (packageName)));
    }


    /**
     * 退出app
     */
    public static void exitApp() {
        System.exit(0);

    }




}
