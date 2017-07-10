package com.waterfairy.tool2.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

//import com.waterfairy.document.pptx.PPTXFragment;
//import com.waterfairy.document.pptx.PPTXFragment;
import com.waterfairy.tool2.R;

public class MyPPTXActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ppt);
//        PPTXFragment fragment = PPTXFragment.newInstance();
//        fragment.setPPTPath(Environment.getExternalStorageDirectory().getPath() + "/documentTest/pptx.pptx");
//        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout, fragment).commit();
    }
}
