package com.jiangzuomeng.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiangzuomeng.dataManager.DataManager;
import com.jiangzuomeng.module.Travel;
import com.jiangzuomeng.travelmap.R;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.DeflaterInputStream;

/**
 * Created by wilbert on 2015/10/31.
 */
public class DrawerAdapter  extends BaseAdapter{
    public static final  String TITLE = "title";
    public static final  String IMAGE = "image";
    List<Map<String, Object>> bs;
    List<Uri> uriList = new ArrayList<>();
    List<String> nameList = new ArrayList<>();
    List<Bitmap> bitmapList = new ArrayList<>();
    public final class content {
    public ImageView image;
    public TextView title;
    }
    public Context context;
    public DrawerAdapter(List<Uri> uriList, List<String> nameList, Context c) {
        bs = new ArrayList<>();
        context = c;

        this.uriList = uriList;
        this.nameList = nameList;
        initData();
    }

    private void initData() {
        //// TODO: 2015/11/21

        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < nameList.size(); i++) {
            map = new HashMap<>();
            map.put(TITLE, nameList.get(i));
            map.put(IMAGE, uriList.get(i));
            bs.add(map);
        }

        for (int i = 0; i < bs.size(); i++) {
            Uri uri = (Uri)bs.get(i).get(IMAGE);
            if (uri != null) {
//        uri = Uri.parse("file:///storage/sdcard0/Pictures/TravelMap/IMG_20151124_011820.jpg");
                try {
                    InputStream inputStream = context.getContentResolver().openInputStream(uri);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                    float imageHeight = options.outHeight;
                    Resources resources = context.getResources();
                    DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                    float showHeight = 150 * (displayMetrics.densityDpi / 160f);
                    int sampleSize = (int) (imageHeight / showHeight);
                    options.inJustDecodeBounds = false;
                    options.inSampleSize = sampleSize;
                    inputStream = context.getContentResolver().openInputStream(uri);
                    bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                    bitmapList.add(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }} else {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_mood_black_24dp);
                    bitmapList.add(bitmap);
            }
        }
    }

    @Override
    public int getCount() {
        return bs.size();
    }

    @Override
    public Object getItem(int position) {
        return bs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        content z = null;
        if (convertView == null) {
            z = new content();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_single_drawer, null);
            z.image = (ImageView)convertView.findViewById(R.id.drawer_image);
            z.title = (TextView)convertView.findViewById(R.id.drawer_text);
            convertView.setTag(z);
        } else {
            z = (content)convertView.getTag();
        }
        z.image.setScaleType(ImageView.ScaleType.CENTER);
        Bitmap bitmap = bitmapList.get(position);
        z.image.setImageBitmap(bitmap);
        z.title.setText((String) bs.get(position).get(TITLE));
        return convertView;
    }
}
