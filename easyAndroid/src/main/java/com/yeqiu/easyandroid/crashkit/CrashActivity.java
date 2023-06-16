package com.yeqiu.easyandroid.crashkit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.ViewCompat;

import com.yeqiu.easyandroid.R;
import com.yeqiu.easyandroid.utils.LogUtil;
import com.yeqiu.easyandroid.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/15
 * @describe:
 * @fix:
 */
public class CrashActivity extends AppCompatActivity {

    public static final String CRASH_MODEL = "crash_model";
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
    private CrashModel model;

    private ScrollView sScrollView;
    private ViewGroup sToolbar;

    private File screenshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);

        setStatusBarTransparent();
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar!=null){
            supportActionBar.hide();
        }

        model = getIntent().getParcelableExtra(CRASH_MODEL);
        if (model == null) {
            return;
        }
        sScrollView = findViewById(R.id.sScrollView);
        sToolbar = findViewById(R.id.sToolbar);
        TextView textMessage = findViewById(R.id.sTextMessage);
        TextView tv_className = findViewById(R.id.sTvClassName);
        TextView tv_methodName = findViewById(R.id.sTvMethodName);
        TextView tv_lineNumber = findViewById(R.id.sTvLineNumber);
        TextView tv_exceptionType = findViewById(R.id.sTvExceptionType);
        TextView tv_fullException = findViewById(R.id.sTvFullException);
        TextView tv_time = findViewById(R.id.sTvTime);
        TextView tv_model = findViewById(R.id.sTvModel);
        TextView tv_brand = findViewById(R.id.sTvBrand);
        TextView tv_version = findViewById(R.id.sTvVersion);
        TextView tv_Share = findViewById(R.id.sTvShare);
        TextView tv_cpuAbi = findViewById(R.id.sTvCpuAbi);
        TextView tv_versionCode = findViewById(R.id.sTvVersionCode);
        TextView tv_versionName = findViewById(R.id.sTvVersionName);
        textMessage.setText(model.getExceptionMsg());
        tv_className.setText(model.getFileName());
        tv_methodName.setText(model.getMethodName());
        tv_lineNumber.setText(String.valueOf(model.getLineNumber()));
        tv_exceptionType.setText(model.getExceptionType());
        tv_fullException.setText(model.getFullException());
        tv_time.setText(df.format(model.getTime()));

        CrashModel.Device device = model.getDevice();
        tv_model.setText(model.getDevice().getModel());
        tv_brand.setText(model.getDevice().getBrand());
        String platform = "Android " + device.getRelease() + "-" + device.getVersion();
        tv_version.setText(platform);

        String cpuAbi = device.getCpuAbi();
        tv_cpuAbi.setText(cpuAbi);

        tv_versionCode.setText(model.getVersionCode());
        tv_versionName.setText(model.getVersionName());


        //自动保存崩溃图片
        sScrollView.post(new Runnable() {
            @Override
            public void run() {
                saveImage();
            }
        });


        tv_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage();
            }
        });
    }

    private void saveImage() {

        String path = getExternalFilesDir("crash").getAbsoluteFile() + "/"
                + model.getExceptionType() + "_" + System.currentTimeMillis() + ".png";

        Bitmap bitmapByView = shotScrollView( sScrollView);
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(file);
            bitmapByView.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            sendBroadcast(new Intent(Intent
                    .ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse
                    ("file://" + file.getAbsolutePath())));

            this.screenshot = file;
            ToastUtil.showToast("已经保存信息截图");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.logException(e);
            ToastUtil.showToast("保存崩溃信息失败"+e.getMessage());
        }
    }


    public static Bitmap shotScrollView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }


    private void shareImage() {

        if ( screenshot == null){
            saveImage();
            //保存后重新调起分享
            shareImage();
        }

        if ( !screenshot.exists()) {
            ToastUtil.showToast("保存截图失败");
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(),
                    getApplicationContext().getPackageName() + ".provider", screenshot);
            intent.putExtra(Intent.EXTRA_STREAM, contentUri);
        } else {
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(screenshot));
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "分享到:"));
    }


    /**
     * 把状态栏设成透明
     */
    protected void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            decorView.setOnApplyWindowInsetsListener((v, insets) -> {
                WindowInsets defaultInsets = v.onApplyWindowInsets(insets);
                return defaultInsets.replaceSystemWindowInsets(
                        defaultInsets.getSystemWindowInsetLeft(),
                        0,
                        defaultInsets.getSystemWindowInsetRight(),
                        defaultInsets.getSystemWindowInsetBottom());
            });
            ViewCompat.requestApplyInsets(decorView);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
        }
    }


}
