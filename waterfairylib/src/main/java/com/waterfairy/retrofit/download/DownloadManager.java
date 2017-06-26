package com.waterfairy.retrofit.download;

import com.waterfairy.retrofit.base.BaseManager;
import com.waterfairy.retrofit.base.BaseProgressInfo;
import com.waterfairy.retrofit.base.IBaseControl;
import com.waterfairy.retrofit.upload.UploadControl;
import com.waterfairy.retrofit.upload.UploadInfo;

/**
 * Created by water_fairy on 2017/6/26.
 * 995637517@qq.com
 */

public class DownloadManager extends BaseManager {
    public static DownloadManager DOWNLOAD_MANGER;

    private DownloadManager() {

    }

    public static DownloadManager getInstance() {
        if (DOWNLOAD_MANGER == null) DOWNLOAD_MANGER = new DownloadManager();
        return DOWNLOAD_MANGER;
    }


    @Override
    protected DownloadControl newDownloadControl(BaseProgressInfo downloadInfo) {
        return new DownloadControl((DownloadInfo) downloadInfo);
    }

    @Override
    protected UploadControl newUploadControl(BaseProgressInfo downloadInfo) {
        return null;
    }
}
