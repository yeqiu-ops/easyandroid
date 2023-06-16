package com.yeqiu.easyandroid.network.capture;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yeqiu.easyandroid.databinding.ItemNetLogBinding;
import com.yeqiu.easyandroid.utils.CommonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @project: Answer
 * @author: 小卷子
 * @date: 2022/5/6
 * @describe:
 * @fix:
 */
public class NetLogAdapter extends RecyclerView.Adapter<NetLogAdapter.ViewHolder> {

    private List<NetLog> netLogs;
    private int index;
    private int pageSize;
    private OnItemClickListener onItemClickListener;

    public NetLogAdapter(int pageSize, OnItemClickListener onItemClickListener) {
        this.pageSize = pageSize;
        this.onItemClickListener = onItemClickListener;
        if (CommonUtil.isEmpty(this.netLogs)) {
            this.netLogs = new ArrayList<>();
        }
    }


    public void setNewData(List<NetLog> netLogs, int index) {
        this.netLogs = netLogs;
        this.index = index;
        if (CommonUtil.isEmpty(this.netLogs)) {
            this.netLogs = new ArrayList<>();
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemNetLogBinding binding = ItemNetLogBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        NetLog netLog = netLogs.get(position);
        holder.binding.tvIndex.setText(String.valueOf(((index -1) * pageSize) + position+1));
        holder.binding.tvDate.setText(getDataFromTime(netLogs.get(position).getDate()));
        Uri uri = Uri.parse(netLog.getRequestUrl());
        holder.binding.tvHost.setText(uri.getHost());
        holder.binding.tvPath.setText(uri.getPath());
        holder.binding.tvStatusCode.setText(String.valueOf(netLog.getStatusCode()));
        holder.binding.tvStatusCode.setTextColor(netLog.getStatusCode() == 200 ? Color.BLACK : Color.RED);


        try {
            String responseBody = netLog.getResponseBody();
            JSONObject jsonObject = new JSONObject(responseBody);
            String code = jsonObject.getString("code");
            holder.binding.tvDataCode.setText(code);
            holder.binding.tvDataCode.setTextColor(code.equals("0") ? Color.BLACK : Color.RED);
        } catch (JSONException e) {
            e.printStackTrace();
            holder.binding.tvDataCode.setText("null");
            holder.binding.tvDataCode.setTextColor( Color.RED);
        }


        holder.binding.tvReceivedIn.setText(netLog.getTime());

        holder.binding.llLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(holder.getAdapterPosition(), netLogs.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return netLogs.size();
    }


    public String getDataFromTime(Long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = sdf.parse(sdf.format(time));
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemNetLogBinding binding;

        public ViewHolder(ItemNetLogBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, NetLog netLog);
    }

}
