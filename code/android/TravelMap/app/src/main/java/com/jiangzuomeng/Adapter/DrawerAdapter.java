package com.jiangzuomeng.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiangzuomeng.travelmap.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by wilbert on 2015/10/31.
 */
public class DrawerAdapter  extends BaseAdapter{
    List<Map<String, Object>> bs;
    public final class zujian {
    public ImageView image;
    public TextView title;
    }
    public Context context;
    public DrawerAdapter(Context c) {
        bs = new ArrayList<>();
        context = c;
        initData();
    }

    private void initData() {
        Map<String, Object> map = new HashMap<>();
        map.put("image",R.mipmap.test2_show);
        map.put("title", "长隆");
        bs.add(map);
        map = new HashMap<>();
        map.put("image",R.mipmap.test3_show);
        map.put("title", "XX两日游");
        bs.add(map);
        map = new HashMap<>();
        map.put("image", R.mipmap.test4_show);
        map.put("title", "广州");
        bs.add(map);
        map = new HashMap<>();
        map.put("image",R.mipmap.test1_show);
        map.put("title", "XX");
        bs.add(map);
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
        zujian z = null;
        if (convertView == null) {
            z = new zujian();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_single_drawer, null);
            z.image = (ImageView)convertView.findViewById(R.id.drawer_image);
            z.title = (TextView)convertView.findViewById(R.id.drawer_text);
            convertView.setTag(z);
        } else {
            z = (zujian)convertView.getTag();
        }
        z.image.setImageResource((Integer)bs.get(position).get("image"));
        z.title.setText((String)bs.get(position).get("title"));
        return convertView;
    }
}
