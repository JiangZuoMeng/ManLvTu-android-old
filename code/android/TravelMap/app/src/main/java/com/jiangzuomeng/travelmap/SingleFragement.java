package com.jiangzuomeng.travelmap;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;


/**
 * Created by wilbert on 2015/10/27.
 */
public class SingleFragement extends Fragment implements View.OnClickListener{
    public static final String TYPE = "type";
    LocationClient locationClient = null;
    BaiduMap baiduMap = null;
    MapView mapView = null;
    boolean isFirstLoc = false;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //case: R.id.button
        }
    }

    public interface ButtonOnclick {
        public void test_onclick();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        locationClient = new LocationClient(activity.getApplicationContext());
    }
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_single, container, false);
        Bundle args = getArguments();

        isFirstLoc = true;
        mapView = (MapView)view.findViewById(R.id.mapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        locationClient = new LocationClient(getActivity().getApplicationContext());
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                            if (bdLocation == null || mapView == null)
                return;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
                if (isFirstLoc) {
                    isFirstLoc = false;
                    LatLng ll = new LatLng(bdLocation.getLatitude(),
                            bdLocation.getLongitude());
                    MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                    baiduMap.animateMapStatus(u);
                }
            }
        });
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd0911");
        option.setScanSpan(1000);
        option.setAddrType("all");
        locationClient.setLocOption(option);
        locationClient.start();
        return view;
    }

    @Override
    public void onPause() {
        //mapView.setVisibility(View.INVISIBLE);
        mapView.onPause();
        super.onPause();
    }
    @Override
    public  void onResume() {
        //mapView.setVisibility(View.VISIBLE);
        mapView.onResume();
        super.onResume();
    }
}
