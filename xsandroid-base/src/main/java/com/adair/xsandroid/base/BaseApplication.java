package com.adair.xsandroid.base;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;


/**
 * package：    com.adair.xsandroid.base
 * author：     XuShuai
 * date：       2017/12/6  10:33
 * version:     v1.0
 * describe：   BaseApplication
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 重写getResources,使APP文字大小不随用户系统字体大小设置而改变
     *
     * @return resources
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        return resources;
    }
}
