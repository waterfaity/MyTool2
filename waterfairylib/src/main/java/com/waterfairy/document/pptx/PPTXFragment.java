package com.waterfairy.document.pptx;

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

import org.apache.poi.xslf.usermodel.XMLSlideShow;

/**
 * 使用itsrts-pptviewer.jar 包
 * Created by water_fairy on 2017/7/6.
 * 995637517@qq.com
 */

public class PPTXFragment extends Fragment {
    private RelativeLayout rootView;
    private ListView listView;
    private PPTXAdapter pptAdapter;
    private ImageView freshView;
    private String pptPath;

    public static PPTXFragment newInstance() {
        return new PPTXFragment();
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
        new PPTXEntity().openPPTX(pptPath, new PPTXEntity.OnOpenPPTXListener() {
            @Override
            public void onReady(final XMLSlideShow xmlSlideShow) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pptAdapter = new PPTXAdapter(getActivity(), xmlSlideShow, getActivity().getResources().getDisplayMetrics().widthPixels);
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


//        new PPTEntity().openPPT(getActivity(), pptPath, new PPTEntity.OnPPTOpenListener() {
//
//            @Override
//            public void onReady(final SlideShowNavigator slideShowNavigator) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        pptAdapter = new PPTAdapter(slideShowNavigator, getActivity(), listView.getWidth());
//                        listView.setAdapter(pptAdapter);
//                    }
//                });
//            }
//
//            @Override
//            public void onError(Exception e) {
//
//            }
//
//            @Override
//            public void onOpening() {
//
//            }
//        });
    }


}
