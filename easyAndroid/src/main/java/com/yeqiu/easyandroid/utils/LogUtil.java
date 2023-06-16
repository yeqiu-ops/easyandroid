package com.yeqiu.easyandroid.utils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/11
 * @describe:
 * @fix:
 */
public class LogUtil {


    private static final String space = "       ";
    private static String logTag =APPInfoUtil.getPackageNameLast()+"_log";
    private static boolean enabledLog = true;
    private static final String flagI = "i";
    private static final String flagE = "e";
    public static final String easyAndroidTag = "easyandroid_log";


    private static boolean enabledLog() {
        return enabledLog;
    }



    /**
     * 默认开启日志
     *
     * @return
     */
    public static void setEnabledLog(boolean enabledLog) {
        LogUtil.enabledLog = enabledLog;
    }

    /**
     * tag 是全局的
     * 默认是app名字+ log
     *
     * @param tag
     */
    public static void setTag(String tag) {
        if (TextUtils.isEmpty(tag)){
            throw new IllegalArgumentException("tag Can't be empty");
        }
        LogUtil.logTag = tag;
    }


    public static void i(Object msg) {

        if (!enabledLog()) {
            return;
        }

        String tag = logTag;

        String logString = getLogString(msg);
        String taskName = getTaskName(4);
        printTopLine(flagI,taskName,tag);
        Log.i(tag, space + logString);
        printBottomLine(flagI,tag);
    }



    public static void e(Exception exception) {
        if (!enabledLog()) {
            return;
        }

        String tag = "Exception";

        String logString = getLogString(exception);
        String taskName = getTaskName(4);
        printTopLine(flagE,taskName,tag);
        Log.e(logTag, space + logString);
        printBottomLine(flagE,tag);
    }

    public static void logException(Exception exception) {
        if (!enabledLog()) {
            return;
        }

        String tag = "Exception";

        Log.e(tag +"\n",   Log.getStackTraceString(exception));
    }
    
    public static void logException(Throwable throwable) {
        if (!enabledLog()) {
            return;
        }
        String tag = "Exception";
        Log.e(tag +"\n",   Log.getStackTraceString(throwable));
    }

    public static void json(Object msg) {
        if (!enabledLog()) {
            return;
        }
        String tag = logTag;
        String logString = getLogString(msg);
        String message;
        try {
            if (logString.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(logString);
                //最重要的方法，就一行，返回格式化的json字符串，其中的数字4是缩进字符数
                message = jsonObject.toString(4);
            } else if (logString.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                message = logString;
            }
        } catch (JSONException e) {
            message = logString;
        }

        if (!enabledLog()) {
            return;
        }

        String taskName = getTaskName(4);
        Log.i(tag,  "═════════ JSON "+taskName+" \n"+ message);
        printBottomLine(flagI,tag);
    }



    public static void i(String tag,Object msg) {

        if (!enabledLog()) {
            return;
        }

        String logString = getLogString(msg);
        String taskName = getTaskName(4);
        printTopLine(flagI,taskName,tag);
        Log.i(tag, space + logString);
        printBottomLine(flagI,tag);
    }



    public static void e(String tag,Exception exception) {
        if (!enabledLog()) {
            return;
        }

        String logString = getLogString(exception);
        String taskName = getTaskName(4);
        printTopLine(flagE,taskName,tag);
        Log.e(logTag, space + logString);
        printBottomLine(flagE,tag);
    }

    public static void logException(String tag,Exception exception) {
        if (!enabledLog()) {
            return;
        }

        Log.e(tag +"\n",   Log.getStackTraceString(exception));
    }

    public static void logException(String tag,Throwable throwable) {
        if (!enabledLog()) {
            return;
        }
        Log.e(tag +"\n",   Log.getStackTraceString(throwable));
    }

    public static void json(String tag,Object msg) {
        if (!enabledLog()) {
            return;
        }
        String logString = getLogString(msg);
        String message;
        try {
            if (logString.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(logString);
                //最重要的方法，就一行，返回格式化的json字符串，其中的数字4是缩进字符数
                message = jsonObject.toString(4);
            } else if (logString.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                message = logString;
            }
        } catch (JSONException e) {
            message = logString;
        }

        if (!enabledLog()) {
            return;
        }

        String taskName = getTaskName(4);
        Log.i(tag,  "═════════ JSON "+taskName+" \n"+ message);
        printBottomLine(flagI,tag);
    }




    private static String getLogString(Object msg) {

        if (msg instanceof String) {
            return msg.toString();
        } else {
            //转json
            return JsonUtil.toJson(msg);
        }
    }


    private static String getTaskName(int index) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        //查看方法栈堆信息
//        for (int i = 0; i < stackTrace.length; i++) {
//            StackTraceElement stackTraceElement = stackTrace[i];
//            String className = stackTraceElement.getClassName();
//            Log.i("LogTest", "i = " + i + " ," + className);
//        }
        String fileName = "unknown";
        int lineNumber = 0;
        if (stackTrace != null && stackTrace.length >= 5) {
            fileName = stackTrace[index].getFileName();
            lineNumber = stackTrace[index].getLineNumber();
        }
        StringBuilder taskName = new StringBuilder();
        taskName.append("(").append(fileName).append(":").append(lineNumber).append(")");
        return taskName.toString();
    }


    private static void printTopLine(String flag, String title,String tag){
        switch (flag) {
            case flagI:
                Log.i(tag,"                                                  ");
                Log.i(tag,"═════════"+title+"═════════");
                break;
            case flagE:
                Log.i(tag,"═════════");
                Log.e(tag,"═════════"+title+"═════════");
                break;
            default:
                break;
        }
    }

    private static void printBottomLine(String flag,String tag){
        switch (flag) {
            case flagI:
                Log.i(tag,"═════════ log end ═════════ ");
                break;
            case flagE:
                Log.e(tag,"═════════ log end ═════════ ");
                break;
            default:
                break;
        }
    }

}
