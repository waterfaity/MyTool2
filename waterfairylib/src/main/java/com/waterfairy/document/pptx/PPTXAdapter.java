package com.waterfairy.document.pptx;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.pbdavey.awt.Graphics2D;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.util.concurrent.atomic.AtomicBoolean;

import and.awt.Dimension;

/**
 * Created by water_fairy on 2017/7/7.
 * 995637517@qq.com
 */

class PPTXAdapter extends BaseAdapter {

    private Context context;
    ;
    private int width;
    private XSLFSlide[] xslfSlides;
    private XMLSlideShow xmlSlideShow;
    private Dimension dimension;

    public PPTXAdapter(Context context, XMLSlideShow xslfSlides, int width) {
        this.context = context;
        this.dimension = xslfSlides.getPageSize();
        this.xslfSlides = xslfSlides.getSlides();
        this.width = width;
    }

    @Override
    public int getCount() {
        if (xslfSlides == null) return 0;
        return xslfSlides.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new LinearLayout(context);
        }
        LinearLayout linearLayout = (LinearLayout) convertView;
        linearLayout.removeAllViews();
        ImageView imageView = new ImageView(context);
        linearLayout.addView(imageView);

        int widthTemp = (int) dimension.getWidth();
        int heightTemp = (int) dimension.getHeight();

        XSLFSlide xslfSlide = xslfSlides[position];
        Bitmap bitmap = Bitmap.createBitmap(widthTemp, heightTemp, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        canvas.drawPaint(paint);
        Graphics2D graphics2D = new Graphics2D(canvas);
        new LoadThread(imageView, xslfSlide, graphics2D, position, bitmap).start();
//        xslfSlide.draw(graphics2D, new AtomicBoolean(false), handler, position);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        imageView.setImageBitmap(bitmap);
        layoutParams.width = width;
        layoutParams.height = (int) ((float) heightTemp * width / widthTemp);
        imageView.setLayoutParams(layoutParams);
        return convertView;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                View imageView = (View) msg.obj;
                imageView.invalidate();
            } else {
                Log.i("tese", "handleMessage: " + msg.what);
            }

        }
    };


    private class LoadThread extends Thread {
        private ImageView imageView;
        private XSLFSlide xslfSlide;
        private Graphics2D graphics2D;
        private int position;
        private Bitmap bitmap;

        public LoadThread(ImageView imageView, XSLFSlide xslfSlide, Graphics2D graphics2D, int position, Bitmap bitmap) {
            this.imageView = imageView;
            this.xslfSlide = xslfSlide;
            this.graphics2D = graphics2D;
            this.position = position;
            this.bitmap = bitmap;
        }

        @Override
        public void run() {
            xslfSlide.draw(graphics2D, new AtomicBoolean(false), handler, position);
            imageView.setImageBitmap(bitmap);
//            handler.sendMessage(Message.obtain(handler, 0,
//                    position, 0, imageView));
        }
    }
}

