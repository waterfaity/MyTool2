package com.waterfairy.tool2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.waterfairy.tool2.R;
import com.waterfairy.tool2.widget.Histogram2View;
import com.waterfairy.tool2.widget.HistogramEntity;

import java.util.ArrayList;
import java.util.List;

public class HistogramActivity extends AppCompatActivity {

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
        HistogramEntity histogramEntity1 = new HistogramEntity(1, "4.15");
        HistogramEntity histogramEntity2 = new HistogramEntity(8, "4.16");
        HistogramEntity histogramEntity3 = new HistogramEntity(3, "4.17");
        HistogramEntity histogramEntity4 = new HistogramEntity(12, "4.18");
        HistogramEntity histogramEntity5 = new HistogramEntity(15, "4.19");
        HistogramEntity histogramEntity6 = new HistogramEntity(6, "4.20");
        HistogramEntity histogramEntity7 = new HistogramEntity(12, "4.21");
        histogramEntities.add(histogramEntity1);
        histogramEntities.add(histogramEntity2);
        histogramEntities.add(histogramEntity3);
        histogramEntities.add(histogramEntity4);
        histogramEntities.add(histogramEntity5);
        histogramEntities.add(histogramEntity6);
        histogramEntities.add(histogramEntity7);
        histogramView2.initTextSize(30);
        histogramView2.initTitle("时间", "本数");
        histogramView2.initData(histogramEntities);

    }
}
