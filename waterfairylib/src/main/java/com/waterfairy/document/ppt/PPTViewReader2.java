package com.waterfairy.document.ppt;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

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
import com.waterfairy.utils.ImageUtils;
import com.waterfairy.utils.MD5Utils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.R.attr.bitmap;

/**
 * Created by water_fairy on 2017/7/10.
 * 995637517@qq.com
 */

public class PPTViewReader2 extends ScrollView implements DocumentSessionStatusListener {
    private String baseCacheImgPath;
    private DocumentSession session;
    private String path;
    private Context context;
    private Activity activity;
    private SlideShowNavigator navitator;
    private LinearLayout contentView;
    private int width;
    private long fileSize;

    public PPTViewReader2(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        contentView = new LinearLayout(context, attr);
        addView(contentView);
        contentView.setOrientation(LinearLayout.VERTICAL);
        baseCacheImgPath = context.getExternalCacheDir().getPath() + "/img/";
    }

    public void setWidth(int width) {
        this.width = width;

    }

    public void setPath(String path) {
        this.path = path;
    }

    public void loadPPT(Activity activity, String path) {
        this.setPath(path);
        this.loadPPT(activity);
        File file = new File(path);
        if (file.exists()) {
            fileSize = file.length();
        }
    }

    public void loadPPT(Activity activity) {
        this.activity = activity;
        try {
            AndroidMessageProvider e = new AndroidMessageProvider(this.context);
            TempFileManager tmpFileManager = new TempFileManager(new AndroidTempFileStorageProvider(this.context));
            AndroidSystemColorProvider sysColorProvider = new AndroidSystemColorProvider();
            this.session = (new DocumentSessionBuilder(new File(this.path))).setMessageProvider(e).setTempFileManager(tmpFileManager).setSystemColorProvider(sysColorProvider).setSessionStatusListener(this).build();
            this.session.startSession();
        } catch (Exception var5) {
            var5.printStackTrace();
        }
    }


    public void onDocumentException(Exception arg0) {
    }

    public void onDocumentReady() {
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                navitator = new SlideShowNavigator(session.getPPTContext());
                int firstSlideNumber = navitator.getFirstSlideNumber();
                int slideCount = navitator.getSlideCount();
                for (int i = 0; i < slideCount; i++) {
                    addChildView(firstSlideNumber + i);
                }
            }
        });
    }

    private void addChildView(int position) {
        String bitmapFile = baseCacheImgPath + "ppt/" + MD5Utils.getMD5Code(path + fileSize + position);
        Bitmap showBitmap = BitmapFactory.decodeFile(bitmapFile);
        if (showBitmap == null) {
            showBitmap = createBitmap(position, bitmapFile);
        }
        if (showBitmap != null) {
            ImageView imageView = new ImageView(context);
            contentView.addView(imageView);
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width = showBitmap.getWidth();
            layoutParams.height = showBitmap.getHeight();
            imageView.setLayoutParams(layoutParams);
            imageView.setImageBitmap(showBitmap);
        }
    }

    private Bitmap createBitmap(int position, String bitmapFile) {
        PersentationView persentationView = new PersentationView(context, null);
        SlideView slideShow = navitator.navigateToSlide(persentationView.getGraphicsContext(), position);
        int widthTemp = slideShow.a();
        int heightTemp = slideShow.b();
        Log.i("test", "navigateTo: " + widthTemp + "   " + heightTemp);
        persentationView.setContentView(slideShow);
        persentationView.notifyScale((float) width / widthTemp / 4);
        Bitmap bitmap = Bitmap.createBitmap(width, (int) ((float) heightTemp * width / widthTemp), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        try {
            Class statusBarManager = Class.forName("com.olivephone.office.powerpoint.view.SlideView");
            Method a = statusBarManager.getDeclaredMethod("a", Canvas.class);
            a.invoke(slideShow, canvas);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        canvas.save();
        ImageUtils.saveBitmap(bitmapFile, bitmap);
        return bitmap;
    }

    public void onSessionEnded() {
    }

    public void onSessionStarted() {
    }

}

