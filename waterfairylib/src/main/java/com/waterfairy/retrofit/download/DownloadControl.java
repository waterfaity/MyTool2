package com.waterfairy.retrofit.download;

import com.waterfairy.retrofit.base.BaseManager;
import com.waterfairy.retrofit.base.BaseProgress;
import com.waterfairy.retrofit.base.FileWriter;
import com.waterfairy.retrofit.base.IBaseControl;
import com.waterfairy.retrofit.base.OnBaseListener;
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

public class DownloadControl implements IBaseControl, OnBaseProgressSuccessListener {
    public static final int INIT = 0;
    public static final int START = 1;
    public static final int DOWNLOADING = 2;
    public static final int PAUSE = 3;
    public static final int STOP = 4;
    public static final int FINISH = 5;
    public static final int ERROR = 6;

    private DownloadInfo downloadInfo;
    private IDownloadService downloadService;
    private BaseProgress downloadProgress;
    private Call<ResponseBody> call;
    private String url;
    private int downloadState;

    public DownloadControl(DownloadInfo info) {
        this.downloadInfo = info;
        url = downloadInfo.getUrl();
        initDownload();
    }

    public DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }

    private void initDownload() {
        if (downloadService == null) {
            downloadProgress = new BaseProgress(downloadInfo, this);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(downloadInfo.getTimeOut(), TimeUnit.SECONDS)
                    .addInterceptor(new DownloadInterceptor(downloadProgress))
                    .build();
            downloadService = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(downloadInfo.getBasePath())
                    .addConverterFactory(GsonConverterFactory.create())
                    .callbackExecutor(Executors.newCachedThreadPool())
                    .build()
                    .create(IDownloadService.class);
        }
    }

    public DownloadControl setDownloadListener(OnBaseListener onDownloadListener) {
        if (downloadProgress != null)
            downloadProgress.setOnDownloadListener(onDownloadListener);
        return this;
    }

    @Override
    public void start() {
        if (downloadState == DOWNLOADING) {
            returnError(BaseManager.ERROR_IS_DOWNLOADING);
        } else if (downloadState == FINISH) {
            returnError(BaseManager.ERROR_HAS_FINISHED);
//        }
//        else  if (downloadState == BaseManager.STOP) {
//            returnError(BaseManager.ERROR_HAS_STOP);
        } else {
            call = downloadService.download("bytes=" + downloadInfo.getCurrentLen() + "-", url);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    new FileWriter().writeFile(
                            getDownloadListener(),
                            response.body(),
                            downloadInfo);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                    returnError(BaseManager.ERROR_NET);
                }
            });
            OnBaseListener downloadListener = getDownloadListener();
            if (downloadListener != null) {
                if (downloadState == PAUSE || downloadState == STOP) {
                    returnChange(BaseManager.CONTINUE);
                } else {
                    returnChange(BaseManager.START);
                }
            }
            downloadState = DOWNLOADING;
        }
    }


    private void returnChange(int code) {
        OnBaseListener downloadListener = getDownloadListener();
        if (downloadListener != null) downloadListener.onChange(code);
    }

    private void returnError(int code) {
        OnBaseListener downloadListener = getDownloadListener();
        if (downloadListener != null) downloadListener.onError(code);
    }

    private OnBaseListener getDownloadListener() {
        OnBaseListener onDownloadListener = null;
        if (downloadProgress != null)
            onDownloadListener = downloadProgress.getOnDownloadListener();
        return onDownloadListener;
    }

    @Override
    public void pause() {
        handle(PAUSE);
        returnChange(BaseManager.PAUSE);
    }

    private void handle(int state) {
        if (call != null)
            call.cancel();
        downloadState = state;

    }

    @Override
    public void stop() {
        handle(STOP);
        if (downloadInfo != null) {
            downloadInfo.setCurrentLen(0);
            File file = new File(downloadInfo.getSavePath());
            if (file.exists()) file.delete();
        }
        returnChange(BaseManager.STOP);
    }

    public int getState() {
        return downloadState;
    }

    @Override
    public void onDownloadSuccess(String url) {
        downloadState = FINISH;
        DownloadManager.getInstance().onFinished(url);
        returnChange(BaseManager.FINISHED);
    }
}
