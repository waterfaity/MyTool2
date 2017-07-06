package com.waterfairy.tool2.activity;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.waterfairy.document.doc.DOCFragment;
import com.waterfairy.tool2.R;

public class DocActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ppt);
        DOCFragment docFragment = DOCFragment.newInstance();
        docFragment.setDocPath(Environment.getExternalStorageDirectory().getPath() + "/documentTest/doc.doc");
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout, docFragment).commit();
    }
}
