package com.yeqiu.easyandroid.ui.dialog;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yeqiu.easyandroid.R;
import com.yeqiu.easyandroid.databinding.DialogBottomSheetBinding;
import com.yeqiu.easyandroid.ui.dialog.exception.DialogException;
import com.yeqiu.easyandroid.ui.dialog.listener.OnBottomSheetDialogListener;
import com.yeqiu.easyandroid.ui.dialog.model.TextInfo;
import com.yeqiu.easyandroid.utils.CommonUtil;
import com.yeqiu.easyandroid.utils.LogUtil;
import com.yeqiu.easyandroid.utils.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @project: DemoApplication
 * @author: 小卷子
 * @date: 2022/9/16
 * @describe:
 * @fix:
 */
public class BottomSheetDialog extends BaseDialog<DialogBottomSheetBinding, BottomSheetDialog> {


    private List<String> items;
    private List<TextInfo> itemTextInfo;
    private TextInfo commonTextInfo;
    /**
     * 选项列表的高度
     */
    private int listHeight = -1;
    /**
     * 列表弹框的列表最多显示几个item 此属性优先于listHeight
     */
    private int listMaxHeightWhitItem = -1;
    private OnBottomSheetDialogListener onBottomSheetDialogListener;
    private String cancelStr;
    private TextInfo cancelTextInfo;

    @Override
    protected void initData() {
        setTextInfo();
        setText();
        binding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemAdapter itemAdapter = new ItemAdapter(itemTextInfo);
        binding.rvList.setAdapter(itemAdapter);
        //设置list高度
        setListHeight();

        getDialog().getWindow().setWindowAnimations(R.style.bottom_dialog_anim);

    }

    private void setListHeight() {

        int height = 0;
        if (listMaxHeightWhitItem != -1) {
            height = getListHeightWhitItem(binding.rvList, listMaxHeightWhitItem);
        } else {
            //按最大高度
            if (listHeight != -1) {
                height = listHeight;
            }
        }
        if (height <= 0) {
            //防止出错
            height = 100;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) binding.rvList.getLayoutParams();
        layoutParams.height = height;
        binding.rvList.setLayoutParams(layoutParams);
    }

    private void setTextInfo() {
        if (titleTextInfo == null) {
            titleTextInfo = new TextInfo(getTitle());
            titleTextInfo.setBold(true);
            titleTextInfo.setFontColor(Color.BLACK);
            titleTextInfo.setFontSize(18);
        }

        if (CommonUtil.isNotEmpty(cancelStr)) {
            if (cancelTextInfo == null) {
                cancelTextInfo = new TextInfo(cancelStr);
                cancelTextInfo.setBold(false);
                cancelTextInfo.setFontColor(Color.BLACK);
                cancelTextInfo.setFontSize(16);
            }
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

        if (CommonUtil.isEmpty(cancelTextInfo)) {
            //不显示取消
            binding.clRoot.setTopLeftCorner(ResourceUtil.getDimension(R.dimen.dp_8))
                    .setTopRightCorner(ResourceUtil.getDimension(R.dimen.dp_8))
                    .setBottomLeftCorner(0)
                    .setBottomRightCorner(0)
                    .refresh();
            binding.clCancel.setVisibility(View.GONE);
        } else {
            binding.clRoot.setCorners(ResourceUtil.getDimension(R.dimen.dp_8))
                    .refresh();
            binding.clCancel.setVisibility(View.VISIBLE);
            setTextInfoToText(binding.tvCancel, cancelTextInfo);
        }

    }

    @Override
    protected void initListener() {

        binding.clCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == binding.clCancel) {
            dismiss();
        }
    }

    @Override
    protected void setSize(WindowManager.LayoutParams layoutParams) {
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected void setGravity(WindowManager.LayoutParams layoutParams) {
        layoutParams.gravity = Gravity.BOTTOM;
    }

    public BottomSheetDialog setItems(List<String> items) {
        this.items = items;
        return this;
    }

    public BottomSheetDialog setItemTextInfo(List<TextInfo> itemTextInfo) {
        this.itemTextInfo = itemTextInfo;
        return this;
    }


    public BottomSheetDialog setListHeight(int listHeight) {
        this.listHeight = listHeight;
        return this;
    }

    public BottomSheetDialog setListMaxHeightWhitItem(int listMaxHeightWhitItem) {
        this.listMaxHeightWhitItem = listMaxHeightWhitItem;
        return this;
    }

    public BottomSheetDialog setOnBottomSheetDialogListener(OnBottomSheetDialogListener onBottomSheetDialogListener) {
        this.onBottomSheetDialogListener = onBottomSheetDialogListener;
        return this;
    }

    public BottomSheetDialog setCommonTextInfo(TextInfo commonTextInfo) {
        this.commonTextInfo = commonTextInfo;
        return this;
    }


    public BottomSheetDialog setCancelStr(String cancelStr) {
        this.cancelStr = cancelStr;
        return this;
    }

    public BottomSheetDialog setCancelTextInfo(TextInfo cancelTextInfo) {
        this.cancelTextInfo = cancelTextInfo;
        return this;
    }


    private int getListHeightWhitItem(RecyclerView recyclerView, int maxItem) {

        RecyclerView.Adapter adapter = recyclerView.getAdapter();

        if (adapter == null) {
            return 0;
        }

        int itemCount = adapter.getItemCount();
        int height = 0;

        int max = Math.min(itemCount, maxItem);

        max = itemCount;

        for (int i = 0; i < max; i++) {
            RecyclerView.ViewHolder viewHolder = adapter.createViewHolder(recyclerView, adapter
                    .getItemViewType(i));

            adapter.onBindViewHolder(viewHolder, i);
            viewHolder.itemView.measure(
                    View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            viewHolder.itemView.layout(0, 0, viewHolder.itemView.getMeasuredWidth(),
                    viewHolder.itemView.getMeasuredHeight());
            viewHolder.itemView.setDrawingCacheEnabled(true);
            viewHolder.itemView.buildDrawingCache();
            height = viewHolder.itemView.getMeasuredHeight() + height;
            LogUtil.i("height = " + height);
        }
        return height;
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
                    if (onBottomSheetDialogListener != null) {
                        onBottomSheetDialogListener.onItemClick(fPosition);
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
