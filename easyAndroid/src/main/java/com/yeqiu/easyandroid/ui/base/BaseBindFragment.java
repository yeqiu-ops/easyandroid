package com.yeqiu.easyandroid.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
public abstract class BaseBindFragment<T extends ViewBinding> extends Fragment {

    protected T binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try {
            ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
            Class<T> clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
            Method inflate = clazz.getMethod("inflate", LayoutInflater.class,ViewGroup.class, boolean.class);
//            Method inflate = clazz.getMethod("inflate", LayoutInflater.class);

            binding = (T) inflate.invoke(null,getLayoutInflater(),container,false);
        } catch (Exception exception) {
            exception.printStackTrace();
            LogUtil.logException(exception);
            throw new EasyAndroidException(exception);
        }

        if (binding!=null){
            return binding.getRoot();
        }else{
            return super.onCreateView(inflater, container, savedInstanceState);
        }

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

   protected abstract void init();
}
