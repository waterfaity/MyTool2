package com.waterfairy.tool2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.waterfairy.tool2.R;


public class OpenDocActivity extends AppCompatActivity {
    String basePath = Environment.getExternalStorageDirectory().getPath() + "/documentTest/";
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_doc_actvity);

    }

    public void openPPT(View view) {
        path = basePath + "ppt.ppt";
        Intent intent = new Intent(this, PPT2Activity.class);
        startActivity(intent);

    }

    public void openPPTX(View view) {
        Intent intent = new Intent(this, MyPPTXActivity.class);
        startActivity(intent);

    }

    public void openDOC(View view) {
        path = basePath + "doc.doc";
//        DocumentReadUtils.readDOC(path);
        Intent intent = new Intent(this, DocActivity.class);
        startActivity(intent);
    }

    public void openDOCX(View view) {
        path = basePath + "docx.docx";
//        DocumentReadUtils.readDOCX(path);
    }

    public void openXLS(View view) {
        path = basePath + "xls.xls";
//        DocumentReadUtils.readXLS(path);
    }

    public void openXLSX(View view) {
        path = basePath + "xlsx.xlsx";
//        DocumentReadUtils.readXLSX(path);
    }
}
