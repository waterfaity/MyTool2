package com.waterfairy.tool2.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.waterfairy.tool2.R;
import com.waterfairy.utils.AssetsUtils;
import com.waterfairy.utils.ImageUtils;
import com.waterfairy.widget.FiveView;
import com.waterfairy.widget.colorSeclect.ColorSelectView;
import com.waterfairy.widget.colorSeclect.ColorTransitionView;
import com.waterfairy.widget.colorSeclect.OnColorSelectListener;

import java.io.IOException;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        final ColorSelectView colorSelectView = (ColorSelectView) findViewById(R.id.color_select_view);
//        try {
//            Bitmap bitmap = BitmapFactory.decodeStream(AssetsUtils.getIS(this, "circle.png"));
//            Bitmap matrix = ImageUtils.matrix(bitmap, -1, 1);
//            ImageUtils.saveBitmap("/sdcard/jjj.png", matrix, Bitmap.CompressFormat.PNG, 100);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Bitmap bitmap = Bitmap.createBitmap(153, 153, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
//        canvas.drawColor(Color.BLUE);
        Paint paint = new Paint();
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        canvas.drawCircle(76, 76, 75, paint);
        ImageUtils.saveBitmap("/sdcard/jjjj.png", bitmap, Bitmap.CompressFormat.PNG, 100);


        colorSelectView.setOnColorSelectListener(new OnColorSelectListener() {
            @Override
            public void onColorSelect(int color) {
                colorSelectView.setBackgroundColor(color);
            }
        });

    }


}
