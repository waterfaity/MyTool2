package com.waterfairy.document.ppt;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
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
import static android.R.attr.theme;

/**
 * Created by water_fairy on 2017/7/10.
 * 995637517@qq.com
 */

public class PPTViewReader extends ScrollView implements DocumentSessionStatusListener {
    private String baseCacheImgPath;
    private DocumentSession session;
    private String path;
    private Context context;
    private Activity activity;
    private SlideShowNavigator navitator;
    private LinearLayout contentView;
    private int width, screenWidth;
    private long fileSize;
    private float scale = 1;
    public float limit = 900f;
    private OnPPTLoadListener onPPTLoadingListener;

    public PPTViewReader(Context context) {
        this(context, null);
    }

    public PPTViewReader(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        contentView = new LinearLayout(context, attr);
        addView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        contentView.setLayoutParams(layoutParams);
        contentView.setGravity(Gravity.CENTER_HORIZONTAL);
        contentView.setOrientation(LinearLayout.VERTICAL);
        baseCacheImgPath = context.getExternalCacheDir().getPath() + "/ppt/";
    }

    public void setWidth(int width) {
        screenWidth = width;
        if (width > limit) {
            width = (int) limit;
        }
        this.width = width;
    }

    public void setPath(String path) {
        this.path = path;
        baseCacheImgPath = baseCacheImgPath + "/" + MD5Utils.getMD5Code(path);
    }

    public void loadPPT(Activity activity, String path) {
        this.setPath(path);
        File file = new File(path);
        if (file.exists()) {
            fileSize = file.length();
            this.loadPPT(activity);
        } else {
            onPPTLoadingListener.onLoadError();
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
        getAndSaveBitmap();
//
    }

    private void getAndSaveBitmap() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                navitator = new SlideShowNavigator(session.getPPTContext());
                int firstSlideNumber = navitator.getFirstSlideNumber();
                int slideCount = navitator.getSlideCount();
                for (int i = 0; i < slideCount; i++) {
                    if (activity == null) {
                        return;
                    }
                    getBitmap(firstSlideNumber + i);
                    onPPTLoadingListener.onLoading("加载中" + (i + 1) + "/" + slideCount);
                }
                showView();
            }

            private void getBitmap(int pos) {
                String bitmapFile = baseCacheImgPath + "/" + MD5Utils.getMD5Code(path + fileSize + pos);
                if (!new File(bitmapFile).exists()) {
                    createBitmap(pos, bitmapFile);
                }
            }
        }.run();
    }

    private void showView() {
        if (this.activity == null) return;
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                navitator = new SlideShowNavigator(session.getPPTContext());
                int firstSlideNumber = navitator.getFirstSlideNumber();
                int slideCount = navitator.getSlideCount();
                for (int i = 0; i < slideCount; i++) {
                    addChildView(firstSlideNumber + i);
                }
                onPPTLoadingListener.onLoadSuccess();
            }
        });
    }

    private void addChildView(int position) {
        String bitmapFile = baseCacheImgPath + "/" + MD5Utils.getMD5Code(path + fileSize + position);
        Bitmap showBitmap = null;
        if (!new File(bitmapFile).exists()) {
            showBitmap = createBitmap(position, bitmapFile);
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            showBitmap = BitmapFactory.decodeFile(bitmapFile, options);
        }
        if (showBitmap != null) {
            LinearLayout putImgViewLin = new LinearLayout(context);
            contentView.addView(putImgViewLin);
            int width = showBitmap.getWidth();
            int height = showBitmap.getHeight();
            scale = screenWidth == 0 ? 1 : screenWidth / (float) width;
            LinearLayout.LayoutParams putImgViewLinLayoutParams = (LinearLayout.LayoutParams) putImgViewLin.getLayoutParams();
            putImgViewLinLayoutParams.width = (int) (width * scale);
            putImgViewLinLayoutParams.height = (int) (height * scale);
            putImgViewLinLayoutParams.gravity = Gravity.CENTER;
//            putImgViewLinLayoutParams.topMargin = 5;
//            putImgViewLinLayoutParams.bottomMargin = 5;
            putImgViewLin.setLayoutParams(putImgViewLinLayoutParams);
            ImageView imageView = new ImageView(context);
            putImgViewLin.addView(imageView);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams.leftMargin = (int) ((width * scale - width) / 2);
            layoutParams.topMargin = (int) ((height * scale - height) / 2);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleX(scale);
            imageView.setScaleY(scale);
            imageView.setImageBitmap(showBitmap);
        }
    }

    private Bitmap createBitmap(int position, final String bitmapFile) {
        PersentationView persentationView = new PersentationView(context, null);
        SlideView slideShow = null;
        try {
            slideShow = navitator.navigateToSlide(persentationView.getGraphicsContext(), position);
        } catch (Exception e) {
            return null;
        }
        int widthTemp = slideShow.a();
        int heightTemp = slideShow.b();
        Log.i("test", "navigateTo: " + widthTemp + "   " + heightTemp);
        persentationView.setContentView(slideShow);
        if (width > widthTemp) {
            width = widthTemp;
        }
        persentationView.notifyScale((float) width / widthTemp / 4);
        final Bitmap bitmap = Bitmap.createBitmap(width, (int) ((float) heightTemp * width / widthTemp), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        try {
            Class statusBarManager = Class.forName("com.olivephone.office.powerpoint.view.SlideView");
            Method a = statusBarManager.getDeclaredMethod("a", Canvas.class);
            a.invoke(slideShow, canvas);
            canvas.save();
            ImageUtils.saveBitmap(bitmapFile, bitmap);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public void onSessionEnded() {
    }

    public void onSessionStarted() {
    }

    public void setOnPPTLoadingListener(OnPPTLoadListener onPPTLoadingListener) {
        this.onPPTLoadingListener = onPPTLoadingListener;
    }

    public interface OnPPTLoadListener {

        void onLoadSuccess();

        void onLoadError();

        void onLoading(String content);
    }
}

