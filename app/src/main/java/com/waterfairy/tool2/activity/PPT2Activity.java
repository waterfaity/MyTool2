package com.waterfairy.tool2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.itsrts.pptviewer.PPTViewer;
import com.waterfairy.document.ppt.PPTViewReader2;
import com.waterfairy.tool2.R;

public class PPT2Activity extends AppCompatActivity {

    PPTViewer pptViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppt2);
        PPTViewReader2 pptViewer = (PPTViewReader2) findViewById(R.id.ppt_view);
        pptViewer.setWidth(getResources().getDisplayMetrics().widthPixels);
        pptViewer.loadPPT(this, "/sdcard/documentTest/ppt.ppt");
    }
}
