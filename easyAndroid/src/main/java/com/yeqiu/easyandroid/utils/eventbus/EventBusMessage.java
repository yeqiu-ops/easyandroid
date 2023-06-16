package com.yeqiu.easyandroid.utils.eventbus;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/14
 * @describe:
 * @fix:
 */
public class EventBusMessage<T> {


    /**
     * 事件类型,应该定义成常量使用
     */
    private int eventType;

    private T data;


    public EventBusMessage(int eventType) {
        this.eventType = eventType;
    }

    public EventBusMessage(int eventType, T data) {
        this.eventType = eventType;
        this.data = data;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
