package com.yeqiu.easyandroid.network.capture.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.ScrollView;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.yeqiu.easyandroid.databinding.ActivityNetLogDetailBinding;
import com.yeqiu.easyandroid.network.capture.NetLog;
import com.yeqiu.easyandroid.ui.base.BaseBindActivity;
import com.yeqiu.easyandroid.utils.CommonUtil;
import com.yeqiu.easyandroid.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

/**
 * @project: LearningMachine
 * @author: 小卷子
 * @date: 2022/7/13
 * @describe:
 * @fix:
 */
public class NetLogDetailActivity extends BaseBindActivity<ActivityNetLogDetailBinding> {

    @Override
    public void setContentView(View view) {
        setStatusBar();
        super.setContentView(view);
    }

    private void setStatusBar() {
        //沉浸式
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(),getWindow().getDecorView());
        controller.show(WindowInsetsCompat.Type.statusBars());
        controller.setAppearanceLightStatusBars(true);
    }

    @Override
    protected void init() {

        NetLog netLog = (NetLog) getIntent().getSerializableExtra("data");

        if (CommonUtil.isEmpty(netLog)) {
            ToastUtil.showToast("未获取到日志信息");
            return;
        }
        binding.tvLog.setText(netLog.toString());


        binding.ivCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String log = binding.tvLog.getText().toString();
                if (CommonUtil.isEmpty(log)) {
                    ToastUtil.showToast("无日志");
                    return;
                }

                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("copy", log);
                if (cm != null) {
                    cm.setPrimaryClip(mClipData);
                    ToastUtil.showToast("复制成功");
                }
            }
        });

        binding.ivScreenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });

    }


    private void saveImage() {

        String path = getExternalFilesDir("netcapture").getAbsoluteFile().getAbsolutePath();

        Bitmap bitmapByView = shotScrollView( binding.slScroll);
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        File file = new File(dir + "/", df.format(System.currentTimeMillis()) + ".png");

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
            ToastUtil.showToast("已经保存信息截图");

        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showToast("保存崩溃信息失败");

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



}
