package com.adair.xsandroid.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.adair.xsandroid.base.BaseActivity;
import com.adair.xsandroid.communication.retrofit.BaseObserver;
import com.adair.xsandroid.communication.retrofit.Callback;
import com.adair.xsandroid.communication.retrofit.RetrofitManager;
import com.adair.xsandroid.communication.retrofit.RxSchedulers;
import com.adair.xsandroid.communication.retrofit.download.DownloadManager;
import com.adair.xsandroid.communication.retrofit.entity.HttpResult;
import com.adair.xsandroid.communication.retrofit.upload.UploadManager;
import com.adair.xsandroid.utils.LogUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * package：    com.adair.xsandroid
 * author：     XuShuai
 * date：       2017/12/6  15:15
 * version:     v1.0
 * describe：
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_result)
    TextView mTvResult;
    @BindView(R.id.tv_download_address)
    TextView mTvDownloadAddress;
    @BindView(R.id.tv_upload_info)
    TextView mTvUploadInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_request_http, R.id.btn_download, R.id.btn_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_request_http:
                requestHttp();
                break;
            case R.id.btn_download:
                download();
                break;
            case R.id.btn_upload:
                upload();
                break;
        }
    }

    private void upload() {
        LogUtils.d("1111111111111");
        LogUtils.d("22222222222222222");
        String filePath = "/storage/emulated/0/DCIM/Camera/IMG_20160709_155316.jpg";
        File file = new File(filePath);
        if (!file.isFile()) {
            LogUtils.d("3333333333333");
        }
        UploadManager.getInstance().upload2(AppConstants.BASE_URL + "file/upload", new File[]{file}, null,
                new Callback<com.adair.xsandroid.sample.HttpResult<User>>() {

                    @Override
                    protected void onStart() {
                        super.onStart();
                        LogUtils.d("开始上传");
                    }

                    @Override
                    public void onSuccess(com.adair.xsandroid.sample.HttpResult<User> user) {
                        LogUtils.d(user.toString());
                    }

                    @Override
                    public void onFail(Throwable e) {
                        LogUtils.e(e);
                    }

                    @Override
                    public void progress(final long progress, final long total) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTvDownloadAddress.setText("下载：" + progress * 1f / total * 100 + "%");
                            }
                        });
                    }
                });
    }

    //下载文件
    private void download() {
        File file = getApplication().getExternalFilesDir("download");
        if (file != null && !file.exists()) {
            if (!file.mkdirs()) {
                LogUtils.d("11111111");
            }
        }
        DownloadManager.getInstance().download("http://n4.qnfen.cn/h14/dsy/qinjianshen.apk",
                file.getAbsolutePath(), new Callback<String>() {

                    @Override
                    protected void onStart() {
                        super.onStart();
                        mTvDownloadAddress.setText("开始下载");
                    }

                    @Override
                    public void onSuccess(String s) {
                        LogUtils.d(s);
                        mTvDownloadAddress.setText("下载完成");
                    }

                    @Override
                    public void onFail(Throwable e) {
                        LogUtils.e(e);
                        mTvDownloadAddress.setText("下载错误");
                    }

                    @Override
                    public void progress(final long progress, final long total) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTvDownloadAddress.setText("下载：" + progress * 1f / total * 100 + "%");
                            }
                        });
                    }
                });
    }

    //网络请求
    private void requestHttp() {
        RetrofitManager.createService(NetworkService.class).getUserById(1)
                .compose(RxSchedulers.<HttpResult<User>>io_main())
                .subscribe(new BaseObserver<User>() {


                    @Override
                    protected void onSuccess(HttpResult<User> httpResult) {
                        LogUtils.d(httpResult.toString());
                        mTvResult.setText(httpResult.toString());
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetworkError) {
                        LogUtils.e(e);
                        mTvResult.setText("isNetworkError:" + isNetworkError);
                    }
                });
    }
}


