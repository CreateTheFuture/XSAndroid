package com.adair.xsandroid.utils;

import android.content.Context;

/**
 * package：    com.adair.xsandroid.utils
 * author：     XuShuai
 * date：       2017/12/6  11:00
 * version:     v1.0
 * describe：   工具类管理类
 */
public class Utils {

    private static Context mContext;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (mContext != null) {
            return mContext;
        }
        throw new NullPointerException("u should init first");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context, Boolean debug) {
        mContext = context.getApplicationContext();
        LogUtils.isDebug(debug);
    }
}
