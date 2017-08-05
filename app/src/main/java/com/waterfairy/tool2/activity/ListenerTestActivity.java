package com.waterfairy.tool2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.waterfairy.tool2.R;
import com.waterfairy.tool2.test.ListenerTestEntity;

public class ListenerTestActivity extends AppCompatActivity {
    ListenerTestEntity testEntity = ListenerTestEntity.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listener_test);
        testEntity.startClock();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        testEntity.stopClock();
    }
}
