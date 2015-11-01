package com.jiangzuomeng.travelmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.jiangzuomeng.Adapter.SingleTravelItemListViewAdapter;

public class SingleTravelActivity extends AppCompatActivity {

    private MapView mapView;
    private AMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_travel);

        // setup single travel activity list view
        ListView listView_drawer = (ListView)findViewById(R.id.SingleTravelMapListView);
        SingleTravelItemListViewAdapter singleTravelItemAdapter = new SingleTravelItemListViewAdapter(this);
        listView_drawer.setAdapter(singleTravelItemAdapter);

        // set map
        mapView = (MapView) findViewById(R.id.SingleTravelMapMapView);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
