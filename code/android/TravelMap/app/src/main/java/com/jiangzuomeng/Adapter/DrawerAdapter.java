package com.jiangzuomeng.Adapter;

import android.content.Context;
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

/**
 * Created by wilbert on 2015/10/31.
 */
public class DrawerAdapter  extends BaseAdapter{
    List<Map<String, Object>> bs;
    public final class content {
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
        map.put("image",R.drawable.test2_show);
        map.put("title", "长隆");
        bs.add(map);
        map = new HashMap<>();
        map.put("image",R.drawable.test3_show);
        map.put("title", "XX两日游");
        bs.add(map);
        map = new HashMap<>();
        map.put("image", R.drawable.test4_show);
        map.put("title", "广州");
        bs.add(map);
        map = new HashMap<>();
        map.put("image",R.drawable.test1_show);
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
        z.image.setImageResource((Integer)bs.get(position).get("image"));
        z.title.setText((String)bs.get(position).get("title"));
        return convertView;
    }
}
