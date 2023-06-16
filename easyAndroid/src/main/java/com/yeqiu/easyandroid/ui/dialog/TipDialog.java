package com.yeqiu.easyandroid.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yeqiu.easyandroid.R;
import com.yeqiu.easyandroid.databinding.DialogTipBinding;
import com.yeqiu.easyandroid.ui.dialog.exception.DialogException;
import com.yeqiu.easyandroid.utils.CommonUtil;
import com.yeqiu.easyandroid.utils.ThreadUtil;

/**
 * @project: LearningMachine
 * @author: 小卷子
 * @date: 2022/9/19
 * @describe: 提示弹窗，2秒后自动关闭
 * @fix:
 */
public class TipDialog extends BaseDialog<DialogTipBinding, TipDialog> {


    private String tipText;
    @DrawableRes
    private Integer imgRes;

    @Override
    protected void initData() {

        if (CommonUtil.isEmpty(tipText)) {
            throw new DialogException("tipText can't be null !!!");
        }

        binding.tvTip.setText(tipText);

        if (CommonUtil.isEmpty(imgRes)) {
            binding.ivTip.setVisibility(View.GONE);
        } else {
            binding.ivTip.setVisibility(View.VISIBLE);
            binding.ivTip.setImageResource(imgRes);
        }

        ThreadUtil.runOnMainThreadDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 2000);

//        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_top_enter);
//        animation.setInterpolator(new DecelerateInterpolator());
//        binding.clRoot.startAnimation(animation);
        getDialog().getWindow().setWindowAnimations(R.style.tip_dialog_anim);

    }



    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected float getCover() {
//        return super.getCover();
        return 0;
    }

    @Override
    protected void setGravity(WindowManager.LayoutParams layoutParams) {
//        super.setGravity(layoutParams);
        layoutParams.gravity = Gravity.TOP;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    @Override
    public TipDialog setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        return super.setCanceledOnTouchOutside(true);
    }


    public TipDialog setTipText(String tipText) {
        this.tipText = tipText;
        return this;
    }

    public TipDialog setTipIcon(@DrawableRes Integer imgRes) {
        this.imgRes = imgRes;
        return this;
    }
}
