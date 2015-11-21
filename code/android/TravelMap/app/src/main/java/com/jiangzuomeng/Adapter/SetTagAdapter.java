package com.jiangzuomeng.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.jiangzuomeng.travelmap.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wilbert on 2015/11/19.
 */
public class SetTagAdapter extends BaseAdapter {
    private List<String> strings = new ArrayList<>();
    private static HashMap<Integer, Boolean> isSelected;
    private Context context;
    private LayoutInflater layoutInflater = null;

    public SetTagAdapter(List<String> s, Context mContext) {
        strings = s;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
        isSelected = new HashMap<Integer, Boolean>();
        initData();
        Log.v("wilbert", strings.size()+"");
    }

    private void initData() {
        for (int i = 0; i < strings.size(); i++) {
            isSelected.put(i, false);
        }
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Object getItem(int position) {
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_single_tag, null);
            viewHolder.checkBox = (CheckBox)convertView.findViewById(R.id.tagcheckBox);
            convertView.setTag(viewHolder);
            Log.v("wilbert", "convertView == null");
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
            Log.v("wilbert", "convertView != null");
        }
        viewHolder.checkBox.setText(strings.get(position));
        viewHolder.checkBox.setChecked(isSelected.get(position));
        return convertView;
    }

    public HashMap<Integer, Boolean> getIsSelected() {
        return  isSelected;
    }
    public final class ViewHolder {
        public CheckBox checkBox;
    }
    public List<String> getStrings() {
        return strings;
    }
}
