package com.yeqiu.easyandroid.network.capture;

import android.database.CursorWindow;

import com.yeqiu.easyandroid.utils.LogUtil;

import org.litepal.LitePal;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @project: Answer
 * @author: 小卷子
 * @date: 2022/5/6
 * @describe:
 * @fix:
 */
public class NetLogDao {

    private static int maxNumber = 500;

    /**
     * 最多只保留500条记录
     *
     * @param netLog
     */
    public static void addLog(NetLog netLog) {

        synchronized (NetLogDao.class) {

            //是否超过最大限制
            delIfOverTheMax();
            long currentTime = System.currentTimeMillis();
            netLog.setDate(currentTime);
            netLog.save();
        }
    }


    public static List<NetLog> findAll() {

        List<NetLog> netLogs = LitePal
                .order("date desc")
                .limit(20)
                .find(NetLog.class);
        return netLogs;
    }


    public static List<NetLog> findPage(int pageSize, int pageNumber) {


        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (pageNumber <= 1) {
            pageNumber = 1;
        }
        pageNumber = pageNumber - 1;

        int offset = pageSize * pageNumber;

        List<NetLog> netLogs = LitePal
                .order("date desc")
                .limit(pageSize)
                .offset(offset)
                .find(NetLog.class);


        return netLogs;

    }


    public static int getPageCount(int pageSize) {

        int count = getCount();
        int pageCount = count / pageSize;
        if (count % pageSize > 0) {
            pageCount = pageCount + 1;
        }
        return pageCount;
    }


    public static int getCount() {
        int count = LitePal.count(NetLog.class);
        return count;
    }

    public static void clear() {

        LitePal.deleteAll(NetLog.class);
    }


    /**
     * 最大数量删除机制：单次使用不设限，重新打开app时仅保留最近500条记录
     */
    private static void delIfOverTheMax() {

        try {
            int count = getCount();
            if (count > maxNumber) {
                //超出最大限制，删除第一条
                NetLog first = LitePal.findFirst(NetLog.class);
                int delete = LitePal.delete(NetLog.class, first.getId());
            }
        } catch (Exception e) {
            LogUtil.i("数据库操作错误,清空数据库：" + e.getMessage());
            clear();
        }

    }


}
