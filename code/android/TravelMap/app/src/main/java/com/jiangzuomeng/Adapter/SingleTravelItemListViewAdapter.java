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
 * Created by ekuri-PC on 2015/11/1.
 */
public class SingleTravelItemListViewAdapter extends BaseAdapter {
    List<Map<String, Object>> data;
    public final class content {
        public ImageView image;
        public TextView title;
    }
    public Context context;
    public SingleTravelItemListViewAdapter(Context c) {
        data = new ArrayList<>();
        context = c;
        initData();
    }

    private void initData() {
        Map<String, Object> map = new HashMap<>();
        map.put("image", R.mipmap.test2_show);
        map.put("title", "长隆");
        data.add(map);
        map = new HashMap<>();
        map.put("image",R.mipmap.test3_show);
        map.put("title", "XX两日游");
        data.add(map);
        map = new HashMap<>();
        map.put("image", R.mipmap.test4_show);
        map.put("title", "广州");
        data.add(map);
        map = new HashMap<>();
        map.put("image",R.mipmap.test1_show);
        map.put("title", "XX");
        data.add(map);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.single_travel_item_layout, null);
            z.image = (ImageView)convertView.findViewById(R.id.SingleTravelItemImageView);
            z.title = (TextView)convertView.findViewById(R.id.SingleTravelItemTextView);
            convertView.setTag(z);
        } else {
            z = (content)convertView.getTag();
        }
        z.image.setImageResource((Integer) data.get(position).get("image"));
        z.title.setText((String) data.get(position).get("title"));
        return convertView;
    }
}