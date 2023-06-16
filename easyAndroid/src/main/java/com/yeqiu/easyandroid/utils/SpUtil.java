package com.yeqiu.easyandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.yeqiu.easyandroid.EasyAndroid;

/**
 * @project: EasyAndroid
 * @author: 小卷子
 * @date: 2022/4/11
 * @describe:
 * @fix:
 */
public class SpUtil {

    private static final String SP_NAME = APPInfoUtil.getAppName();

    private static SpUtil instance;

    public static SpUtil getInstance() {
        if (instance == null) {
            instance = new SpUtil();
        }
        return instance;
    }

    private SharedPreferences getSp() {

        return EasyAndroid.getInstance().getContext()
                .getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    private void apply(SharedPreferences.Editor editor) {

        editor.apply();

    }

    private void commit(SharedPreferences.Editor editor) {

        editor.commit();

    }



    public void putString(String key, String val) {

        try {
            SharedPreferences sp = getSp();
            if (sp != null) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(key, val);
                apply(editor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void putBoolean(String key, boolean val) {
        try {
            SharedPreferences sp = getSp();
            if (sp != null) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean(key, val);
                apply(editor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void putInt(String key, int val) {
        try {
            SharedPreferences sp = getSp();
            if (sp != null) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt(key, val);
                apply(editor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getInt(String key, int def) {
        try {
            SharedPreferences sp = getSp();
            if (sp != null) {
                def = sp.getInt(key, def);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public String getString(String key, String def) {
        try {
            SharedPreferences sp = getSp();
            if (sp != null) {
                def = sp.getString(key, def);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }


    public boolean getBoolean(String key, boolean def) {
        try {
            SharedPreferences sp = getSp();
            if (sp != null) {
                def = sp.getBoolean(key, def);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }


    public void remove(String key) {

        try {
            SharedPreferences sp = getSp();
            if (sp != null) {
                SharedPreferences.Editor editor = sp.edit();
                editor.remove(key);
                apply(editor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {

        try {
            SharedPreferences sp = getSp();
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            apply(editor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
