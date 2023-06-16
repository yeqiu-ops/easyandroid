package com.yeqiu.easyandroid.utils;

import android.text.TextUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/15
 * @describe:
 * @fix:
 */
public class CommonUtil {

    public static boolean isEmpty(Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                return TextUtils.isEmpty((CharSequence) obj);
            } else if (obj instanceof Collection) {
                return ((Collection<?>) obj).size() <= 0;
            } else if (obj instanceof Map) {
                return ((Map<?, ?>) obj).size() <= 0;
            } else {
                //其他类型，默认非null
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }


    public static boolean oneOfEmpty(Object... obj) {

        if (isEmpty(obj)) {
            return true;
        }
        boolean oneOfEmpty = false;
        for (Object o : obj) {
            if (isEmpty(o)) {
                oneOfEmpty = true;
                break;
            }
        }
        return oneOfEmpty;
    }


    public static String getMaxSizeFromStr(String str, int max) {

        if (isEmpty(str)) {
            return "";
        }
        if (max >= str.length()) {
            return str;
        }
        return str.substring(0, max+1) + "...";


    }


    public static String getTaskName(int index) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        //查看所有方法栈堆信息
//        for (int i = 0; i < stackTrace.length; i++) {
//            StackTraceElement stackTraceElement = stackTrace[i];
//            String className = stackTraceElement.getClassName();
//            Log.i("net_log", "i = " + i + " ," + className);
//        }
        String fileName = "unknown";
        int lineNumber = 0;
        if (stackTrace != null && stackTrace.length >= index) {
            fileName = stackTrace[index].getFileName();
            lineNumber = stackTrace[index].getLineNumber();
        }
        StringBuilder taskName = new StringBuilder();
        taskName.append("(").append(fileName).append(":").append(lineNumber).append(")");
        return taskName.toString();
    }


    public static int booleanToInt(boolean b) {
        return b ? 1 : 0;
    }

    public static boolean intToBoolean(int i) {
        return i == 1;
    }


    public static boolean stringToBoolean(String s) {
        return s.equals("0");
    }


    public static boolean isInt(String intStr) {
        try {
            Integer.parseInt(intStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public static boolean equals(String s1, String s2) {
        if (CommonUtil.isEmpty(s1) && CommonUtil.isEmpty(s2)) {
            //null
            return true;
        }
        if (CommonUtil.isNotEmpty(s1) && CommonUtil.isNotEmpty(s2)) {
            //！=null
            return s1.equals(s2);
        } else {
            return false;
        }
    }

    public static boolean notEquals(String s1, String s2) {
        return !equals(s1, s2);
    }


    public static boolean equalsOneOf(Object obj1, Object... others) {

        boolean equals = true;
        if (isEmpty(obj1) && isEmpty(others)) {
            //都是空
            equals = false;
        } else {
            if (isNotEmpty(obj1) && isNotEmpty(others)) {
                //单个比较
                for (Object other : others) {
                    if (!obj1.equals(others)) {
                        equals = false;
                        break;
                    }
                }
            } else {
                //其中有非空
                equals = false;
            }
        }

        return equals;
    }


}
