package com.adair.xsandroid.communication.retrofit.upload;

import com.adair.xsandroid.communication.retrofit.BaseServer;
import com.adair.xsandroid.communication.retrofit.Callback;
import com.adair.xsandroid.communication.retrofit.Param;
import com.adair.xsandroid.communication.retrofit.RetrofitManager;
import com.google.gson.Gson;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * package：    com.simple.library.retrofit.upload
 * author：     XuShuai
 * date：       2017/9/20  14:34
 * version:     v1.0
 * describe：   上传文件
 */
public class UploadManager {
    private static volatile UploadManager mManager;

    private UploadManager() {
    }

    public static UploadManager getInstance() {
        if (mManager == null) {
            synchronized (UploadManager.class) {
                if (mManager == null) {
                    mManager = new UploadManager();
                }
            }
        }
        return mManager;
    }

    /**
     * 使用MultipartBody方式上传文件
     *
     * @param url    上传文件地址
     * @param files  上传文件
     * @param params 附带参数
     */
    public <T> void upload(String url, File[] files, Param[] params, final Callback<T> callback) {
        final MultipartBody requestBody = createRequestBody(files, params, callback);
        Retrofit retrofit = RetrofitManager.getInstance().getUploadRetrofit(callback);
        BaseServer server = retrofit.create(BaseServer.class);
        server.upload(url, requestBody)
                .map(new Function<ResponseBody, T>() {
                    @Override
                    public T apply(ResponseBody responseBody) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(responseBody.charStream(), callback.getType());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(callback);
    }

    /**
     * 构建MultipartBody
     *
     * @param files  文件
     * @param params 参数
     * @return MultipartBody
     */
    private MultipartBody createRequestBody(File[] files, Param[] params, Callback callback) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //参数
        if (null != params) {
            for (Param param : params) {
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
                        RequestBody.create(null, param.value));
            }
        }
        if (null != files) {
            String[] fileKeys = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                fileKeys[i] = files[i].getName();
            }
            RequestBody fileBody;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                UploadRequestBody body = new UploadRequestBody(fileBody, callback);
                //根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + fileKeys[i] + "\"; " +
                        "filename=\"" + fileName + "\""), body);
            }
        }
        return builder.build();
    }


    /**
     * @param path 文件路径
     * @return 文件mime type
     */
    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


    public <T> void upload2(String url, File[] files, Param[] params, final Callback<T> callback) {
        Map<String, RequestBody> maps = new HashMap<>();
        if (null != params) {
            for (Param param : params) {
                RequestBody body = RequestBody.create(null, param.value);
                maps.put(param.key, body);
            }
        }
        if (null != files) {
            for (File file : files) {
                String fileName = file.getName();
                RequestBody body = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                UploadRequestBody uploadRequestBody = new UploadRequestBody(body, callback);
                //retrofit 使用基本就是把key放在name里面，故在里面加入filename,就可以上传
                maps.put(fileName + "\"; filename=\"" + fileName, uploadRequestBody);
            }
        }

        Retrofit retrofit = RetrofitManager.getInstance().getUploadRetrofit(callback);
        BaseServer server = retrofit.create(BaseServer.class);
        server.upload2(url, maps)
                .map(new Function<ResponseBody, T>() {
                    @Override
                    public T apply(ResponseBody responseBody) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(responseBody.charStream(), callback.getType());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }
}
