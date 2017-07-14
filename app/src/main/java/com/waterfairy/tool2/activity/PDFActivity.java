package com.waterfairy.tool2.activity;

import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnDrawListener;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.waterfairy.tool2.R;

import java.io.File;

public class PDFActivity extends AppCompatActivity implements OnDrawListener, OnLoadCompleteListener, OnPageChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        PDFView pdfView = (PDFView) findViewById(R.id.pdf);
//        pdfView.fromFile(new File("/sdcard/documentTest/pdf.pdf"));
        pdfView.fromFile(new File("/sdcard/documentTest/pdf.pdf"))
                .pages(0, 2, 1, 3, 3, 3)
                .defaultPage(1)
                .showMinimap(false)
                .enableSwipe(true)
                .onDraw(this)
                .onLoad(this)
                .onPageChange(this)
                .load();

    }

    @Override
    public void onLayerDrawn(Canvas canvas, float v, float v1, int i) {

    }

    @Override
    public void loadComplete(int i) {

    }

    @Override
    public void onPageChanged(int i, int i1) {

    }
}
