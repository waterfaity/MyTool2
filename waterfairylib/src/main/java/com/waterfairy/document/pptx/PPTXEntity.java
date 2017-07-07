package com.waterfairy.document.pptx;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xslf.usermodel.XMLSlideShow;

/**
 * Created by water_fairy on 2017/7/7.
 * 995637517@qq.com
 */

public class PPTXEntity {
    private OnOpenPPTXListener onOpenPPTXListener;

    public void openPPTX( final String path, final OnOpenPPTXListener listener) {
        this.onOpenPPTXListener = listener;
        onOpenPPTXListener.onOpening();
        new Thread() {
            @Override
            public void run() {
                try {
                    XMLSlideShow xmlSlideShow = new XMLSlideShow(OPCPackage.open(path,
                            PackageAccess.READ));
                    onOpenPPTXListener.onReady(xmlSlideShow);
                } catch (InvalidFormatException e) {
                    e.printStackTrace();
                    listener.onError(e);
                }
            }
        }.run();

    }

    public interface OnOpenPPTXListener {
        void onReady(XMLSlideShow slideShowNavigator);

        void onError(Exception e);

        void onOpening();
    }
}
