package com.adair.xsandroid.communication.retrofit;

/**
 * package：    com.adair.xsandroid.communication.retrofit
 * author：     XuShuai
 * date：       2017/12/6  14:55
 * version:     v1.0
 * describe：   上传参数
 */
public class Param {
    public String key;
    public String value;

    public Param() {
    }

    public Param(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Param{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
