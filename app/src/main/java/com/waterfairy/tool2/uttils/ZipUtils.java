package com.waterfairy.tool2.uttils;

import android.util.Log;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by water_fairy on 2017/7/7.
 * 995637517@qq.com
 */

public class ZipUtils {
    private static final String TAG = "zipUtils";

    public static void openZipFile(String path) {
        try {
            ZipFile zipFile = new ZipFile(path);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            Log.i(TAG, "openZipFile: " + entries.hasMoreElements());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
