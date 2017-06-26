package com.waterfairy.retrofit.upload;

import com.waterfairy.retrofit.base.IBaseControl;
import com.waterfairy.retrofit.download.DownloadInterceptor;
import com.waterfairy.retrofit.base.BaseProgress;
import com.waterfairy.retrofit.download.IDownloadService;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by water_fairy on 2017/6/26.
 * 995637517@qq.com
 */

public class UploadControl implements IBaseControl {
    private IUploadService uploadService;
    private UploadInfo uploadInfo;

    public void initUpload() {
//        if (uploadService == null) {
//            uploadService = new BaseProgress(uploadInfo, this);
//            OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                    .connectTimeout(uploadInfo.getTimeOut(), TimeUnit.SECONDS)
//                    .addInterceptor(new DownloadInterceptor(downloadProgress))
//                    .build();
//            uploadService = new Retrofit.Builder()
//                    .client(okHttpClient)
//                    .baseUrl(downloadInfo.getBasePath())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .callbackExecutor(Executors.newCachedThreadPool())
//                    .build()
//                    .create(IDownloadService.class);
//        }
    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }
}
