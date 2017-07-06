package com.waterfairy.document.ppt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.olivephone.office.TempFileManager;
import com.olivephone.office.powerpoint.DocumentSession;
import com.olivephone.office.powerpoint.DocumentSessionBuilder;
import com.olivephone.office.powerpoint.DocumentSessionStatusListener;
import com.olivephone.office.powerpoint.android.AndroidMessageProvider;
import com.olivephone.office.powerpoint.android.AndroidSystemColorProvider;
import com.olivephone.office.powerpoint.android.AndroidTempFileStorageProvider;
import com.olivephone.office.powerpoint.view.SlideShowNavigator;

import java.io.File;

/**
 * 使用itsrts-pptviewer.jar 包
 * Created by water_fairy on 2017/7/6.
 * 995637517@qq.com
 */

public class PPTFragment extends Fragment implements DocumentSessionStatusListener {
    private RelativeLayout rootView;
    private ListView listView;
    private PPTAdapter pptAdapter;
    private ImageView freshView;
    private DocumentSession build;
    private String pptPath;
    private SlideShowNavigator slideShowNavigator;

    public static PPTFragment newInstance() {
        return new PPTFragment();
    }

    public void setPPTPath(String pptPath) {
        this.pptPath = pptPath;
    }

    /**
     * 再次加载
     *
     * @param pptPath
     */
    public void loadPath(String pptPath) {
        this.pptPath = pptPath;
        initPPTData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView();
        initData(savedInstanceState);
        return rootView;
    }

    private void initView() {
        rootView = new RelativeLayout(getActivity());
        listView = new ListView(getActivity());
        freshView = new ImageView(getActivity());
        rootView.addView(listView);
        rootView.addView(freshView);
    }

    private void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String pptPath = savedInstanceState.getString("pptPath");
            if (!TextUtils.isEmpty(pptPath)) {
                this.pptPath = pptPath;
            }
        }

        initPPTData();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("pptPath", pptPath);
        super.onSaveInstanceState(outState);
    }

    private void initPPTData() {
        if (new File(pptPath).exists()) {
        }
        try {
            AndroidMessageProvider e = new AndroidMessageProvider(getActivity());
            TempFileManager tmpFileManager = new TempFileManager(new AndroidTempFileStorageProvider(getActivity()));
            AndroidSystemColorProvider sysColorProvider = new AndroidSystemColorProvider();
            build = (new DocumentSessionBuilder(new File(pptPath))).setMessageProvider(e).setTempFileManager(tmpFileManager).setSystemColorProvider(sysColorProvider).setSessionStatusListener(this).build();
            build.startSession();
        } catch (Exception var5) {
            var5.printStackTrace();
        }
    }

    public void stop() {
        build.endSession();
    }

    @Override
    public void onSessionStarted() {

    }

    @Override
    public void onDocumentReady() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                slideShowNavigator = new SlideShowNavigator(build.getPPTContext());
                pptAdapter = new PPTAdapter(slideShowNavigator, getActivity(), listView.getWidth());
                listView.setAdapter(pptAdapter);
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
