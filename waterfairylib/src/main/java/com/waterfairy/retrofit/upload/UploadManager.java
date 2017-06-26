package com.waterfairy.retrofit.upload;

import com.waterfairy.retrofit.base.BaseManager;
import com.waterfairy.retrofit.base.BaseProgressInfo;
import com.waterfairy.retrofit.download.DownloadControl;

/**
 * Created by water_fairy on 2017/6/26.
 * 995637517@qq.com
 */

public class UploadManager extends BaseManager {
    public static UploadManager UPLOAD_MANGER;

    private UploadManager() {

    }

    public static UploadManager getInstance() {
        if (UPLOAD_MANGER == null) UPLOAD_MANGER = new UploadManager();
        return UPLOAD_MANGER;
    }


    @Override
    protected DownloadControl newDownloadControl(BaseProgressInfo downloadInfo) {
        return null;
    }

    @Override
    protected UploadControl newUploadControl(BaseProgressInfo downloadInfo) {
        return new UploadControl(downloadInfo);
    }
}
