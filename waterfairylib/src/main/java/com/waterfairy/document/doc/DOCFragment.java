package com.waterfairy.document.doc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.waterfairy.document.OnOfficeLoadListener;
import com.waterfairy.utils.MD5Utils;

import java.io.File;

/**
 * Created by water_fairy on 2017/7/6.
 * 995637517@qq.com
 */

public class DOCFragment extends Fragment {
    private WebView webView;
    private String docPath;
    private OnOfficeLoadListener onLoadingListener;
    private String sdPath;

    public void setDocPath(String docPath) {
        this.docPath = docPath;

    }

    public void loadDoc(String docPath) {
        setDocPath(docPath);
        initPath();
        initDocData();
    }

    private void initPath() {
        sdPath = getActivity().getExternalCacheDir() + "/doc/" + MD5Utils.getMD5Code(docPath);
        File file = new File(sdPath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                sdPath = null;
            }
        }
    }

    public static DOCFragment newInstance() {
        return new DOCFragment();
    }

    public void initDocData() {

        onLoadingListener.onLoading("");
        new Thread() {
            @Override
            public void run() {
                WordUtil wordUtil = new WordUtil(docPath, sdPath);
                wordUtil.load(new WordUtil.OnDocLoadListener() {
                    @Override
                    public void onLoadSuccess(final String htmlPath) {
                        FragmentActivity activity = getActivity();
                        if (activity == null) return;
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                webView.loadUrl("file:///" + htmlPath);
                                onLoadingListener.onLoadSuccess();
                            }
                        });
                    }

                    @Override
                    public void onLoadError(Exception e) {
                        FragmentActivity activity = getActivity();
                        if (activity == null) return;
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onLoadingListener.onLoadError();
                            }
                        });
                    }
                });


            }
        }.start();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String docPath = savedInstanceState.getString("docPath");
            if (!TextUtils.isEmpty(docPath)) {
                this.docPath = docPath;
            }
        }
        webView = new WebView(getActivity());
        initPath();
        initDocData();
        return webView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("docPath", docPath);
        super.onSaveInstanceState(outState);
    }

    public void setOnLoadingListener(OnOfficeLoadListener onLoadingListener) {
        this.onLoadingListener = onLoadingListener;
    }
}
