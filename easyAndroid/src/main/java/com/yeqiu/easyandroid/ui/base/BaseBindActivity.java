package com.yeqiu.easyandroid.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.yeqiu.easyandroid.exception.EasyAndroidException;
import com.yeqiu.easyandroid.utils.LogUtil;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/13
 * @describe:
 * @fix:
 */
public abstract class BaseBindActivity<T extends ViewBinding> extends AppCompatActivity {

    protected T binding;
    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        try {
            //获取父类的泛型
            ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
            //获取第一个泛型参数
            Class<T> clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
            //反射调用
            Method inflate = clazz.getMethod("inflate", LayoutInflater.class);
            binding = (T) inflate.invoke(null, getLayoutInflater());
            setContentView(binding.getRoot());
            init();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.logException(e);
            throw new EasyAndroidException(e);
        }
    }


    protected abstract void init();

    public Context getContext() {
        return context;
    }
}


