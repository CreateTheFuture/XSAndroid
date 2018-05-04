package com.adair.xsandroid.communication.retrofit.download;

import android.support.annotation.NonNull;

import com.adair.xsandroid.communication.retrofit.Callback;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * package：    com.adair.xsandroid.communication.retrofit.download
 * author：     XuShuai
 * date：       2017/12/6  15:11
 * version:     v1.0
 * describe：   进度监听拦截器
 */
public class DownloadInterceptor implements Interceptor {
    private Callback callback;

    public DownloadInterceptor(Callback callback) {
        this.callback = callback;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new DownloadResponseBody(originalResponse.body(), callback))
                .build();
    }
}
