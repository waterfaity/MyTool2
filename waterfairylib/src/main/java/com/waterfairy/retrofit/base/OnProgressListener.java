package com.waterfairy.retrofit.base;

/**
 * Created by shui on 2017/4/26.
 */

public interface OnProgressListener {

    void onDownloading(boolean done, long total, long current);

    void onError(int code);

    void onChange(int code);
}
