package com.adair.xsandroid.communication.retrofit;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * package：    com.adair.xsandroid.communication.retrofit
 * author：     XuShuai
 * date：       2017/12/6  14:58
 * version:     v1.0
 * describe：   Retrofit请求管理
 */
public class RetrofitManager {
    private static volatile RetrofitManager mManager;
    private static String mBaseUrl = "";
    private boolean debug = true;
    //网络请求retrofit
    private static Retrofit mRetrofit;

    //下载文件retrofit
    private static Retrofit mDownloadRetrofit;

    //上传文件retrofit
    private static Retrofit mUploadRetrofit;

    /**
     * 私有构造方法
     */
    private RetrofitManager() {
    }

    /**
     * 单利模式创建
     *
     * @return manager
     */
    public static RetrofitManager getInstance() {
        if (mManager == null) {
            synchronized (RetrofitManager.class) {
                if (mManager == null) {
                    mManager = new RetrofitManager();
                }
            }
        }
        return mManager;
    }

    /**
     * 请求接口
     *
     * @param server 请求接口类
     */
    public static <T> T createService(Class<T> server) {
        if (mRetrofit == null) {
            throw new UnsupportedOperationException("u can't instantiate me because retrofit is null");
        }
        return mRetrofit.create(server);
    }

    /**
     * 初始化Manager
     *
     * @param baseUrl 网络请求基础地址
     */
    public void init(String baseUrl, boolean debug) {
        mBaseUrl = baseUrl;
        this.debug = debug;
        Log.d("LogUtils", baseUrl);
        if (mBaseUrl == null || mBaseUrl.trim().length() == 0) {
            throw new UnsupportedOperationException("u can't instantiate me because base url is null");
        }
        mRetrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(OkHttpFactory.getOkHttpClient(debug))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Retrofit getDownloadRetrofit(Callback callback) {
        if (null == mDownloadRetrofit) {
            mDownloadRetrofit = new Retrofit.Builder()
                    .baseUrl(mBaseUrl)
                    .client(OkHttpFactory.getDownloadClient(debug, callback))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mDownloadRetrofit;
    }


    public Retrofit getUploadRetrofit(Callback callback) {
        if (null == mUploadRetrofit) {
            mUploadRetrofit = new Retrofit.Builder()
                    .baseUrl(RetrofitManager.getInstance().getBaseUrl())
                    .client(OkHttpFactory.getUploadClient(debug, callback))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mUploadRetrofit;
    }


    public String getBaseUrl() {
        return mBaseUrl;
    }

    public boolean isDebug() {
        return debug;
    }
}
