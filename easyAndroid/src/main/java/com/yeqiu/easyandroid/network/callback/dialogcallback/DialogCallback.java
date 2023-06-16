package com.yeqiu.easyandroid.network.callback.dialogcallback;

import androidx.fragment.app.FragmentActivity;

import com.lzy.okgo.request.base.Request;
import com.yeqiu.easyandroid.network.callback.jsoncallback.JsonCallback;
import com.yeqiu.easyandroid.ui.dialog.LoadingDialog;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/11
 * @describe:
 * @fix:
 */
public abstract class DialogCallback<T> extends JsonCallback<T> {


    private LoadingDialog loadingDialog;
    private FragmentActivity activity;

    public DialogCallback(FragmentActivity activity) {
        this.activity = activity;
        loadingDialog = new LoadingDialog();

    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);

        String url = request.getUrl();
        if (loadingDialog != null ) {
            if (activity!=null&&!activity.isFinishing()){
                loadingDialog.show(activity.getSupportFragmentManager(), url);
            }
        }
    }


    @Override
    public void onFinish() {
        super.onFinish();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }
}
