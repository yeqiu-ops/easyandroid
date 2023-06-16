package com.yeqiu.easyandroid.utils.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/14
 * @describe:
 * @fix:
 */
public class EventBusManager {

    /**
     * 注册
     *
     * @param subscriber
     */
    public static void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    /**
     * 反注册
     *
     * @param subscriber
     */
    public static void unregister(Object subscriber) {
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber);
        }
    }


    /**
     * 发送事件消息
     *
     * @param event
     */
    public static <T> void post(EventBusMessage<T> event) {
        EventBus.getDefault().post(event);
    }


    public static <T> void postSticky(EventBusMessage<T> event) {
        EventBus.getDefault().postSticky(event);
    }

    public static <T> void removeSticky(EventBusMessage<T> event) {
        EventBus.getDefault().removeStickyEvent(event);
    }


}
