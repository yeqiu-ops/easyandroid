package com.yeqiu.easyandroid.ui.dialog;


import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.yeqiu.easyandroid.R;
import com.yeqiu.easyandroid.databinding.DialogLoadingBinding;
import com.yeqiu.easyandroid.utils.CommonUtil;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/11
 * @describe:
 * @fix:
 */
public class LoadingDialog extends BaseDialog<DialogLoadingBinding, LoadingDialog> {


    private String tipText;
    @DrawableRes
    private int imgRes = R.drawable.icon_loading;

    private ObjectAnimator animator;
    private Type type = Type.loading;

    public enum Type {
        loading,
        progress,
        success,
        warning,
        error
    }


    @Override
    protected void initData() {

        if (type == Type.loading) {
            //默认显示文字
            tipText = "正在加载...";
        }


        binding.ivLoading.setImageResource(imgRes);
        if (CommonUtil.isNotEmpty(tipText)) {
            binding.tvInfo.setVisibility(View.VISIBLE);
            binding.tvInfo.setText(tipText);
        } else {
            binding.tvInfo.setVisibility(View.GONE);
        }
        setViewImg();
        startAnimate();
    }

    private void setViewImg() {

        switch (type) {
            case loading:
                binding.ivLoading.setImageResource(R.drawable.icon_loading);
                break;
            case progress:
                binding.ivLoading.setImageResource(R.drawable.icon_progress);
                break;
            case success:
                binding.ivLoading.setImageResource(R.drawable.icon_success);
                break;
            case warning:
                binding.ivLoading.setImageResource(R.drawable.icon_warning);
                break;
            case error:
                binding.ivLoading.setImageResource(R.drawable.icon_error);
                break;
            default:
                binding.ivLoading.setImageResource(imgRes);
                break;
        }
    }


    private void startAnimate() {
        animator = getAnimator();
        if (animator != null) {
            animator.start();
        }
    }

    public void stopAnimate() {
        if (animator != null) {
            animator.end();
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        stopAnimate();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

    public ObjectAnimator getAnimator() {

        if (type == Type.loading || type == Type.progress) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(binding.ivLoading, "rotation",
                    0f, 360f);
            animator.setDuration(1000);
            animator.setInterpolator(new LinearInterpolator());
            animator.setRepeatCount(ObjectAnimator.INFINITE);
            animator.setRepeatMode(ObjectAnimator.RESTART);
            return animator;
        }
        // 普通提示 无动画
        return null;


    }


    public LoadingDialog setTipText(String tipText) {
        this.tipText = tipText;
        if (binding != null && binding.tvInfo != null) {
            binding.tvInfo.setText(tipText);
        }
        return this;
    }

    public LoadingDialog setImgRest(@DrawableRes int imgRes) {
        this.imgRes = imgRes;
        if (binding != null && binding.ivLoading != null) {
            binding.ivLoading.setImageResource(imgRes);
        }
        return this;
    }


    public LoadingDialog setType(Type type) {
        this.type = type;
        if (binding != null && binding.ivLoading != null) {
            setViewImg();
            stopAnimate();
            startAnimate();
        }
        return this;

    }


}
