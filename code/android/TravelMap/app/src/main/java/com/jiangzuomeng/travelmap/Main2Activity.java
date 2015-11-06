package com.jiangzuomeng.travelmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView)findViewById(R.id.test_horizontalScrollView);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.test_meeting_linear_layout);

        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.mipmap.test2_show);
        imageView.setPadding(5, 5, 5, 5);
        linearLayout.addView(imageView);

        imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.mipmap.test1_show);
        imageView.setPadding(5, 5, 5, 5);
        linearLayout.addView(imageView);

        imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.mipmap.test4_show);
        imageView.setPadding(5,5,5,5);
        linearLayout.addView(imageView);

    }
}
