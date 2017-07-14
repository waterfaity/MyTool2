package com.waterfairy.document.ppt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.waterfairy.document.OnOfficeLoadListener;
import com.waterfairy.utils.MD5Utils;

import java.io.File;

/**
 * Created by water_fairy on 2017/7/6.
 * 995637517@qq.com
 */

public class PPTFragment extends Fragment implements PPTViewReader.OnPPTLoadListener {
    private PPTViewReader pptViewReader;
    private String pptPath;
    private OnOfficeLoadListener onOfficeLoadListener;
    private String sdPath;

    public void setPPTPath(String pptPath) {
        this.pptPath = pptPath;
    }

    public void loadPPT(String pptPath) {
        setPPTPath(pptPath);
        initPath();
        initPPTData();
    }

    private void initPath() {
        sdPath = getActivity().getExternalCacheDir() + "/ppt/" + MD5Utils.getMD5Code(pptPath);
        File file = new File(sdPath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                sdPath = null;
            }
        }
    }

    public static PPTFragment newInstance() {
        return new PPTFragment();
    }

    public void initPPTData() {
        pptViewReader.setOnPPTLoadingListener(this);
        pptViewReader.setWidth(getResources().getDisplayMetrics().widthPixels);
        if (onOfficeLoadListener != null)
            onOfficeLoadListener.onLoading("");
        pptViewReader.loadPPT(getActivity(), pptPath);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String pptPath = savedInstanceState.getString("pptPath");
            if (!TextUtils.isEmpty(pptPath)) {
                this.pptPath = pptPath;
            }
        }
        pptViewReader = new PPTViewReader(getActivity());
        initPath();
        initPPTData();
        return pptViewReader;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("pptPath", pptPath);
        super.onSaveInstanceState(outState);
    }

    public void setOnLoadingListener(OnOfficeLoadListener onOfficeLoadListener) {
        this.onOfficeLoadListener = onOfficeLoadListener;
    }

    @Override
    public void onLoadSuccess() {
        if (onOfficeLoadListener != null)
            onOfficeLoadListener.onLoadSuccess();
    }

    @Override
    public void onLoadError() {
        if (onOfficeLoadListener != null)
            onOfficeLoadListener.onLoadError();
    }

    @Override
    public void onLoading(final String content) {
        if (onOfficeLoadListener != null) {
            FragmentActivity activity = getActivity();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onOfficeLoadListener.onLoading(content);
                    }
                });
            }
        }
    }
}
