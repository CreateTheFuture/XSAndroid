package com.adair.xsandroid.sample;

import com.adair.xsandroid.base.BaseApplication;
import com.adair.xsandroid.communication.retrofit.RetrofitManager;
import com.adair.xsandroid.utils.Utils;

/**
 * package：    com.adair.xsandroid.sample
 * author：     XuShuai
 * date：       2017/12/6  16:22
 * version:     v1.0
 * describe：   自定义Application
 */
public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this.getApplicationContext(), BuildConfig.DEBUG);
        RetrofitManager.getInstance().init(AppConstants.BASE_URL, BuildConfig.DEBUG);
    }
}
