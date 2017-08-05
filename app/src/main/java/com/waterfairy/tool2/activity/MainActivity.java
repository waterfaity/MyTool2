package com.waterfairy.tool2.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.waterfairy.tool2.R;
import com.waterfairy.tool2.rocketMQ.RocketMQActivity;
import com.waterfairy.tool2.uttils.ZipUtils;
import com.waterfairy.utils.PermissionUtils;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionUtils.requestPermission(this, PermissionUtils.REQUEST_STORAGE);
        test();
    }

    private void test() {
        ZipUtils.openZipFile(Environment.getExternalStorageDirectory().getPath() + "/documentTest/pptx.pptx");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.widget:
                startActivity(new Intent(this, HistogramActivity.class));
                break;
            case R.id.down:
                startActivity(new Intent(this, DownloadActivity.class));
                break;
            case R.id.webview:
                startActivity(new Intent(this, WebViewActivity.class));
                break;
            case R.id.life:
                startActivity(new Intent(this, LifeActivity.class));
                break;
            case R.id.document:
                startActivity(new Intent(this, OpenDocActivity.class));
                break;
            case R.id.lottie:
                startActivity(new Intent(this, LottieActivity.class));
                break;
            case R.id.service:
                startActivity(new Intent(this, ServiceTestActivity.class));
                break;
            case R.id.rocket:
                startActivity(new Intent(this, RocketMQActivity.class));
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
