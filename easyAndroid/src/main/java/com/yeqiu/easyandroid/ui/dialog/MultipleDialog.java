package com.yeqiu.easyandroid.ui.dialog;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yeqiu.easyandroid.R;
import com.yeqiu.easyandroid.databinding.DialogMultipleBinding;
import com.yeqiu.easyandroid.ui.dialog.exception.DialogException;
import com.yeqiu.easyandroid.ui.dialog.listener.OnMultipleDialogListener;
import com.yeqiu.easyandroid.ui.dialog.model.TextInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @project: DemoApplication
 * @author: 小卷子
 * @date: 2022/9/15
 * @describe:
 * @fix:
 */
public class MultipleDialog extends BaseDialog<DialogMultipleBinding, MultipleDialog> {

    private String content;
    private TextInfo commonTextInfo;
    private List<String> items;
    private List<TextInfo> itemTextInfo;
    protected TextInfo contentTextInfo;
    private OnMultipleDialogListener onMultipleDialogListener;


    @Override
    protected void initData() {
        setTextInfo();
        setText();
        binding.tvContent.setVisibility(!(TextUtils.isEmpty(content)) ? View.VISIBLE : View.GONE);
        binding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemAdapter itemAdapter = new ItemAdapter(itemTextInfo);
        binding.rvList.setAdapter(itemAdapter);
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
        if (items == null || items.size() == 0) {
            throw new DialogException("items  can't be empty!!!");
        }
        if (itemTextInfo == null) {
            itemTextInfo = new ArrayList<>();
            for (String item : items) {
                TextInfo textInfo;
                if (commonTextInfo != null) {
                    textInfo = new TextInfo(commonTextInfo);
                    textInfo.setText(item);
                } else {
                    //使用默认样式
                    textInfo = new TextInfo(item);
                    textInfo.setFontColor(Color.BLACK);
                    textInfo.setFontSize(16);
                    textInfo.setBold(true);
                }
                itemTextInfo.add(textInfo);
            }
        } else {
            if (itemTextInfo.size() != items.size()) {
                throw new DialogException("itemTextInfo size error !!!");
            }
        }
    }

    private void setText() {
        setTextInfoToText(binding.tvTitle, titleTextInfo);
        setTextInfoToText(binding.tvContent, contentTextInfo);
    }


    public MultipleDialog setOnMultipleDialogListener(OnMultipleDialogListener onMultipleDialogListener) {
        this.onMultipleDialogListener = onMultipleDialogListener;
        return this;
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View view) {

    }



    public MultipleDialog setItems(List<String> items) {
        if (items.size() > 4) {
            throw new DialogException("items max size can't exceed 4 !!!");

        }
        this.items = items;
        return this;
    }

    public MultipleDialog setItemTextInfo(List<TextInfo> itemTextInfo) {
        if (itemTextInfo.size() > 4) {
            throw new DialogException("items max size can't exceed 4 !!!");
        }
        this.itemTextInfo = itemTextInfo;
        return this;
    }


    public MultipleDialog setContent(String content) {
        this.content = content;
        return this;
    }


    public MultipleDialog setContentTextInfo(TextInfo contentTextInfo) {
        this.contentTextInfo = contentTextInfo;
        return this;
    }

    public MultipleDialog setCommonTextInfo(TextInfo commonTextInfo) {
        this.commonTextInfo = commonTextInfo;
        return this;
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemAdapterViewHolder> {


        private List<TextInfo> items;

        public ItemAdapter(List<TextInfo> items) {

            this.items = items;
        }

        @NonNull
        @Override
        public ItemAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
            ItemAdapterViewHolder itemAdapterViewHolder = new ItemAdapterViewHolder(view);
            return itemAdapterViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ItemAdapterViewHolder holder, int position) {

            TextInfo textInfo = items.get(position);
            if (textInfo.getFontSize() > 0) {
                holder.tvItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, textInfo.getFontSize());
            }
            if (textInfo.getFontColor() != 1) {
                holder.tvItem.setTextColor(textInfo.getFontColor());
            }

            if (textInfo.getMaxLines() != -1) {
                holder.tvItem.setMaxLines(textInfo.getMaxLines());
            } else if (textInfo.getMaxLines() == 1) {
                //单行
                holder.tvItem.setSingleLine(true);
            } else {
                holder.tvItem.setMaxLines(Integer.MAX_VALUE);
            }

            holder.tvItem.getPaint().setFakeBoldText(textInfo.getBold());
            holder.tvItem.setText(textInfo.getText());

            int fPosition = position;
            holder.tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMultipleDialogListener != null) {
                        onMultipleDialogListener.onItemClick(fPosition);
                    }
                    dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            return Math.min(items.size(), 4);
        }

        public class ItemAdapterViewHolder extends RecyclerView.ViewHolder {

            public TextView tvItem;

            public ItemAdapterViewHolder(@NonNull View itemView) {
                super(itemView);
                tvItem = itemView.findViewById(R.id.tv_item);
            }
        }

    }

}
