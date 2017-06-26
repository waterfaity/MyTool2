package com.waterfairy.retrofit.base;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by shui on 2017/4/26.
 * 文件下载控制 返回boolean  值为isDownloading
 */

public abstract class IBaseControl {
    protected BaseProgressInfo baseProgressInfo;
    protected BaseProgress baseProgress;
    protected int baseProgressState;
    protected Call<ResponseBody> call;
    protected String url;

    /**
     * 开始 继续
     *
     * @return isDownloading
     */
    protected void start() {
        OnProgressListener downloadListener = getDownloadListener();
        if (downloadListener != null) {
            if (baseProgressState == BaseManager.PAUSE || baseProgressState == BaseManager.STOP) {
                returnChange(BaseManager.CONTINUE);
            } else {
                returnChange(BaseManager.START);
            }
        }
        baseProgressState = BaseManager.CONTINUE;
    }

    /**
     * 暂停
     *
     * @return isDownloading
     */
    public void pause() {
        if (call != null)
            call.cancel();
        returnChange(BaseManager.PAUSE);

    }

    /**
     * 停止 同暂停(待定)
     *
     * @return isDownloading
     */
    public abstract void stop();


    public int getState() {
        return baseProgressState;
    }

    protected void returnChange(int code) {
        baseProgressState = code;
        OnProgressListener downloadListener = getDownloadListener();
        if (downloadListener != null) downloadListener.onChange(code);
    }

    protected void returnError(int code) {
        baseProgressState = code;
        OnProgressListener downloadListener = getDownloadListener();
        if (downloadListener != null) downloadListener.onError(code);
    }

    protected OnProgressListener getDownloadListener() {
        OnProgressListener onDownloadListener = null;
        if (baseProgress != null)
            onDownloadListener = baseProgress.getOnProgressListener();
        return onDownloadListener;
    }

    public BaseProgressInfo getBaseProgressInfo() {
        return baseProgressInfo;
    }

    public IBaseControl setDownloadListener(OnProgressListener onDownloadListener) {
        if (baseProgress != null)
            baseProgress.setOnProgressListener(onDownloadListener);
        return this;
    }

}
