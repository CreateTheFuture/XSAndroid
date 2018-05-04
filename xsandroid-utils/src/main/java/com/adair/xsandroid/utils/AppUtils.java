package com.adair.xsandroid.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.List;

/**
 * package：    com.adair.xsandroid.utils
 * author：     XuShuai
 * date：       2017/12/6  11:08
 * version:     v1.0
 * describe：   App操作类
 */
public class AppUtils {
    private AppUtils() {
        /*cannot be instantiated */
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName() {
        String appName = "";
        try {
            PackageManager packageManager = Utils.getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(Utils.getContext().getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            appName = Utils.getContext().getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e(e);
        }
        return appName;
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @return 当前应用的版本名称
     */
    public static String getVersionName() {
        String versionName = "";
        try {
            PackageManager packageManager = Utils.getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(Utils.getContext().getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e(e);
        }
        return versionName;
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode() {
        int versionCode = -1;
        try {
            PackageManager packageManager = Utils.getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(Utils.getContext().getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e(e);
        }
        return versionCode;
    }


    /**
     * 安装apk,用于更新时下载Apk并安装
     *
     * @param context 上下文对象
     * @param path    apk文件路径
     */
    public static void installApk(Context context, String path, String authority) {
        File file = new File(path);
        Intent intent = new Intent();
        // 执行动作
        Uri data;
        intent.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "com.xxx.fileProvider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(context, authority, file);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(file);
        }
        // 执行的数据类型
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 跳转到系统应用设置页面
     *
     * @param activity    activity
     * @param requestCode 请求码
     */
    public static void go2Setting(Activity activity, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 打开网络设置界面
     *
     * @param activity activity
     */
    public static void openNetworkSetting(Activity activity) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT > 10) {
            //跳转到设置界面
            intent.setAction(Settings.ACTION_SETTINGS);
        } else {
            //现在会跳转到移动网络设置界面
            ComponentName componentName = new ComponentName("com.android.settting",
                    "com.android.setting.WirelessSettings");
            intent.setComponent(componentName);
            intent.setAction("android.intent.action.View");
        }
        activity.startActivity(intent);
    }

    /**
     * 判断当前app是否在后台运行
     *
     * @param context 上下文对象
     * @return true 在后台运行，false不在后台运行
     */
    public static boolean isRunBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            List<RunningAppProcessInfo> appProcessInfoList = activityManager.getRunningAppProcesses();
            String packageName = context.getPackageName();
            for (RunningAppProcessInfo appProcessInfo : appProcessInfoList) {
                if (appProcessInfo.processName.equals(packageName)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        return appProcessInfo.importance == RunningAppProcessInfo.IMPORTANCE_CACHED;
                    } else {
                        return appProcessInfo.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断当前app是否在前台运行
     *
     * @param context 上下文对象
     * @return true 在前台运行，false不在前台运行
     */
    public static boolean isRunForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            List<RunningAppProcessInfo> appProcessInfoList = activityManager.getRunningAppProcesses();
            String packageName = context.getPackageName();
            for (RunningAppProcessInfo appProcessInfo : appProcessInfoList) {
                if (appProcessInfo.processName.equals(packageName)) {
                    return appProcessInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
                }
            }
        }
        return false;
    }
}
