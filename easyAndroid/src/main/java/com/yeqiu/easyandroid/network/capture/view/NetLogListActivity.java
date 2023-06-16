package com.yeqiu.easyandroid.network.capture.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.yeqiu.easyandroid.databinding.ActivityNetLogListBinding;
import com.yeqiu.easyandroid.network.capture.NetLog;
import com.yeqiu.easyandroid.network.capture.NetLogAdapter;
import com.yeqiu.easyandroid.network.capture.NetLogDao;
import com.yeqiu.easyandroid.ui.base.BaseBindActivity;
import com.yeqiu.easyandroid.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @project: LearningMachine
 * @author: 小卷子
 * @date: 2022/7/13
 * @describe:
 * @fix:
 */
public class NetLogListActivity extends BaseBindActivity<ActivityNetLogListBinding> implements View.OnClickListener {

    private List<NetLog> netLogs;
    private NetLogAdapter netLogAdapter;
    private int pageSize = 20;
    private int pageNumber = 1;
    private int pageCount;

    public static void startActivity(Context context) {

        Intent intent = new Intent(context, NetLogListActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);

    }


    @Override
    public void setContentView(View view) {
        setStatusBar();
        super.setContentView(view);
    }

    private void setStatusBar() {
        //沉浸式
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.show(WindowInsetsCompat.Type.statusBars());
        controller.setAppearanceLightStatusBars(true);
    }


    @Override
    protected void init() {
        netLogs = new ArrayList<>();
        binding.rvLog.setLayoutManager(new LinearLayoutManager(this));
        netLogAdapter = new NetLogAdapter(pageSize, onItemClickListener);
        binding.rvLog.setAdapter(netLogAdapter);

        initData();
        initListener();

    }

    private void initData() {
        setPage();
        getNetLog();
    }

    private void initListener() {

        binding.ivNextPage.setOnClickListener(this);
        binding.ivPreviousPage.setOnClickListener(this);
        binding.ivClear.setOnClickListener(this);
    }


    private void getNetLog() {

        List<NetLog> page = NetLogDao.findPage(pageSize, pageNumber);
        netLogs = page;
        netLogAdapter.setNewData(netLogs,pageNumber);

        binding.rvLog.scrollToPosition(0);

    }

    private void setPage() {

        pageCount = NetLogDao.getPageCount(pageSize);
        binding.tvTotalPage.setText(String.valueOf(pageCount));
        binding.tvCurrentPage.setText(String.valueOf(pageNumber));
        binding.tvTotalCount.setText("total:"+NetLogDao.getCount());
    }

    private NetLogAdapter.OnItemClickListener onItemClickListener = new NetLogAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position, NetLog netLog) {

            try {
                Intent intent = new Intent(getContext(), NetLogDetailActivity.class);
                intent.putExtra("data", netLog);
                startActivity(intent);
            }catch ( Exception e){
                ToastUtil.showToast("格式化接口数据失败");
            }


        }
    };


    @Override
    public void onClick(View view) {
        if (view == binding.ivPreviousPage) {
            if (pageNumber <= 1) {
                return;
            }
            pageNumber = pageNumber - 1;
            getNetLog();
            binding.tvCurrentPage.setText(String.valueOf(pageNumber));
        } else if (view == binding.ivNextPage) {
            if (pageNumber >= pageCount) {
                return;
            }
            pageNumber = pageNumber + 1;
            getNetLog();
            binding.tvCurrentPage.setText(String.valueOf(pageNumber));
        } else if (view == binding.ivClear) {
            NetLogDao.clear();
            netLogs.clear();
            netLogAdapter.setNewData(netLogs,pageNumber);
            pageCount = 1;
            pageNumber=1;

            binding.tvTotalPage.setText(String.valueOf(pageCount));
            binding.tvCurrentPage.setText(String.valueOf(pageNumber));
            binding.tvTotalCount.setText("total:"+0);
        }
    }
}
