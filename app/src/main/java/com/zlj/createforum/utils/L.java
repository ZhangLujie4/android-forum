package com.zlj.createforum.utils;

import android.util.Log;

/**
 * 项目名:   ZLJapp
 * 包名:     com.zlj.zljapp.utils
 * 文件名:   L
 * 创建者:   zhanglujie
 * 创建时间: 2018/6/9 18:04
 * 描述:    日志工具
 */

public class L {

    //开关
    public static final boolean DEBUG = true;
    //TAG
    public static final String TAG = "ZLJLog";

    //debug, info, warning, error, fatal

    public static void d(String text) {
        if (DEBUG) {
            Log.d(TAG, text);
        }
    }

    public static void i(String text) {
        if (DEBUG) {
            Log.i(TAG, text);
        }
    }

    public static void w(String text) {
        if (DEBUG) {
            Log.w(TAG, text);
        }
    }

    public static void e(String text) {
        if (DEBUG) {
            Log.e(TAG, text);
        }
    }
}
