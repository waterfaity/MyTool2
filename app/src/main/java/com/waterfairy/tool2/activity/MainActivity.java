package com.waterfairy.tool2.activity;

import android.content.Intent;
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
        PermissionUtils.requestPermission(this, PermissionUtils.REQUEST_STORAGE);
        PermissionUtils.requestPermission(this, PermissionUtils.REQUEST_LOCATION);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.widget:
                startActivity(new Intent(this, HistogramActivity.class));
                break;   case R.id.down:
                startActivity(new Intent(this, DownloadActivity.class));
                break;
        }

    }
}
