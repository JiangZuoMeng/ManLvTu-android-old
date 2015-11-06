package com.jiangzuomeng.travelmap;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;
import com.jiangzuomeng.Adapter.SingleTravelItemListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SingleTravelActivity
        extends AppCompatActivity
        implements AMap.OnMapClickListener, AdapterView.OnItemLongClickListener {

    private MapView mapView;
    private AMap aMap;
    private Polyline polyline;
    private ArrayList markersLocation = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_travel);

        // setup single travel activity list view
        ListView listView_drawer = (ListView)findViewById(R.id.SingleTravelMapListView);
        listView_drawer.setLongClickable(true);
        listView_drawer.setOnItemLongClickListener(this);
        SingleTravelItemListViewAdapter singleTravelItemAdapter = new SingleTravelItemListViewAdapter(this);
        listView_drawer.setAdapter(singleTravelItemAdapter);

        // set map
        mapView = (MapView) findViewById(R.id.SingleTravelMapMapView);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        setupMap();
    }

    private void setupMap() {
        aMap.setOnMapClickListener(this);
    }

    private void addMarkerToMap(LatLng location) {
        markersLocation.add(location);
        aMap.addMarker(new MarkerOptions().position(location));
        linkMarkersOfMap();
    }

    private void linkMarkersOfMap() {
        if (polyline != null) {
            polyline.remove();
        }
        polyline = aMap.addPolyline(new PolylineOptions().addAll(markersLocation));
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

    @Override
    public void onMapClick(LatLng latLng) {
        Log.v("ekuri", latLng.toString());
        addMarkerToMap(latLng);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        View popViewContent = getLayoutInflater().inflate(R.layout.popup_window_for_single_travel_view_list_item, null);
        PopupWindow popupWindow = new PopupWindow(popViewContent, ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupWindow.showAsDropDown(view);
        Log.v("ekuri", "item long clicked");
        return false;
    }
}
