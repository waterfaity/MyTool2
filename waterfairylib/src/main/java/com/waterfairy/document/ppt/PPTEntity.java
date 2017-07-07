package com.waterfairy.document.ppt;

import android.content.Context;

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
 * Created by water_fairy on 2017/7/7.
 * 995637517@qq.com
 */

public class PPTEntity implements DocumentSessionStatusListener {
    DocumentSession documentSession;
    private OnPPTOpenListener onPPTOpenListener;


    public void openPPT(Context context, String path, OnPPTOpenListener listener) {
        onPPTOpenListener = listener;
        try {
            AndroidMessageProvider e = new AndroidMessageProvider(context);
            TempFileManager tmpFileManager = new TempFileManager(new AndroidTempFileStorageProvider(context));
            AndroidSystemColorProvider sysColorProvider = new AndroidSystemColorProvider();
            documentSession = (new DocumentSessionBuilder(new File(path))).setMessageProvider(e).setTempFileManager(tmpFileManager).setSystemColorProvider(sysColorProvider).setSessionStatusListener(this).build();
            documentSession.startSession();
        } catch (Exception var5) {
            var5.printStackTrace();
        }
    }

    @Override
    public void onSessionStarted() {
        if (onPPTOpenListener != null) {
            onPPTOpenListener.onOpening();
        }
    }

    @Override
    public void onDocumentReady() {
        if (onPPTOpenListener != null) {
            onPPTOpenListener.onReady(new SlideShowNavigator(documentSession.getPPTContext()));
        }
    }

    @Override
    public void onDocumentException(Exception e) {
        if (onPPTOpenListener != null) {
            onPPTOpenListener.onError(e);
        }
    }

    @Override
    public void onSessionEnded() {

    }

    public interface OnPPTOpenListener {
        void onReady(SlideShowNavigator slideShowNavigator);

        void onError(Exception e);

        void onOpening();
    }

    public void stopSession() {
        if (documentSession != null) {
            documentSession.endSession();
        }
    }
}
