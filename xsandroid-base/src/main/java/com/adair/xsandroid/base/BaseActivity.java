package com.adair.xsandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.adair.xsandroid.base.system.ActivityManager;


/**
 * package：    com.adair.xsandroid.base
 * author：     XuShuai
 * date：       2017/12/6  10:33
 * version:     v1.0
 * describe：   基础BaseActivity,自动加入ActivityManager管理
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().pushActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().popActivity(this);
    }
}
