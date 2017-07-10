package com.waterfairy.tool2.activity;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.waterfairy.tool2.R;
import com.waterfairy.document.ppt.PPTFragment;
import com.waterfairy.utils.PermissionUtils;

public class MyPPTActivity extends AppCompatActivity {
    String pptPath = Environment.getExternalStorageDirectory().getPath() + "/documentTest/ppt.ppt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ppt);
        PermissionUtils.requestPermission(this, PermissionUtils.REQUEST_STORAGE);
        PPTFragment fragment = PPTFragment.newInstance();
        fragment.setPPTPath(pptPath);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout, fragment).commit();
    }
}
