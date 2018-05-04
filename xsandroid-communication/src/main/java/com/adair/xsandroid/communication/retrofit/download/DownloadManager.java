package com.adair.xsandroid.communication.retrofit.download;

import com.adair.xsandroid.communication.retrofit.BaseServer;
import com.adair.xsandroid.communication.retrofit.Callback;
import com.adair.xsandroid.communication.retrofit.RetrofitManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * package：    com.adair.xsandroid.communication.retrofit.download
 * author：     XuShuai
 * date：       2017/12/6  15:08
 * version:     v1.0
 * describe：   简单下载文件管理器
 */
public class DownloadManager {

    private static volatile DownloadManager mManager;
    private static Retrofit mRetrofit;

    private DownloadManager() {
    }

    public static DownloadManager getInstance() {
        if (mManager == null) {
            synchronized (DownloadManager.class) {
                if (mManager == null) {
                    mManager = new DownloadManager();
                }
            }
        }
        return mManager;
    }

    /**
     * 从下载地址获取文件名
     *
     * @param url 下载地址
     * @return 文件名
     */
    private static String getFileName(String url) {
        int separatorIndex = url.lastIndexOf("/");
        return (separatorIndex < 0) ? url : url.substring(separatorIndex + 1, url.length());
    }

    /**
     * Download 下载文件
     *
     * @param url        the url 下载地址
     * @param fileParent the file parent 文件保存目录
     * @param callback   the callback 下载回调接口
     */
    public <T> void download(String url, final String fileParent, final Callback<String> callback) {
        String fileName = getFileName(url);
        File parentFile = new File(fileParent);
        if (parentFile.isFile()) {
            parentFile = parentFile.getParentFile();
        }
        if (!parentFile.exists()) {
            if (!parentFile.mkdirs()) {
                callback.onFail(new Throwable("创建文件失败"));
                return;
            }
        }
        final File file = new File(parentFile.getAbsoluteFile() + File.separator + fileName);
        Retrofit retrofit = RetrofitManager.getInstance().getDownloadRetrofit(callback);
        BaseServer server = retrofit.create(BaseServer.class);
        server.download(url)
                .subscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, String>() {
                    @Override
                    public String apply(ResponseBody responseBody) throws Exception {
                        return writeResponseBody2File(responseBody, file, callback);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    /**
     * 讲responseBody写入文件
     *
     * @param responseBody 下载返回body
     * @param file         下载文件
     * @param callback     下载回调
     */
    private String writeResponseBody2File(ResponseBody responseBody, File file, Callback callback) {
        try {
            InputStream is = responseBody.byteStream();
            FileOutputStream fos = new FileOutputStream(file);
            int byteCount;
            byte[] buffer = new byte[2048];
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
            fos.close();
            is.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            callback.onFail(e);
        }
        return null;
    }
}
