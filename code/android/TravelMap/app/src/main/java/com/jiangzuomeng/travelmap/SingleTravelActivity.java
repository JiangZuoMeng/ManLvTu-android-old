package com.jiangzuomeng.travelmap;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;
import com.jiangzuomeng.Adapter.SingleTravelItemListViewAdapter;
import com.jiangzuomeng.dataManager.DataManager;
import com.jiangzuomeng.module.TravelItem;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SingleTravelActivity
        extends AppCompatActivity
        implements AMap.OnMapClickListener, AdapterView.OnItemLongClickListener
        ,AdapterView.OnItemClickListener, AMap.OnMarkerDragListener {

    public static final String INTENT_TRAVEL_KEY = "travelId";
    private MapView mapView;
    private AMap aMap;
    private Polyline polyline;
    private ArrayList<Marker> markers = new ArrayList<>();
    List<TravelItem> travelItemList;
    private boolean isMapMovable = true;
    private ListView listView_drawer;
    PopupWindow popupWindow;
    private int listViewClickPosition = 0;
    private int currentTravelId;
    SingleTravelItemListViewAdapter singleTravelItemAdapter;


    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.SingleTravelActivityListViewPopupDeleteButton:
                    popupWindow.dismiss();

                    TravelItem targetTravelItem = travelItemList.get(listViewClickPosition);
                    DataManager.getInstance(getApplicationContext()).removeTravelItemByTravelItemId(
                            targetTravelItem.id
                    );
                    initData();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_travel);

        // setup single travel activity list view
        listView_drawer = (ListView)findViewById(R.id.SingleTravelMapListView);
        listView_drawer.setLongClickable(true);
        listView_drawer.setOnItemLongClickListener(this);
        listView_drawer.setOnItemClickListener(this);
        singleTravelItemAdapter = new SingleTravelItemListViewAdapter(this);
        currentTravelId = getIntent().getIntExtra(INTENT_TRAVEL_KEY, -1);
        /*currentTravelId = 0;*/
        initPopupWindow();

        // setup map
        mapView = (MapView) findViewById(R.id.SingleTravelMapMapView);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        setupMap();

        // setting action bar
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle("在生物岛");
        supportActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        initData();

        // tend to move camera to location of first item
        if (!travelItemList.isEmpty()) {
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(
                    new LatLng(travelItemList.get(0).locationLat, travelItemList.get(0).locationLng)));
        }
    }
    private void initData() {
        travelItemList = DataManager.getInstance(getApplicationContext())
                .queryTravelItemListByTravelId(currentTravelId);
        singleTravelItemAdapter.setup(travelItemList);
        listView_drawer.setAdapter(singleTravelItemAdapter);

        aMap.clear();
        markers.clear();

        for (TravelItem travelItem : travelItemList) {
            LatLng latLng= new LatLng(travelItem.locationLat, travelItem.locationLng);
            addMarker(new MarkerOptions().position(latLng).draggable(true));
        }
        linkMarkersOfMap();
    }



    private void setupMap() {
        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
        aMap.setOnMapClickListener(this);
        aMap.setOnMarkerDragListener(this);
    }
    private void initPopupWindow() {
        View popViewContent = getLayoutInflater().inflate(R.layout.popup_window_for_single_travel_view_list_item, null);

        Button editButton = (Button) popViewContent.findViewById(R.id.SingleTravelActivityListViewPopupEditButton);
        Button deleteButton = (Button) popViewContent.findViewById(R.id.SingleTravelActivityListViewPopupDeleteButton);

        editButton.setOnClickListener(clickListener);

        deleteButton.setOnClickListener(clickListener);

        popupWindow = new PopupWindow(popViewContent, ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
    }
    private void addMarker(MarkerOptions markerOptions) {
        markers.add(aMap.addMarker(markerOptions));
    }
    private void linkMarkersOfMap() {
        if (polyline != null) {
            polyline.remove();
        }

        PolylineOptions polylineOptions = new PolylineOptions()
                .color(getResources().getColor(R.color.single_travel_polyline_color));

        for (Marker marker: markers) {
            polylineOptions.add(marker.getPosition());
        }

        polyline = aMap.addPolyline(polylineOptions);
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
        //addMarker(new MarkerOptions().position(latLng));
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        listViewClickPosition = position;
        popupWindow.showAsDropDown(view, view.getWidth() / 2, -view.getHeight() / 2);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_add_new_position:
                /*addMarker(new MarkerOptions()
                                .position(aMap.getCameraPosition().target)
                                .draggable(true)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                );*/
                TravelItem travelItem = new TravelItem();
                travelItem.travelId = currentTravelId;
                travelItem.text = getResources().getString(R.string.single_travel_default_list_view_item_description);
                travelItem.locationLng = aMap.getCameraPosition().target.longitude;
                travelItem.locationLat = aMap.getCameraPosition().target.latitude;
                DataManager.getInstance(getApplicationContext()).addNewTravelItem(travelItem);
                initData();
                break;
            case R.id.action_lock_map:
                isMapMovable = !isMapMovable;
                aMap.getUiSettings().setScrollGesturesEnabled(isMapMovable);
                if (isMapMovable) {
                    item.setIcon(R.drawable.ic_lock_open_white_24dp);
                } else {
                    item.setIcon(R.drawable.ic_lock_outline_white_24dp);
                }
        }
        return true;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, AlbumViewerActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.single_travel_activity_actionbar_menu, menu);
        return true;
    }
    @Override
    public void onMarkerDragStart(Marker marker) {

    }
    @Override
    public void onMarkerDrag(Marker marker) {
        linkMarkersOfMap();
    }
    @Override
    public void onMarkerDragEnd(Marker marker) {
        TravelItem targetTravelItem = travelItemList.get(markers.indexOf(marker));

        targetTravelItem.locationLat = marker.getPosition().latitude;
        targetTravelItem.locationLng = marker.getPosition().longitude;

        DataManager.getInstance(getApplicationContext()).updateTravelItem(targetTravelItem);

        initData();
    }
}
