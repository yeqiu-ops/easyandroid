package com.yeqiu.easyandroid.ui.dialog.exception;

/**
 * @project: DemoApplication
 * @author: 小卷子
 * @date: 2022/9/14
 * @describe:
 * @fix:
 */
public class DialogException extends RuntimeException{

    public DialogException() {
    }

    public DialogException(String message) {
        super(message);
    }

    public DialogException(String message, Throwable cause) {
        super(message, cause);
    }
}
