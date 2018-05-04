package com.adair.xsandroid.base.system;

import android.app.Activity;

import java.util.Stack;


/**
 * package：    com.adair.xsandroid.base.system
 * author：     XuShuai
 * date：       2017/12/6  10:40
 * version:     v1.0
 * describe：   activity管理类
 */
public class ActivityManager {
    private static final ActivityManager activityManager = new ActivityManager();
    private static Stack<Activity> activityStack;

    private ActivityManager() {
    }

    public static ActivityManager getInstance() {
        return activityManager;
    }

    /**
     * 将activity推入栈
     *
     * @param activity the activity
     */
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 将activity移出栈
     *
     * @param activity 目标activity
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束activity
     *
     * @param activity 目标activity
     */
    public void endActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 获取当前activity（即栈顶activity）
     *
     * @return activity
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (!activityStack.empty()) {
            activity = activityStack.lastElement();
        }
        return activity;
    }

    /**
     * 弹出cls以外的所以activity
     *
     * @param cls 目标activity类
     */
    public void popAllActivityExceptOne(Class<? extends Activity> cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }

    /**
     * 结束除了cls以外的所有activity
     *
     * @param cls the cls
     */
    public void finishAllActivityExceptOne(Class<? extends Activity> cls) {
        while (!activityStack.empty()) {
            Activity activity = currentActivity();
            if (activity.getClass().equals(cls)) {
                popActivity(activity);
            } else {
                endActivity(activity);
            }
        }
    }

    /**
     * 结束所有activity
     */
    public void finishAllActivity() {
        while (!activityStack.empty()) {
            Activity activity = currentActivity();
            endActivity(activity);
        }
    }
}
