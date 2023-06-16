package com.yeqiu.easyandroid.ui.dialog.model;

import com.yeqiu.easyandroid.R;

/**
 * @project: DemoApplication
 * @author: 小卷子
 * @date: 2022/9/14
 * @describe:
 * @fix:
 */
public class TextInfo {


    private Integer fontSize = -1;              //字号大小，值为-1时使用默认样式，单位：dp
    private Integer fontColor = 1;              //文字颜色，值为1时使用默认样式，取值可以用Color.rgb(r,g,b)等方式获取
    private Boolean bold = false;           //是否粗体
    private Integer maxLines = -1;              //最大行数
    private String text;

    public TextInfo(String text) {
        this.text = text == null ? "" : text;
        this.fontSize = 16;
        this.fontColor = R.color.color_e6000000;
        this.bold = false;
    }

    public TextInfo(String text, Integer fontSize, Integer fontColor, Boolean bold, Integer maxLines) {
        this.text = text;
        this.fontSize = fontSize;
        this.fontColor = fontColor;
        this.bold = bold;
        this.maxLines = maxLines;
    }

    public TextInfo(Integer fontSize, Integer fontColor, Boolean bold, Integer maxLines) {
        this.fontSize = fontSize;
        this.fontColor = fontColor;
        this.bold = bold;
        this.maxLines = maxLines;
    }

    public TextInfo(TextInfo textInfo){
        this.fontSize = textInfo.getFontSize();
        this.fontColor = textInfo.getFontColor();
        this.bold = textInfo.getBold();
        this.maxLines = textInfo.getMaxLines();
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public Integer getFontColor() {
        return fontColor;
    }

    public void setFontColor(Integer fontColor) {
        this.fontColor = fontColor;
    }

    public Boolean getBold() {
        return bold;
    }

    public void setBold(Boolean bold) {
        this.bold = bold;
    }

    public Integer getMaxLines() {
        return maxLines;
    }

    public void setMaxLines(Integer maxLines) {
        this.maxLines = maxLines;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
