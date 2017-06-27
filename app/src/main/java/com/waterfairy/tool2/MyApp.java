package com.waterfairy.tool2;

import android.app.Application;
import android.content.Context;

import com.waterfairy.crash.CrashHandler;
import com.waterfairy.utils.ToastUtils;

/**
 * Created by water_fairy on 2017/6/26.
 * 995637517@qq.com
 */

public class MyApp extends Application {
    private static MyApp app;

    public static Context getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this).setPath("/sdcard/ZTest/crashLog");
        ToastUtils.initToast(this);
        app = this;
    }
}
