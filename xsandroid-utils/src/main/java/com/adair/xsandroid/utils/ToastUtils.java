package com.adair.xsandroid.utils;

import android.widget.Toast;

/**
 * package：    com.adair.xsandroid.utils
 * author：     XuShuai
 * date：       2017/12/6  11:20
 * version:     v1.0
 * describe：   Toast工具类
 */
public class ToastUtils {


    private ToastUtils() {
        /*cannot be instantiated**/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 短时间显示Toast
     */
    public static void show(CharSequence message) {
        Toast.makeText(Utils.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     */
    public static void show(int resId) {
        Toast.makeText(Utils.getContext(), resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 自定义显示Toast时间
     */
    public static void show(CharSequence message, int duration) {
        Toast.makeText(Utils.getContext(), message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     */
    public static void show(int resId, int duration) {
        Toast.makeText(Utils.getContext(), resId, duration).show();
    }

    /**
     * 长时间显示Toast
     */
    public static void showLong(CharSequence message) {
        Toast.makeText(Utils.getContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     */
    public static void showLong(int resId) {
        Toast.makeText(Utils.getContext(), resId, Toast.LENGTH_LONG).show();
    }
}
