package com.adair.xsandroid.sample;

import com.adair.xsandroid.communication.retrofit.entity.HttpResult;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * package：    com.adair.xsandroid.sample
 * author：     XuShuai
 * date：       2017/12/6  16:53
 * version:     v1.0
 * describe：   retrofit请求数据server
 */
public interface NetworkService {
    @FormUrlEncoded
    @POST("user/getUserById")
    Observable<HttpResult<User>> getUserById(@Field("id") int id);
}
