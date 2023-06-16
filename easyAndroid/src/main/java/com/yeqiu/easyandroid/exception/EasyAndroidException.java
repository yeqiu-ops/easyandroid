package com.yeqiu.easyandroid.exception;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/11
 * @describe:
 * @fix:
 */
public class EasyAndroidException extends RuntimeException {

    public EasyAndroidException() {
    }

    public EasyAndroidException(String message) {
        super(message);
    }

    public EasyAndroidException(String message, Throwable cause) {
        super(message, cause);
    }

    public EasyAndroidException(Throwable cause) {
        super(cause);
    }


}