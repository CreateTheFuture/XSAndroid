package com.adair.xsandroid.communication.retrofit;

import android.accounts.NetworkErrorException;

import com.adair.xsandroid.communication.retrofit.entity.HttpResult;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * package：    com.adair.xsandroid.communication.retrofit
 * author：     XuShuai
 * date：       2017/12/6  14:55
 * version:     v1.0
 * describe：   初步解析数据
 */
public abstract class BaseObserver<T> implements Observer<HttpResult<T>> {

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();
    }

    @Override
    public void onNext(HttpResult<T> httpResult) {
        onSuccess(httpResult);
        onRequestEnd();
    }

    @Override
    public void onError(Throwable e) {
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

    //网络请求前执行
    protected void onRequestStart() {
    }

    //网络请求完成后执行
    protected void onRequestEnd() {
    }

    //网络请求成功
    protected abstract void onSuccess(HttpResult<T> httpResult);

    //请求失败
    protected abstract void onFailure(Throwable e, boolean isNetworkError);


}
