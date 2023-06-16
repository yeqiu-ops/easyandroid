package com.yeqiu.easyandroid.utils;


import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.yeqiu.easyandroid.EasyAndroid;
import com.yeqiu.easyandroid.R;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/11
 * @describe:
 * @fix:
 */
public class ToastUtil {


    private static String oldMsg;
    private static long time;

    public static void showToast(@StringRes int stringId) {

        showToast(ResourceUtil.getString(stringId));
    }



    public static void showToast(String msg) {

        if (TextUtils.isEmpty(msg)) {
            return;
        }
        // 当显示的内容不一样时，即断定为不是同一个Toast
        if (!msg.equals(oldMsg)) {
            Toast toast = makeToast(msg);
            show(toast);
            time = System.currentTimeMillis();
        } else {
            // 显示内容一样时，只有间隔时间大于2秒时才显示
            if (System.currentTimeMillis() - time > 2000) {
                Toast toast = makeToast(msg);
                show(toast);
                time = System.currentTimeMillis();
            }
        }
        oldMsg = msg;
    }


    private static Toast makeToast(String msg){
        Context context = EasyAndroid.getInstance().getContext();
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
        toast.setView(view);
        TextView tvToast = view.findViewById(R.id.tv_toast);
        tvToast.setText(msg);
        return toast;
    }


    private static void show(final Toast toast) {

        if (Looper.myLooper() == Looper.getMainLooper()) {
            toast.show();
        } else {
            ThreadUtil.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    toast.show();
                }
            });
        }


    }



}
