package com.waterfairy.document;

/**
 * Created by water_fiay on 2017/7/23.
 * 995637517@qq.com
 */

public interface OnOfficeLoadListener {
    void onLoading(String msg);

    void onLoadSuccess();

    void onLoadError();
}
