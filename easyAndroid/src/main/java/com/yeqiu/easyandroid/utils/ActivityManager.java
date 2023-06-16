package com.yeqiu.easyandroid.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/13
 * @describe:
 * @fix:
 */
public class ActivityManager  {

    private static ActivityManager instance;

    private ActivityManager() {
    }

    public static ActivityManager getInstance() {
        synchronized (ActivityManager.class) {
            if (instance == null) {
                instance = new ActivityManager();
            }
        }
        return instance;
    }

    private List<Activity> activityStack = new ArrayList<>();


    public void pushActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 仅从栈中删除，不销毁
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {

        if (!activityStack.isEmpty()) {
            activityStack.remove(activity);
        }
    }

    /**
     * 从栈中删除并销毁
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {

        removeActivity(activity);
        activity.finish();
    }


    /**
     * 结束指定类名的Activity,并销毁
     *
     * @param clazz
     */
    public void finishActivity(Class<? extends Activity> clazz) {

        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();
            if (next.getClass().equals(clazz)) {
                iterator.remove();
                next.finish();
            }
        }

    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {

        for (Activity activity : activityStack) {
            activity.finish();
        }
        activityStack.clear();

        //退出应用

    }


    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity getCurrentActivity() {

        if (!activityStack.isEmpty()){
            return activityStack.get(activityStack.size() - 1);
        }
        return null;
    }


    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishCurrentActivity() {

        if (!activityStack.isEmpty()){
            Activity activity =activityStack.get(activityStack.size() - 1);
            finishActivity(activity);
        }
    }


    public void finishAllButThis(Class<? extends Activity> clazz) {

        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();
            if (next.getClass().equals(clazz)) {
                continue;
            }
            //结束
            iterator.remove();
            next.finish();
        }
    }
    public void finishAllButThis(String className) {

        try {
            Class<?> aClass = Class.forName(className);
            Iterator<Activity> iterator = activityStack.iterator();
            while (iterator.hasNext()) {
                Activity next = iterator.next();
                if (next.getClass().equals(aClass)) {
                    continue;
                }
                //结束
                iterator.remove();
                next.finish();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }



    /**
     * 回到指定页面，关闭指定页面之后的所有页面
     */
    public void popTo(Class<? extends Activity> activity) {

        ListIterator<Activity> listIterator;

        for (listIterator = activityStack.listIterator(); listIterator.hasNext(); ) {
            // 将游标定位到列表结尾
            listIterator.next();
        }

        while (listIterator.hasPrevious()) {
            Activity previous = listIterator.previous();
            if (previous.getClass().equals(activity)) {
                break;
            }
            previous.finish();
            listIterator.remove();
        }

    }



}
