package com.waterfairy.tool2.activity;

import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.itsrts.pptviewer.PPTViewer;
import com.olivephone.office.TempFileManager;
import com.olivephone.office.powerpoint.DocumentSession;
import com.olivephone.office.powerpoint.DocumentSessionBuilder;
import com.olivephone.office.powerpoint.DocumentSessionStatusListener;
import com.olivephone.office.powerpoint.android.AndroidMessageProvider;
import com.olivephone.office.powerpoint.android.AndroidSystemColorProvider;
import com.olivephone.office.powerpoint.android.AndroidTempFileStorageProvider;
import com.olivephone.office.powerpoint.view.PersentationView;
import com.olivephone.office.powerpoint.view.SlideShowNavigator;
import com.olivephone.office.powerpoint.view.SlideView;
import com.waterfairy.tool2.R;

import java.io.File;

import im.delight.android.webview.AdvancedWebView;

public class PPTActivity extends AppCompatActivity implements DocumentSessionStatusListener {

    String basePath = Environment.getExternalStorageDirectory().getPath() + "/documentTest";
    String pptPath = Environment.getExternalStorageDirectory().getPath() + "/documentTest/ppt.ppt";
    private String baseUrl = "https://view.officeapps.live.com/op/view.aspx?src=";
    private String ppt1 = "http://h.xueduoduo.com.cn/data4/other/2016/08/18/152717121948578.ppt";
    private AdvancedWebView advancedWebView;
    private PPTViewer pptViewer;
    private PersentationView persentationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppt);
        advancedWebView = (AdvancedWebView) findViewById(R.id.webview);
        pptViewer = (PPTViewer) findViewById(R.id.ppt_view);
//        getImgs();
//        showPPt();
        initView();
        getPpt();

    }

    private void initView() {
        persentationView = (PersentationView) findViewById(R.id.persentation_view);
    }


    private void showPPt() {
        pptViewer.setNext_img(R.mipmap.next).setPrev_img(R.mipmap.prev)
                .setSettings_img(R.mipmap.settings)
                .setZoomin_img(R.mipmap.zoomin)
                .setZoomout_img(R.mipmap.zoomout);
        pptViewer.loadPPT(this, pptPath);
        int totalSlides = pptViewer.getTotalSlides();
        pptViewer.onSessionStarted();
    }

    private void getImgs() {

    }

    DocumentSession build;

    public void getPpt() {
        try {
            AndroidMessageProvider e = new AndroidMessageProvider(this);
            TempFileManager tmpFileManager = new TempFileManager(new AndroidTempFileStorageProvider(this));
            AndroidSystemColorProvider sysColorProvider = new AndroidSystemColorProvider();
            build = (new DocumentSessionBuilder(new File(pptPath))).setMessageProvider(e).setTempFileManager(tmpFileManager).setSystemColorProvider(sysColorProvider).setSessionStatusListener(this).build();
            build.startSession();
        } catch (Exception var5) {
            var5.printStackTrace();
        }
    }

    @Override
    public void onSessionStarted() {

    }

    SlideShowNavigator slideShowNavigator;

    @Override
    public void onDocumentReady() {
        runOnUiThread(new Runnable() {
            public void run() {

                slideShowNavigator = new SlideShowNavigator(build.getPPTContext());
                int num = slideShowNavigator.getFirstSlideNumber() - 1;

                SlideView slideShow = slideShowNavigator.navigateToSlide(persentationView.getGraphicsContext(), num + 1);
                int width = slideShow.a();//1600
                int height = slideShow.b();//900
                persentationView.setContentView(slideShow);

                int width1 = persentationView.getWidth();//1080
                persentationView.notifyScale((float) width1 / width / 4);
                ViewGroup.LayoutParams layoutParams = persentationView.getLayoutParams();
                layoutParams.height = (int) ((float) height * width1 / width);
                persentationView.setLayoutParams(layoutParams);

            }
        });
    }

    @Override
    public void onDocumentException(Exception e) {

    }

    @Override
    public void onSessionEnded() {

    }
}
