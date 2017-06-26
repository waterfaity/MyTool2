package com.waterfairy.retrofit.base;

import com.waterfairy.retrofit.download.DownloadInfo;

/**
 * Created by shui on 2017/5/6.
 */

public class BaseProgress implements OnBaseProgressListener {
    private OnBaseListener onDownloadListener;//开放给用户的接口
    private OnBaseProgressSuccessListener onDownloadSuccessListener;//内部接口 下载完成时关闭control
    private DownloadInfo downloadInfo;//下载信息

    public void setOnDownloadListener(OnBaseListener onDownloadListener) {
        this.onDownloadListener = onDownloadListener;

    }

    public OnBaseListener getOnDownloadListener() {
        return onDownloadListener;

    }

    public BaseProgress(DownloadInfo downloadInfo, OnBaseProgressSuccessListener onDownloadSuccessListener) {
        this.downloadInfo = downloadInfo;
        this.onDownloadSuccessListener = onDownloadSuccessListener;
    }


    @Override
    public void onDownloading(boolean done, long total, long current) {
        current = downloadInfo.getLastLen() + current;
        downloadInfo.setCurrentLen(current);
        if (done) {
            downloadInfo.setState(DownloadInfo.FINISH);
            onDownloadSuccessListener.onDownloadSuccess(downloadInfo.getUrl());
        }
        if (onDownloadListener != null)
            onDownloadListener.onDownloading(done, downloadInfo.getTotalLen(), current);
    }

}