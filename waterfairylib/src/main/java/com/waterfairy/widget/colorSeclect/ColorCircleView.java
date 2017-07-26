package com.waterfairy.widget.colorSeclect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.waterfairy.utils.AlphaBgImgCreator;
import com.waterfairy.utils.ImageUtils;
import com.waterfairy.utils.NumberChange;
import com.waterfairy.utils.RGBColorBitmapCreator;
import com.waterfairy.widget.baseView.BaseSelfView;

/**
 * Created by water_fairy on 2017/7/26.
 * 995637517@qq.com
 */

public class ColorCircleView extends BaseSelfView implements View.OnTouchListener {
    private final String TAG = "ColorCircleView";
    private Paint sweepPaint, radialPaint;
    private Bitmap bitmapAlpha, bitmapColor;
    private Paint paintAlpha, paintDeep;


    private int cellWidth;


    public ColorCircleView(Context context) {
        this(context, null);
    }

    public ColorCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        cellWidth = (int) (context.getResources().getDisplayMetrics().density * 6);
        onInitDataOk();
    }

    @Override
    protected void beforeDraw() {
        SweepGradient sweepGradient = new SweepGradient(mWidth / 2, mHeight / 2,
                new int[]{Color.RED, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.RED}, null);
        sweepPaint = new Paint();
        sweepPaint.setAntiAlias(true);
        sweepPaint.setShader(sweepGradient);

        RadialGradient radialGradient = new RadialGradient(mWidth / 2, mHeight / 2, mHeight / 2, new int[]{Color.WHITE, Color.TRANSPARENT}, null, Shader.TileMode.CLAMP);
        radialPaint = new Paint();
        radialPaint.setAntiAlias(true);
        radialPaint.setShader(radialGradient);

        bitmapAlpha = new AlphaBgImgCreator(mWidth, mHeight, cellWidth).createCircleBg();

        paintAlpha = new Paint();
        paintAlpha.setAntiAlias(true);
        paintAlpha.setAlpha(0);
        paintDeep = new Paint();
        paintDeep.setAntiAlias(true);
        paintDeep.setColor(Color.TRANSPARENT);
//        bitmapColor = new RGBColorBitmapCreator(mWidth, mHeight).createBitmap();

    }

    @Override
    protected void drawOne(Canvas canvas) {
        canvas.drawBitmap(bitmapAlpha, 0, 0, null);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mHeight / 2, sweepPaint);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mHeight / 2, radialPaint);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mHeight / 2, paintAlpha);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
        }
        return false;
    }

    public void updateAlpha(float alphaRadio) {
        int alpha = (int) (255 * alphaRadio);
        sweepPaint.setAlpha(alpha);
        radialPaint.setAlpha(alpha);
//        paintAlpha.setAlpha();
        postInvalidate();
    }

    public void updateDeep(float deepRadio) {
        String s = Integer.toHexString(255 - (int) (255 * deepRadio));
        if (s.length() == 1) s += "0";
        Log.i(TAG, "updateDeep: " + s);
        paintAlpha.setColor(Color.parseColor("#" + s + "000000"));
        postInvalidate();
    }
}
