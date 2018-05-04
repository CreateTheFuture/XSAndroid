package com.adair.xsandroid.base.system;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.adair.xsandroid.base.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * package：    com.simple.library.utils
 * author：     XuShuai
 * date：       2017/9/20  9:58
 * version:     v2.0
 * describe：   app错误日志收集类
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String FILE_DIR = "CrashText";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".txt";
    private static volatile CrashHandler INSTANCE;
    private String path;
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

    private CrashHandler() {
    }

    /**
     * 单列注册模式
     */
    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            synchronized (CrashHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CrashHandler();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 初始化CrashHandler
     *
     * @param context 上下文对象
     */
    public void init(Context context) {
        mContext = context.getApplicationContext();
        ///获取系统默认的UncaughtException处理器
        this.mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为系统默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);

        //初始化错误日志保存位置，当外置存储卡可用，放在外置存储里面，否则存在内置数据里面
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = mContext.getExternalFilesDir(FILE_DIR);
            if (file != null) {
                path = file.getAbsolutePath();
            } else {
                path = mContext.getFilesDir().getAbsolutePath() + File.separator + FILE_DIR;
            }
        } else {
            path = mContext.getFilesDir().getAbsolutePath() + File.separator + FILE_DIR;
        }
        Log.e("LogUtils", path);
    }


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            if (!handleException(e) && mDefaultCrashHandler != null) {
                //如果系统提供了默认异常处理就交给系统进行处理，否则自己进行处理。
                mDefaultCrashHandler.uncaughtException(t, e);
            } else {
                //暂停3秒显示toast
                Thread.sleep(3000);
                ActivityManager.getInstance().finishAllActivity();
                System.exit(0);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("LogUtils", collectCrashInfo(e1));
        } catch (InterruptedException e1) {
            Log.e("LogUtils", collectCrashInfo(e1));
        } catch (IOException e1) {
            Log.e("LogUtils", collectCrashInfo(e1));
        }
    }

    /*
     * 保存错误日志到文件
     */
    private void saveException2File(Throwable e) throws PackageManager.NameNotFoundException,
            IOException {
        e.printStackTrace();
        //如果没有sd卡，则返回
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return;
        }
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("LogUtils", mContext.getString(R.string.crash_create_file_error));
            }
        }

        long currentTime = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new
                Date(currentTime));
        File exFile = new File(path + File.separator + FILE_NAME + "_" + time + FILE_NAME_SUFFIX);

        String deviceInfo = collectDeviceInfo();
        String crashInfo = collectCrashInfo(e);
        //在控制台打印
        Log.e("LogUtils", deviceInfo + crashInfo);
        //输出文件
        PrintWriter filePw = new PrintWriter(new BufferedWriter(new FileWriter(exFile)));
        filePw.append(deviceInfo);
        filePw.append(crashInfo);
        filePw.close();
    }

    /*
     * 收集错误日志
     *
     * @param e 错误信息
     * @return 错误信息字符串
     */
    private String collectCrashInfo(Throwable e) {
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer, true);
        e.printStackTrace(pw);
        Throwable cause = e.getCause();
        while (cause != null) {
            cause.printStackTrace(pw);
            cause = cause.getCause();
        }
        pw.close();
        return writer.toString();
    }

    /*
     * 获取设备信息
     *
     * @return 设备信息
     * @throws PackageManager.NameNotFoundException 信息未找到
     */
    private String collectDeviceInfo() throws PackageManager.NameNotFoundException {
        PackageManager manager = mContext.getPackageManager();
        PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
        //收集信息
        return "App Version:" + info.versionName + "," + info.versionCode + "\n" +
                "OS version:" + Build.VERSION.RELEASE + "," + Build.VERSION.SDK_INT + "\n" +
                "制造商：" + Build.MANUFACTURER + "\n" +
                "Model：" + Build.MODEL + "\n" +
                "CPU ABI:" + Build.CPU_ABI + "\n";
    }

    /*
     * 显示提示信息
     */
    private boolean handleException(Throwable e) throws IOException, PackageManager
            .NameNotFoundException {
        if (e == null) {
            return true;
        }
        //显示toast
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, R.string.app_error, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();
        //保存文件
        saveException2File(e);
        return true;
    }
}
