package com.waterfairy.retrofit.download;

/**
 * Created by shui on 2017/5/6.
 */

public interface OnDownloadingListener {
    void onDownloading(boolean done, long total, long current);
}
