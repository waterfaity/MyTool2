package com.waterfairy.tool2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.waterfairy.tool2.R;
import com.waterfairy.wifi.manger.WifiManager;

public class SokectActivity extends AppCompatActivity {
    WifiManager instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sokect);
        instance = WifiManager.getInstance();


    }
}
