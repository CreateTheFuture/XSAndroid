package com.adair.xsandroid.communication.retrofit.download;

import android.support.annotation.NonNull;

import com.adair.xsandroid.communication.retrofit.Callback;

import java.io.IOException;

import io.reactivex.annotations.Nullable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * package：    com.adair.xsandroid.communication.retrofit.download
 * author：     XuShuai
 * date：       2017/12/6  15:11
 * version:     v1.0
 * describe：   文件下载进度监听
 */
public class DownloadResponseBody extends ResponseBody {

    private Callback callback;
    private ResponseBody responseBody;
    private BufferedSource bufferedSource;


    public DownloadResponseBody(ResponseBody responseBody, Callback callback) {
        this.callback = callback;
        this.responseBody = responseBody;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (null == bufferedSource) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long curBytesRead = 0L;//当前下载长度
            long contentLength = 0L;//总下载进度

            @Override
            public long read(@NonNull Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                curBytesRead += (bytesRead != -1 ? bytesRead : 0);
                if (contentLength == 0) {
                    contentLength = contentLength();
                }
                if (null != callback) {
                    callback.progress(curBytesRead, contentLength);
                }
                return bytesRead;
            }
        };
    }
}
