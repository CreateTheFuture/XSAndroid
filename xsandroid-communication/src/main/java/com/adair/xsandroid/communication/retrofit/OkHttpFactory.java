package com.adair.xsandroid.communication.retrofit;

import com.adair.xsandroid.communication.retrofit.download.DownloadInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * package：    com.adair.xsandroid.communication.retrofit
 * author：     XuShuai
 * date：       2017/12/6  14:44
 * version:     v1.0
 * describe：   okHttpClient创建工厂
 */
public class OkHttpFactory {
    public static final int HTTP_CONNECT_OUT_TIME = 10;
    public static final int HTTP_READ_OUT_TIME = 10;
    public static final int HTTP_WRITE_OUT_TIME = 10;

    private static OkHttpClient mOkHttpClient;
    private static OkHttpClient mDownloadClient;
    private static OkHttpClient mUploadClient;

    /**
     * 创建一个OKHttp Log拦截器
     */
    public static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }


    /**
     * Gets ok http client.
     *
     * @return the ok http client
     */
    public static OkHttpClient getOkHttpClient(boolean debug) {
        if (null == mOkHttpClient) {
            OkHttpClient.Builder build = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(HTTP_CONNECT_OUT_TIME, TimeUnit.SECONDS)
                    .readTimeout(HTTP_READ_OUT_TIME, TimeUnit.SECONDS)
                    .writeTimeout(HTTP_WRITE_OUT_TIME, TimeUnit.SECONDS);
            if (debug) {
                build.addInterceptor(getHttpLoggingInterceptor());
            }
            mOkHttpClient = build.build();
        }
        return mOkHttpClient;
    }

    /**
     * 获取下载OKHttp Client
     *
     * @param callback
     * @return
     */
    public static OkHttpClient getDownloadClient(boolean debug, Callback callback) {
        if (null == mDownloadClient) {
            OkHttpClient.Builder build = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(HTTP_CONNECT_OUT_TIME, TimeUnit.SECONDS)
                    .readTimeout(HTTP_READ_OUT_TIME, TimeUnit.SECONDS)
                    .writeTimeout(HTTP_WRITE_OUT_TIME, TimeUnit.SECONDS);
            if (debug) {
                build.addInterceptor(getHttpLoggingInterceptor());
            }
            build.addInterceptor(new DownloadInterceptor(callback));
            mDownloadClient = build.build();
        }
        return mDownloadClient;
    }

    /**
     * 获取上传OKHttp Client
     *
     * @param callback
     * @return
     */
    public static OkHttpClient getUploadClient(boolean debug, Callback callback) {
        if (null == mUploadClient) {
            OkHttpClient.Builder build = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(HTTP_CONNECT_OUT_TIME, TimeUnit.SECONDS)
                    .readTimeout(HTTP_READ_OUT_TIME, TimeUnit.SECONDS)
                    .writeTimeout(HTTP_WRITE_OUT_TIME, TimeUnit.SECONDS);
            //            if (debug) {
            //                build.addInterceptor(getHttpLoggingInterceptor());
            //            }
            mUploadClient = build.build();
        }
        return mUploadClient;
    }
}
