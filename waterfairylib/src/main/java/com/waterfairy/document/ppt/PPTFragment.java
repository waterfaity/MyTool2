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

public class PPTFragment extends Fragment {
    private RelativeLayout rootView;
    private ListView listView;
    private PPTAdapter pptAdapter;
    private ImageView freshView;
    private DocumentSession build;
    private String pptPath;

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
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
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


        new PPTEntity().openPPT(getActivity(), pptPath, new PPTEntity.OnPPTOpenListener() {

            @Override
            public void onReady(final SlideShowNavigator slideShowNavigator) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pptAdapter = new PPTAdapter(slideShowNavigator, getActivity(), listView.getWidth());
                        listView.setAdapter(pptAdapter);
                    }
                });
            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onOpening() {

            }
        });
    }


}
