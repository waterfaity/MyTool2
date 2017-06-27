package com.waterfairy.tool2.activity;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.waterfairy.retrofit2.base.BaseManager;
import com.waterfairy.retrofit2.base.OnProgressListener;
import com.waterfairy.retrofit2.download.DownloadControl;
import com.waterfairy.retrofit2.download.DownloadInfo;
import com.waterfairy.retrofit2.download.DownloadManager;
import com.waterfairy.retrofit2.upload.UploadControl;
import com.waterfairy.retrofit2.upload.UploadInfo;
import com.waterfairy.retrofit2.upload.UploadManager;
import com.waterfairy.tool2.R;
import com.waterfairy.utils.PermissionUtils;
import com.waterfairy.utils.ToastUtils;

public class DownloadActivity extends AppCompatActivity {
    private DownloadControl downloadControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        PermissionUtils.requestPermission(this, PermissionUtils.REQUEST_STORAGE);

        DownloadInfo downloadInfo = new DownloadInfoBean();
        downloadInfo.setBasePath("http://www.baidu.com");
//        downloadInfo.setUrl("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1498486122&di=ed3fcaa4538237471a13d36c455f0e12&src=http://imgsrc.baidu.com/imgad/pic/item/77094b36acaf2eddd2df80cc871001e9390193ed.jpg");
//        downloadInfo.setUrl("http://p.gdown.baidu.com/4984f94093a75d68618a61b288bf3835126624f9e28ae99e93f3e2d8d04ef89c42507e69a9625d5b3a1852d6227e922086809cd5df2e7581696e8906bf469476e5018ed6e5f6800463ec05eefe22ad73810c37428f640d90bfae8234838875395c4ade3a8734263e3d1437b17174673dcfcf28a5cc1c12a4851c15dd6cfbb585857dbd4fb6a0f34f30272e4b21ad857e0916dccd83bd227651db8d9c6d466230e2ac257ec468347e87ac3ebe5096434f13809f8c37646b6b6aea5afb3e8a3e851a3f86797e2da535fee44a6791578d535d2df44e89236f8a3cb3700e97b7ceb30a647315f663833bfe63480a13bd4862d37aeb0e559049e7aa395dc3c364b294d231191c251ed1de");
        downloadInfo.setUrl("https://downloads.gradle.org/distributions/gradle-4.0-bin.zip");
        downloadInfo.setSavePath(Environment.getExternalStorageDirectory().getPath() + "/ZTest/jj.zip");
        downloadControl = (DownloadControl) DownloadManager.getInstance().get(downloadInfo.getUrl());
        if (downloadControl == null) {
            DownloadManager instance = DownloadManager.getInstance();
            downloadControl = (DownloadControl) instance.add(downloadInfo);
            downloadControl.start();

        }
        downloadControl.setLoadListener(new OnProgressListener() {
            @Override
            public void onLoading(boolean done, final long total, final long current) {
                Log.i("test", "onLoading: " + done + "--" + total + "--" + current);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Button button = (Button) DownloadActivity.this.findViewById(R.id.bt);
                        button.setText("" + (int) (current / (float) total * 100));
//                        ToastUtils.show(current + "--" + total);
                    }
                });
            }

            @Override
            public void onError(final int code) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.show(code + "");
                    }
                });

            }

            @Override
            public void onChange(int code) {

            }
        });

//        UploadInfo uploadInfo = new UploadInfo("http://www.baidu.com", "http://www.baidu.com",
//                Environment.getExternalStorageDirectory().getPath() + "/ZTest/jj.apk", "jj.apk");
//        UploadManager uploadManager = UploadManager.getInstance();
//        UploadControl uploadControl = (UploadControl) uploadManager.add(uploadInfo);
//        uploadControl.setLoadListener(new OnProgressListener() {
//            @Override
//            public void onLoading(boolean done, long total, long current) {
//                Log.i("test", "onLoading: " + total + "--" + current);
//            }
//
//            @Override
//            public void onError(int code) {
//                Log.i("test", "onError: " + code);
//
//            }
//
//            @Override
//            public void onChange(int code) {
//                Log.i("test", "onChange: " + code);
//
//            }
//        });
//        uploadControl.start();

    }

    public void onClick(View view) {
        int state = downloadControl.getState();
        if (state != BaseManager.CONTINUE) {
            downloadControl.start();
        } else {
            downloadControl.pause();
        }

    }
}
