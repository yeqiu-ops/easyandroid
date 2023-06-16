package com.yeqiu.easyandroid.crashkit;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

class CrashUtils {

    static CrashModel parseCrash(Throwable ex) {
        CrashModel model = new CrashModel();
        try {
            model.setEx(ex);
            model.setTime(new Date().getTime());
            if (ex.getCause() != null) {
                ex = ex.getCause();
            }
            model.setExceptionMsg(ex.getMessage());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            pw.flush();
            String exceptionType = ex.getClass().getName();

            StackTraceElement element = parseThrowable(ex);
            if (element == null) return model;

            model.setLineNumber(element.getLineNumber());
            model.setClassName(element.getClassName());
            model.setFileName(element.getFileName());
            model.setMethodName(element.getMethodName());
            model.setExceptionType(exceptionType);

            model.setFullException(sw.toString());

            model.setVersionCode(getVersionCode());
            model.setVersionName(getVersionName());
        } catch (Exception e) {
            return model;
        }
        return model;
    }

    static StackTraceElement parseThrowable(Throwable ex) {
        if (ex == null || ex.getStackTrace() == null || ex.getStackTrace().length == 0) return null;
        StackTraceElement element;
        String packageName = CrashKit.getInstance().getContext().getPackageName();
        for (StackTraceElement ele : ex.getStackTrace()) {
            if (ele.getClassName().contains(packageName)) {
                element = ele;
                return element;
            }
        }
        element = ex.getStackTrace()[0];
        return element;
    }

    static String getCachePath() {
        return CrashKit.getInstance().getContext().getCacheDir().getAbsolutePath();
    }

    static String getVersionCode() {
        String versionCode = "";
        try {
            PackageManager pm = CrashKit.getInstance().getContext().getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(CrashKit.getInstance().getContext().getPackageName(), 0);
            versionCode = String.valueOf(packageInfo.versionCode);
        } catch (Exception e) {

        }
        return versionCode;
    }

    static String getVersionName() {
        String versionName = "";
        try {
            PackageManager pm = CrashKit.getInstance().getContext().getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(CrashKit.getInstance().getContext().getPackageName(), 0);
            versionName = String.valueOf(packageInfo.versionName);
        } catch (Exception e) {

        }
        return versionName;
    }


}
