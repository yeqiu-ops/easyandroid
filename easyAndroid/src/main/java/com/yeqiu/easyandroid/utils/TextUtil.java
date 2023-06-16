package com.yeqiu.easyandroid.utils;

import android.util.TypedValue;
import android.widget.TextView;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/1/22
 * @describe:
 * @fix:
 */
public class TextUtil {

    public static void setTextSize(TextView textView, int psSize) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, psSize);

    }


    public static void setTextWithMaxLine(TextView textView, String text, int maxLength) {
        if (CommonUtil.isEmpty(text)) {
            return;
        }
        if (text.length() > maxLength) {
            text = text.substring(0, maxLength)+"...";
        }
        textView.setText(text);
    }

}
