package com.yeqiu.easyandroid.utils.image;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/28
 * @describe:
 * @fix:
 */
public enum ScaleType {

    //默认类型，图片会被等比缩放直到完全填充整个ImageView，并居中显示
    centerCrop,
    //在该模式下，图片会被等比缩放到能够填充控件大小，并居中展示
    fitCenter,
    //图片将被等比缩放到能够完整展示在ImageView中并居中，如果图片大小小于控件大小，那么就直接居中展示该图片
    centerInside,
    //圆形
    circleCrop,
    //圆角 ,默认20
    rounded

}
