package com.yeqiu.easyandroid.ui.dialog;

import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
import static android.view.WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewbinding.ViewBinding;

import com.yeqiu.easyandroid.ui.dialog.exception.DialogException;
import com.yeqiu.easyandroid.ui.dialog.listener.OnDialogDismissListener;
import com.yeqiu.easyandroid.ui.dialog.model.TextInfo;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * @project: DemoApplication
 * @author: 小卷子
 * @date: 2022/9/14
 * @describe:
 * @fix:
 */
public abstract class BaseDialog<T extends ViewBinding, Self extends BaseDialog>
        extends DialogFragment implements View.OnClickListener {

    protected T binding;
    protected OnDialogDismissListener onDialogDismiss;
    private FragmentActivity activity;

    protected boolean isCanceledOnTouchOutside;

    protected String title;
    protected TextInfo titleTextInfo;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        try {
            //获取父类的泛型
            ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
            //获取第一个泛型参数
            Class<T> clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
            //反射调用
            Method inflate = null;
            inflate = clazz.getMethod("inflate", LayoutInflater.class);
            binding = (T) inflate.invoke(null, getLayoutInflater());
            return binding.getRoot();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DialogException("can't create dialogView!!!");
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() == null) {
            return;
        }
        Window window = getDialog().getWindow();
        if (window == null) {
            return;
        }

        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.dimAmount = getCover();
        layoutParams.format = PixelFormat.TRANSPARENT;
        setSize(layoutParams);
        setGravity(layoutParams);
        window.setAttributes(layoutParams);
        window.addFlags(FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setPadding(0, 0, 0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            layoutParams.layoutInDisplayCutoutMode = LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        init();


    }

    protected float getCover() {
        return 0.5f;
    }

    protected void setSize(WindowManager.LayoutParams layoutParams) {
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    protected void setGravity(WindowManager.LayoutParams layoutParams) {
        layoutParams.gravity = Gravity.CENTER;
    }


    private void init() {
        initData();
        initListener();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);


        //点击其他区域是否消失
        dialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        return dialog;
    }

    private Self getSelf() {

        return (Self) this;
    }


    public Self setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        isCanceledOnTouchOutside = canceledOnTouchOutside;
        return getSelf();
    }

    public Self show(AppCompatActivity activity) {
        this.activity = activity;
        show(activity.getSupportFragmentManager());
        return getSelf();
    }

    public Self show(Fragment fragment) {
        this.activity = fragment.getActivity();
        show(fragment.getChildFragmentManager());
        return getSelf();
    }


    public Self show(FragmentManager manager) {
        this.show(manager, getClass().getSimpleName());
        return getSelf();
    }

    @Override
    public void show(FragmentManager manager, String tag) {

        if (manager == null) {
            throw new DialogException("FragmentManager can't be null!!!");
        }

        if (manager.isDestroyed()){
            //FragmentManager 已经被销毁
            return;
        }


        manager.executePendingTransactions();
        FragmentTransaction ft = manager.beginTransaction();
        if (!this.isAdded()){
            ft.add(this, tag);
        }
        ft.show(this);
        ft.commitAllowingStateLoss();

    }


    public Self setOnDialogDismiss(OnDialogDismissListener onDialogDismiss) {
        this.onDialogDismiss = onDialogDismiss;
        return (Self) this;
    }

    @Override
    public void dismiss() {
        super.dismissAllowingStateLoss();
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDialogDismiss != null) {
            onDialogDismiss.onDialogDismiss();
        }
    }


    protected void setTextInfoToText(TextView textView, TextInfo textInfo) {
        if (textInfo == null || textView == null) {
            return;
        }
        if (textInfo.getFontSize() > 0) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textInfo.getFontSize());
        }
        if (textInfo.getFontColor() != 1) {
            textView.setTextColor(textInfo.getFontColor());
        }

        if (textInfo.getMaxLines() != -1) {
            textView.setMaxLines(textInfo.getMaxLines());
        } else if (textInfo.getMaxLines() == 1) {
            //单行
            textView.setSingleLine(true);
        } else {
            textView.setMaxLines(Integer.MAX_VALUE);
        }

        textView.getPaint().setFakeBoldText(textInfo.getBold());
        textView.setText(textInfo.getText());
    }

    public String getTitle() {
        return title;
    }

    public Self setTitle(String title) {
        this.title = title;
        return (Self) this;
    }

    public TextInfo getTitleTextInfo() {
        return titleTextInfo;
    }

    public Self setTitleTextInfo(TextInfo titleTextInfo) {
        this.titleTextInfo = titleTextInfo;
        return (Self) this;
    }

    public boolean isShow() {

        Dialog dialog = getDialog();
        if (dialog == null) {
            return false;
        } else {
            return dialog.isShowing();
        }

    }

    protected abstract void initData();

    protected abstract void initListener();

}
