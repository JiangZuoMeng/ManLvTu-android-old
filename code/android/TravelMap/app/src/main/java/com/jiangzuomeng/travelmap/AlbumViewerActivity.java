package com.jiangzuomeng.travelmap;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import uk.co.senab.photoview.PhotoView;


public class AlbumViewerActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private PhotoView[] imageViews;
    private ImageView[] indicatorViews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albumviewer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ViewGroup indicators = (ViewGroup) findViewById(R.id.album_indicators);

        final int IMAGE_COUNT = 3;

        indicatorViews = new ImageView[IMAGE_COUNT];
        for (int i = 0; i < IMAGE_COUNT; ++i) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
            imageView.setImageResource(R.drawable.page_indicator_unfocused);
            indicatorViews[i] = imageView;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            indicators.addView(imageView, layoutParams);
        }
        indicatorViews[0].setImageResource(R.drawable.page_indicator_focused);

        viewPager = (ViewPager) findViewById(R.id.album_pager);



        imageViews = new PhotoView[IMAGE_COUNT];
        int[] imgId = new int[] {R.drawable.photo0, R.drawable.photo1, R.drawable.photo2};
        for (int i = 0; i < IMAGE_COUNT; ++i) {
            PhotoView imageView = new PhotoView(this);
            imageView.setImageResource(imgId[i]);
            imageView.setBackgroundColor(Color.BLACK);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setAdjustViewBounds(true);
            imageViews[i] = imageView;
        }

        viewPager.setAdapter(new AlbumViewerAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int selected) {
                for (int i = 0; i < indicatorViews.length; ++i) {
                    if (i == selected) {
                        indicatorViews[i].setImageResource(R.drawable.page_indicator_focused);
                    } else {
                        indicatorViews[i].setImageResource(R.drawable.page_indicator_unfocused);
                    }
                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                /*NavUtils.navigateUpFromSameTask(this);
                return true;*/
        }
        return true;
//        return super.onOptionsItemSelected(item);
    }

    public class AlbumViewerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imageViews.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews[position]);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews[position], 0);
            return imageViews[position];
        }


    }

}

