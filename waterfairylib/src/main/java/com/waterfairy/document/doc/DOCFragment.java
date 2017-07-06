package com.waterfairy.document.doc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by water_fairy on 2017/7/6.
 * 995637517@qq.com
 */

public class DOCFragment extends Fragment {
    private WebView webView;
    private String docPath;

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    public void loadDoc(String docPath) {
        setDocPath(docPath);
        initDocData();
    }

    public static DOCFragment newInstance() {
        return new DOCFragment();
    }

    private void initDocData() {
        WordUtil wordUtil = new WordUtil(docPath);
        String htmlPath = wordUtil.htmlPath;
        webView.loadUrl("file:///" + htmlPath);
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
        initDocData();
        return webView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("docPath", docPath);
        super.onSaveInstanceState(outState);
    }
}
