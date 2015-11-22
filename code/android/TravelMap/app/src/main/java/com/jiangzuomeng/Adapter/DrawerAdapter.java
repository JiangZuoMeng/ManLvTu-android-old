package com.jiangzuomeng.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiangzuomeng.dataManager.DataManager;
import com.jiangzuomeng.module.Travel;
import com.jiangzuomeng.travelmap.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by wilbert on 2015/10/31.
 */
public class DrawerAdapter  extends BaseAdapter{
    public static final  String TITLE = "title";
    public static final  String IMAGE = "image";
    List<Map<String, Object>> bs;
    List<Uri> uriList = new ArrayList<>();
    List<String> nameList = new ArrayList<>();
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
            Uri uri = (Uri)bs.get(position).get(IMAGE);
            if (uri == null) {
                uri = Uri.parse("file:///storage/sdcard0/Pictures/TravelMap/IMG_20151122_213829.jpg");
                Log.v("wilbert", uri.toString());
                z.image.setImageURI(uri);
            }
        z.title.setText((String) bs.get(position).get(TITLE));
        return convertView;
    }
}
