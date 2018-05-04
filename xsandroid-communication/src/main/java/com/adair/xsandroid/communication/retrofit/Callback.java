package com.adair.xsandroid.communication.retrofit;


import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.observers.DefaultObserver;

/**
 * package：    com.adair.xsandroid.communication.retrofit.download
 * author：     XuShuai
 * date：       2017/12/6  15:07
 * version:     v1.0
 * describe：   回调接口
 */
public abstract class Callback<T> extends DefaultObserver<T> {

    public abstract void onSuccess(T t);//成功

    public abstract void onFail(Throwable e);//失败

    public abstract void progress(long progress, long total);//进度

    private Type type;

    public Callback() {
        this.type = getSuperclassTypeParameter(getClass());
    }

    public Type getType() {
        return type;
    }

    /**
     * 通过反射想要的返回类型
     *
     * @param subclass 目标类
     * @return type
     */
    private static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterizedType = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onFail(e);
    }

    @Override
    public void onComplete() {

    }
}
