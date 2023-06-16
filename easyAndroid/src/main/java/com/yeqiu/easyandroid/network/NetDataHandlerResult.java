package com.yeqiu.easyandroid.network;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/11
 * @describe:
 * @fix:
 */
public class NetDataHandlerResult {

    private boolean isSucceed;

    private String errorMsg;

    private long code;


    public NetDataHandlerResult(boolean isSucceed) {
        this.isSucceed = isSucceed;
    }

    public NetDataHandlerResult(boolean isSucceed,long code, String errorMsg) {
        this.isSucceed = isSucceed;
        this.errorMsg = errorMsg;
        this.code = code;
    }

    public boolean isSucceed() {
        return isSucceed;
    }

    public void setSucceed(boolean succeed) {
        isSucceed = succeed;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }
}
