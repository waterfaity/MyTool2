package com.waterfairy.retrofit.download;

/**
 * Created by shui on 2017/5/6.
 */

public interface IDownloadManager {
    /**
     * 添加下载
     *
     * @param downloadInfo
     * @return
     */
    DownloadControl add(DownloadInfo downloadInfo);

    /**
     * 获取下载
     *
     * @param url
     * @return
     */
    DownloadControl get(String url);

    /**
     * 移除下载(会删除当前下载中的文件)
     *
     * @param url
     */
    boolean remove(String url);

    /**
     * 移除所有下载(会删除当前下载中的文件)
     */
    boolean removeAll();

    /**
     * 暂停所有下载
     */
    boolean pauseAll();

    /**
     * 停止所有下载
     */
    boolean stopAll();

    /**
     * 开始所有下载
     */
    boolean startAll();

    /**
     * 下载完成
     * @param url
     */
    void onFinished(String url);
}
