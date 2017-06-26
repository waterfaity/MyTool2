package com.waterfairy.tool2.activity;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.waterfairy.retrofit.base.IBaseControl;
import com.waterfairy.retrofit.base.OnBaseListener;
import com.waterfairy.retrofit.download.DownloadControl;
import com.waterfairy.retrofit.download.DownloadInfo;
import com.waterfairy.retrofit.download.DownloadManager;
import com.waterfairy.tool2.R;
import com.waterfairy.utils.PermissionUtils;
import com.waterfairy.utils.ToastUtils;

public class DownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        PermissionUtils.requestPermission(this, PermissionUtils.REQUEST_STORAGE);

        DownloadInfo downloadInfo = new DownloadInfoBean();
        downloadInfo.setBasePath("http://www.baidu.com");
        downloadInfo.setUrl("http://p.gdown.baidu.com/4984f94093a75d68618a61b288bf3835126624f9e28ae99e93f3e2d8d04ef89c42507e69a9625d5b3a1852d6227e922086809cd5df2e7581696e8906bf469476e5018ed6e5f6800463ec05eefe22ad73810c37428f640d90bfae8234838875395c4ade3a8734263e3d1437b17174673dcfcf28a5cc1c12a4851c15dd6cfbb585857dbd4fb6a0f34f30272e4b21ad857e0916dccd83bd227651db8d9c6d466230e2ac257ec468347e87ac3ebe5096434f13809f8c37646b6b6aea5afb3e8a3e851a3f86797e2da535fee44a6791578d535d2df44e89236f8a3cb3700e97b7ceb30a647315f663833bfe63480a13bd4862d37aeb0e559049e7aa395dc3c364b294d231191c251ed1de");
        downloadInfo.setSavePath(Environment.getExternalStorageDirectory().getPath() + "/ZTest/jj.apk");
        DownloadManager instance = DownloadManager.getInstance();
        DownloadControl add = (DownloadControl) instance.add(downloadInfo);
        add.setDownloadListener(new OnBaseListener() {
            @Override
            public void onDownloading(boolean done, final long total, final long current) {
                Log.i("test", "onDownloading: " + done + "--" + total + "--" + current);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.show(current + "--" + total);
                    }
                });
            }

            @Override
            public void onError(final int code) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.show(code);
                    }
                });

            }

            @Override
            public void onChange(int code) {

            }
        });
        add.start();

    }
}
