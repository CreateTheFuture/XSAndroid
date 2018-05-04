package com.adair.xsandroid.sample;

/**
 * package：    com.adair.xsandroid.sample
 * author：     XuShuai
 * date：       2017/12/22  16:45
 * version:     v1.0
 * describe：
 */
public class HttpResult<T> {

    private int code;
    private String result;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "code=" + code +
                ", result='" + result + '\'' +
                ", data=" + data +
                '}';
    }
}
