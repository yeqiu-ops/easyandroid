package com.yeqiu.easyandroid.ui.dialog;


import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.yeqiu.easyandroid.databinding.DialogMessageBinding;
import com.yeqiu.easyandroid.ui.dialog.listener.OnMessageDialogListener;
import com.yeqiu.easyandroid.ui.dialog.model.TextInfo;
import com.yeqiu.easyandroid.utils.CommonUtil;
import com.yeqiu.easyandroid.utils.ThreadUtil;


/**
 * @project: DemoApplication
 * @author: 小卷子
 * @date: 2022/9/14
 * @describe:
 * @fix:
 */
public class MessageDialog extends BaseDialog<DialogMessageBinding, MessageDialog> {


    public enum Type {
        message, input
    }


    private String content;
    private String hint;
    private String input;
    private String negativeText;
    private String positiveText;
    private Type type = Type.message;


    protected TextInfo titleTextInfo;
    protected TextInfo contentTextInfo;
    protected TextInfo negativeTextInfo;
    protected TextInfo positiveTextInfo;
    protected TextInfo inputTextInfo;

    private OnMessageDialogListener onMessageDialogListener;


    public MessageDialog setContent(String content) {
        this.content = content;
        return this;
    }

    public MessageDialog setHint(String hint) {
        this.hint = hint;
        return this;
    }

    public MessageDialog setInput(String input) {
        this.input = input;
        return this;
    }

    public MessageDialog setType(Type type) {
        this.type = type;
        return this;
    }


    public MessageDialog setContentTextInfo(TextInfo contentTextInfo) {
        this.contentTextInfo = contentTextInfo;
        return this;
    }

    public MessageDialog setNegativeTextInfo(TextInfo negativeTextInfo) {
        this.negativeTextInfo = negativeTextInfo;
        return this;
    }

    public MessageDialog setPositiveTextInfo(TextInfo positiveTextInfo) {
        this.positiveTextInfo = positiveTextInfo;
        return this;
    }

    public MessageDialog setInputTextInfo(TextInfo inputTextInfo) {
        this.inputTextInfo = inputTextInfo;
        return this;
    }

    public MessageDialog setNegativeText(String negativeText) {
        this.negativeText = negativeText;
        return this;
    }

    public MessageDialog setPositiveText(String positiveText) {
        this.positiveText = positiveText;
        return this;
    }

    @Override
    protected void initData() {

        setTextInfo();
        setText();
        binding.tvContent.setVisibility(!(TextUtils.isEmpty(content)) ? View.VISIBLE : View.GONE);
        binding.etInput.setVisibility(type == Type.input ? View.VISIBLE : View.GONE);
        if (TextUtils.isEmpty(negativeText)&&CommonUtil.isEmpty(negativeTextInfo.getText())) {
            //不显示取消
            binding.tvNegative.setVisibility(View.GONE);
            binding.vVerticalLine.setVisibility(View.GONE);
        } else {
            binding.tvNegative.setVisibility(View.VISIBLE);
            binding.vVerticalLine.setVisibility(View.VISIBLE);
        }

        if (type == Type.input){
            //弹出键盘
            ThreadUtil.runOnMainThreadDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.etInput.setFocusable(true);
                    binding.etInput.requestFocus();
                    InputMethodManager imm = (InputMethodManager)  binding.etInput.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (null != imm) {
                        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                    }
                }
            },500);

        }

    }


    private void setTextInfo() {

        if (titleTextInfo == null) {
            titleTextInfo = new TextInfo(getTitle());
            titleTextInfo.setBold(true);
            titleTextInfo.setFontColor(Color.BLACK);
            titleTextInfo.setFontSize(18);
        }
        if (contentTextInfo == null) {
            contentTextInfo = new TextInfo(content);
            contentTextInfo.setFontColor(Color.BLACK);
            contentTextInfo.setFontSize(16);
        }
        if (negativeTextInfo == null) {
            negativeTextInfo = new TextInfo(negativeText);
            negativeTextInfo.setFontColor(Color.BLACK);
            negativeTextInfo.setFontSize(16);
            negativeTextInfo.setBold(true);
        }
        if (positiveTextInfo == null) {
            if (CommonUtil.isEmpty(positiveText)){
                positiveText = "确认";
            }
            positiveTextInfo = new TextInfo(positiveText);
            positiveTextInfo.setFontColor(Color.BLACK);
            positiveTextInfo.setFontSize(16);
            positiveTextInfo.setBold(true);
        }
        if (type == Type.input) {
            if (inputTextInfo == null) {
                inputTextInfo = new TextInfo(input);
                inputTextInfo.setFontColor(Color.BLACK);
                inputTextInfo.setFontSize(16);
            }
        }
    }

    private void setText() {

        setTextInfoToText(binding.tvTitle, titleTextInfo);
        setTextInfoToText(binding.tvContent, contentTextInfo);
        setTextInfoToText(binding.tvNegative, negativeTextInfo);
        setTextInfoToText(binding.tvPositive, positiveTextInfo);

        if (type == Type.input) {
            setTextInfoToText(binding.etInput, inputTextInfo);
            binding.etInput.setHint(hint);
            binding.etInput.setText(input);
        } else {
            binding.etInput.setText(null);
        }

    }


    @Override
    protected void initListener() {

        binding.tvNegative.setOnClickListener(this);
        binding.tvPositive.setOnClickListener(this);

    }


    public MessageDialog setOnMessageDialogListener(OnMessageDialogListener onMessageDialogListener) {
        this.onMessageDialogListener = onMessageDialogListener;
        return this;
    }

    @Override
    public void onClick(View view) {
        if (view == binding.tvNegative) {
            if (onMessageDialogListener != null) {
                onMessageDialogListener.onNegativeClick();
            }
            dismiss();
        } else if (view == binding.tvPositive) {
            if (onMessageDialogListener != null) {
                onMessageDialogListener.onPositiveClick(binding.etInput.getText().toString());
            }
            dismiss();
        }
    }
}
