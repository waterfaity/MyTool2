package com.waterfairy.tool2.activity;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.waterfairy.tool2.R;
import com.waterfairy.utils.PermissionUtils;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionUtils.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        PermissionUtils.requestPermission(this, PermissionUtils.REQUEST_STORAGE);
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
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
