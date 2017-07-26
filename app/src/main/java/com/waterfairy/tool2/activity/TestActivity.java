package com.waterfairy.tool2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.waterfairy.tool2.R;
import com.waterfairy.widget.FiveView;
import com.waterfairy.widget.colorSeclect.ColorSelectView;
import com.waterfairy.widget.colorSeclect.ColorTransitionView;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ColorSelectView colorSelectView = (ColorSelectView) findViewById(R.id.color_select_view);


    }


}
