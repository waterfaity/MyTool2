package com.waterfairy.tool2.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.waterfairy.tool2.R;
import com.waterfairy.tool2.widget.Histogram2View;
import com.waterfairy.tool2.widget.HistogramEntity;
import com.waterfairy.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class HistogramActivity extends AppCompatActivity implements Histogram2View.OnClickHistogramListener {

    private Histogram2View histogramView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histogram);
        initView();
    }

    private void initView() {
        histogramView2 = (Histogram2View) findViewById(R.id.histogram);
        List<HistogramEntity> histogramEntities = new ArrayList<>();
        HistogramEntity histogramEntity1 = new HistogramEntity(1, "12.15");
        HistogramEntity histogramEntity2 = new HistogramEntity(67, "12.16");
        HistogramEntity histogramEntity3 = new HistogramEntity(12, "12.17");
        HistogramEntity histogramEntity4 = new HistogramEntity(34, "12.18");
        HistogramEntity histogramEntity5 = new HistogramEntity(68, "12.19");
        HistogramEntity histogramEntity6 = new HistogramEntity(23, "12.20");
        HistogramEntity histogramEntity7 = new HistogramEntity(88, "12.21");
        histogramEntities.add(histogramEntity1);
        histogramEntities.add(histogramEntity2);
        histogramEntities.add(histogramEntity3);
        histogramEntities.add(histogramEntity4);
        histogramEntities.add(histogramEntity5);
        histogramEntities.add(histogramEntity6);
        histogramEntities.add(histogramEntity7);
        histogramView2.initColor(
                Color.parseColor("#2080cd"),
                Color.parseColor("#2080cd"),
                Color.parseColor("#7acd0a"));
//        histogramView2.initStrokeWidth();
        histogramView2.initShadowColor(Color.parseColor("#9ecd4e"), Color.parseColor("#009ecd4e"));
        histogramView2.initTextSize(30);
        histogramView2.initBgColor(Color.WHITE);
        histogramView2.initTitle("时间", "本数");
        histogramView2.initData(histogramEntities);

        histogramView2.setOnClickHistogramListener(this);

    }

    @Override
    public void onClickHistogram(int pos, int x, int tag) {
        ToastUtils.show(pos + "--" + x);
    }
}
