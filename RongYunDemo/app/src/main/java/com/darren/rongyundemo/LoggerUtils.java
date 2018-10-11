package com.darren.rongyundemo;

import android.util.Log;

/**
 * Log 日志输出 帮助类
 */
public class LoggerUtils {
    /**
     * 控制是否 需要开启 日志打印输出的开关
     */
    private static boolean isOpen=true;

    public static void e(final String TAG,String msg){
        if (isOpen){
            Log.e(TAG,">>打印的日志信息>>"+msg);
        }
    }

    public static void d(final String TAG,String msg){
        if (isOpen){
            Log.d(TAG,">>打印的日志信息>>"+msg);
        }
    }

}
