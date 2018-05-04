package com.adair.xsandroid.communication.retrofit;

import android.accounts.NetworkErrorException;

import com.adair.xsandroid.communication.retrofit.entity.HttpResult;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * package：    com.adair.xsandroid.communication.retrofit
 * author：     XuShuai
 * date：       2017/12/6  14:56
 * version:     v1.0
 * describe：   解析数据
 */
public abstract class BaseObserverWithoutObject implements Observer<HttpResult> {
    public BaseObserverWithoutObject() {
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        onRequestStart();
    }

    @Override
    public void onNext(@NonNull HttpResult httpResult) {
        onSuccess(httpResult);
        onRequestEnd();
    }

    @Override
    public void onError(@NonNull Throwable e) {
        if (e instanceof ConnectException
                || e instanceof TimeoutException
                || e instanceof NetworkErrorException
                || e instanceof UnknownHostException) {
            onFailure(e, true);
        } else {
            onFailure(e, false);
        }
        onRequestEnd();
    }

    @Override
    public void onComplete() {

    }

    private void onRequestStart() {
    }

    abstract void onSuccess(HttpResult httpResult);

    abstract void onFailure(Throwable e, boolean isNetworkError);

    private void onRequestEnd() {
    }
}
