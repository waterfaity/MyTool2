package com.waterfairy.retrofit.download;

import com.waterfairy.retrofit.base.BaseManager;
import com.waterfairy.retrofit.base.BaseProgress;
import com.waterfairy.retrofit.base.BaseProgressInfo;
import com.waterfairy.retrofit.base.FileWriter;
import com.waterfairy.retrofit.base.IBaseControl;
import com.waterfairy.retrofit.base.OnBaseProgressSuccessListener;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shui on 2017/5/6.
 */

public class DownloadControl extends IBaseControl implements OnBaseProgressSuccessListener {


    private IDownloadService downloadService;

    public DownloadControl(BaseProgressInfo info) {
        this.baseProgressInfo = info;
        url = baseProgressInfo.getUrl();
        initDownload();
    }


    private void initDownload() {
        if (downloadService == null) {
            baseProgress = new BaseProgress(baseProgressInfo, this);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(baseProgressInfo.getTimeOut(), TimeUnit.SECONDS)
                    .addInterceptor(new DownloadInterceptor(baseProgress))
                    .build();
            downloadService = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(baseProgressInfo.getBasePath())
                    .addConverterFactory(GsonConverterFactory.create())
                    .callbackExecutor(Executors.newCachedThreadPool())
                    .build()
                    .create(IDownloadService.class);
        }
    }


    @Override
    public void start() {
        if (baseProgressState == BaseManager.CONTINUE) {
            returnError(BaseManager.ERROR_IS_DOWNLOADING);
        } else if (baseProgressState == BaseManager.FINISHED) {
            returnError(BaseManager.ERROR_HAS_FINISHED);
//        }
//        else  if (baseProgressState == BaseManager.STOP) {
//            returnError(BaseManager.ERROR_HAS_STOP);
        } else {
            call = downloadService.download("bytes=" + baseProgressInfo.getCurrentLen() + "-", url);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    new FileWriter().writeFile(
                            getDownloadListener(),
                            response.body(),
                            (DownloadInfo) baseProgressInfo);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                    returnError(BaseManager.ERROR_NET);
                }
            });
           super.start();
        }
    }


    @Override
    public void stop() {
        if (baseProgressInfo != null) {
            baseProgressInfo.setCurrentLen(0);
            File file = new File(((DownloadInfo) baseProgressInfo).getSavePath());
            if (file.exists()) file.delete();
        }
        returnChange(BaseManager.STOP);
    }


    @Override
    public void onProgressSuccess(String url) {
        DownloadManager.getInstance().onFinished(url);
        returnChange(BaseManager.FINISHED);
    }
}
