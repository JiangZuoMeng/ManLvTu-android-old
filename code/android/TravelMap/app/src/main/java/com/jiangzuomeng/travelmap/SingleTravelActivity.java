package com.jiangzuomeng.travelmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.jiangzuomeng.Adapter.SingleTravelItemListViewAdapter;

public class SingleTravelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_travel);
        ListView listView_drawer = (ListView)findViewById(R.id.SingleTravelMapListView);
        SingleTravelItemListViewAdapter singleTravelItemAdapter = new SingleTravelItemListViewAdapter(this);
        listView_drawer.setAdapter(singleTravelItemAdapter);
    }
}
