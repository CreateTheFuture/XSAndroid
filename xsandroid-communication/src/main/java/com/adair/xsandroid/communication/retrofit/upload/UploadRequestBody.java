package com.adair.xsandroid.communication.retrofit.upload;

import android.support.annotation.NonNull;

import com.adair.xsandroid.communication.retrofit.Callback;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * package：    com.adair.xsandroid.communication.retrofit.upload
 * author：     XuShuai
 * date：       2017/12/9  14:05
 * version:     v1.0
 * describe：   上传文件进度监听
 */
public class UploadRequestBody<T> extends RequestBody {

    private Callback mCallback;
    private RequestBody mRequestBody;
    private BufferedSink mBufferedSink;

    public UploadRequestBody(RequestBody requestBody, Callback callback) {
        super();
        mRequestBody = requestBody;
        mCallback = callback;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(@NonNull BufferedSink sink) throws IOException {
        if (null == mBufferedSink) {
            //包装
            mBufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        mRequestBody.writeTo(mBufferedSink);
        //调用刷新，保证最后数据白写入
        mBufferedSink.flush();
    }

    //加入进度回调
    private Sink sink(BufferedSink sink) {
        return new ForwardingSink(sink) {
            long bytesRead = 0L;//当前上传文件长度
            long contentLength = 0L;//上传文件总长度

            @Override
            public void write(@NonNull Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    contentLength = contentLength();
                }
                bytesRead += byteCount;
                mCallback.progress(bytesRead, contentLength);
            }
        };
    }
}
