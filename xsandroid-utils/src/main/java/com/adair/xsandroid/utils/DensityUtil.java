package com.adair.xsandroid.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * package：    com.adair.xsandroid.utils
 * author：     XuShuai
 * date：       2017/12/6  11:10
 * version:     v1.0
 * describe：   屏幕尺寸获取及尺寸转换工具类
 */
public class DensityUtil {

    private DensityUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * dp转px
     *
     * @param dpVal 需要转换为px的dp值
     * @return 转换后的值
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, Utils.getContext().getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param spVal 需要转换为px的sp值
     * @return 转换后的值
     */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, Utils.getContext().getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param pxVal 需要转换为dp的px值
     * @return 转换为dp的值
     */
    public static float px2dp(float pxVal) {
        final float scale = Utils.getContext().getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param pxVal 需要转换为sp的px值
     * @return 转换为sp的值
     */
    public static float px2sp(float pxVal) {
        final float scale = Utils.getContext().getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * 获得屏幕宽度
     *
     * @return 屏幕宽度
     */
    public static int getScreenWidth() {
        int width = 0;
        WindowManager wm = (WindowManager) Utils.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(outMetrics);
            width = outMetrics.widthPixels;
        }
        return width;
    }

    /**
     * 获得屏幕高度，不包括底部虚拟按键
     *
     * @return 屏幕高度
     */
    public static int getScreenHeight() {
        int height = 0;
        WindowManager wm = (WindowManager) Utils.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(outMetrics);
            height = outMetrics.heightPixels;
        }
        return height;
    }

    /**
     * 获取屏幕高度，包含有底部虚拟按键的高度
     *
     * @return 屏幕高度
     */
    public static int getScreenHeightWithVirtualKey() {
        int dpi = 0;
        WindowManager wm = (WindowManager) Utils.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        Display display;
        if (wm != null) {
            display = wm.getDefaultDisplay();

            DisplayMetrics dm = new DisplayMetrics();
            @SuppressWarnings("rawtypes")
            Class c;
            try {
                c = Class.forName("android.view.Display");
                @SuppressWarnings("unchecked")
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, dm);
                dpi = dm.heightPixels;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dpi;
    }


    /**
     * 获得状态栏的高度
     *
     * @return 手机状态栏高度
     */
    public static int getStatusHeight() {
        int statusHeight = 0;
        Resources resources = Utils.getContext().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusHeight = resources.getDimensionPixelOffset(resourceId);
        }
        return statusHeight;
    }

    /**
     * 获取Navigation Bar高度
     *
     * @return 手机底部NavigationBar高度
     */
    public static int getNavigationBarHeight() {
        int navigationBarHeight = 0;
        Resources resources = Utils.getContext().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = resources.getDimensionPixelOffset(resourceId);
        }
        return navigationBarHeight;
    }
}
