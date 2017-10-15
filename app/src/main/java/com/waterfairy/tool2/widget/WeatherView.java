package com.waterfairy.tool2.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.waterfairy.widget.baseView.BaseSurfaceView;

/**
 * Created by water_fiay on 2017/7/4.
 * 995637517@qq.com
 */

public class WeatherView extends BaseSurfaceView {
    private Path linePathUp;
    private Path linePathDown;


    public WeatherView(Context context) {
        super(context);
    }

    public WeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        Drawable drawable= ContextCompat.getDrawable(getContext(),)
    }

    @Override
    protected void beforeDraw() {

    }

    @Override
    protected void startDraw() {
        setClockNo();
    }

    @Override
    protected void drawFinishView(Canvas canvas) {

    }
}
