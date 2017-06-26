package com.waterfairy.retrofit.upload;

import com.waterfairy.retrofit.base.BaseProgressInfo;

/**
 * Created by water_fairy on 2017/6/26.
 * 995637517@qq.com
 */

public class UploadInfo extends BaseProgressInfo {
    public UploadInfo(String basePath, String url) {
        this.basePath = basePath;
        this.url = url;
    }

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
