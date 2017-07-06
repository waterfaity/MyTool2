package com.waterfairy.document.ppt;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.olivephone.office.powerpoint.view.PersentationView;
import com.olivephone.office.powerpoint.view.SlideShowNavigator;
import com.olivephone.office.powerpoint.view.SlideView;

/**
 * Created by water_fairy on 2017/7/6.
 * 995637517@qq.com
 */

public class PPTAdapter extends BaseAdapter {
    private Context context;
    SlideShowNavigator slideShowNavigator;
    private int width;

    public PPTAdapter(SlideShowNavigator slideShowNavigator, Context context, int width) {
        this.context = context;
        this.slideShowNavigator = slideShowNavigator;
        this.width = width;
    }

    @Override
    public int getCount() {
        return slideShowNavigator.getSlideCount();
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
        PersentationView persentationView = new PersentationView(context, null);
        linearLayout.addView(persentationView);
        int firstSlideNumber = slideShowNavigator.getFirstSlideNumber();
        SlideView slideView = slideShowNavigator.navigateToSlide(persentationView.getGraphicsContext(), firstSlideNumber + position);
        int widthTemp = slideView.a();
        int heightTemp = slideView.b();
        persentationView.setContentView(slideView);
        persentationView.notifyScale((float) width / widthTemp / 4);
        ViewGroup.LayoutParams layoutParams = persentationView.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = (int) ((float) heightTemp * width / widthTemp);
        persentationView.setLayoutParams(layoutParams);
        return convertView;
    }
}
