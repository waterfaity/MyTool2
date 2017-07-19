package com.waterfairy.tool2.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.waterfairy.tool2.R;
import com.waterfairy.tool2.service.TestService;

public class ServiceTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);
        Intent intent = new Intent(this, TestService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        stopService(new Intent(this, TestService.class));
    }
}
