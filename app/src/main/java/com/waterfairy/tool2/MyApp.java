package com.waterfairy.tool2;

import android.app.Application;

import com.waterfairy.crash.CrashHandler;

/**
 * Created by water_fairy on 2017/6/26.
 * 995637517@qq.com
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this).setPath("/sdcard/ZTest/crashLog");
    }
}
