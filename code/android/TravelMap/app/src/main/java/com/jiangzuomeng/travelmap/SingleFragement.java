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
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.nio.channels.Pipe;
import java.util.List;


/**
 * Created by wilbert on 2015/10/27.
 */
public class SingleFragement extends Fragment implements View.OnClickListener{
    public static final String TYPE = "type";
    LocationClient locationClient = null;
    BaiduMap baiduMap = null;
    MapView mapView = null;
    boolean isFirstLoc = false;
    Marker marker = null;
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
        locationClient = new LocationClient(getActivity().getApplicationContext());
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                on_receiveLocation(bdLocation);
            }
        });
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);
        option.setCoorType("bd0911");
        option.setScanSpan(0);
        locationClient.setLocOption(option);
        locationClient.start();
        return view;
    }

    private void on_receiveLocation(BDLocation location) {
        if (location == null || mapView == null)
            return;
        //test
        StringBuffer sb = new StringBuffer(256);
        sb.append("time : ");
        sb.append(location.getTime());
        sb.append("\nerror code : ");
        sb.append(location.getLocType());
        sb.append("\nlatitude : ");
        sb.append(location.getLatitude());
        sb.append("\nlontitude : ");
        sb.append(location.getLongitude());
        sb.append("\nradius : ");
        sb.append(location.getRadius());
        if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
            sb.append("\nspeed : ");
            sb.append(location.getSpeed());// 单位：公里每小时
            sb.append("\nsatellite : ");
            sb.append(location.getSatelliteNumber());
            sb.append("\nheight : ");
            sb.append(location.getAltitude());// 单位：米
            sb.append("\ndirection : ");
            sb.append(location.getDirection());// 单位度
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            sb.append("\ndescribe : ");
            sb.append("gps定位成功");

        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            //运营商信息
            sb.append("\noperationers : ");
            sb.append(location.getOperators());
            sb.append("\ndescribe : ");
            sb.append("网络定位成功");
        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
            sb.append("\ndescribe : ");
            sb.append("离线定位成功，离线定位结果也是有效的");
        } else if (location.getLocType() == BDLocation.TypeServerError) {
            sb.append("\ndescribe : ");
            sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
            sb.append("\ndescribe : ");
            sb.append("网络不同导致定位失败，请检查网络是否通畅");
        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
            sb.append("\ndescribe : ");
            sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
        }
        sb.append("\nlocationdescribe : ");
        sb.append(location.getLocationDescribe());// 位置语义化信息
        List<Poi> list = location.getPoiList();// POI数据
        if (list != null) {
            sb.append("\npoilist size = : ");
            sb.append(list.size());
            for (Poi p : list) {
                sb.append("\npoi= : ");
                sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
            }
        }
/*        LatLng point = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_bookmark_black_24dp);
        MarkerOptions markerOptions = new MarkerOptions().icon(bitmap).position(point);
        marker = (Marker)baiduMap.addOverlay(markerOptions);

        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return on_meeting_click(marker);
            }
        });*/
        Log.v("wilbert", sb.toString());
        //test
        baiduMap.setMyLocationEnabled(true);
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        baiduMap.setMyLocationData(locData);
        if (isFirstLoc) {
            isFirstLoc = false;
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(u);
        }
    }

    private boolean on_meeting_click(Marker marker) {
        Log.v("wilbert", " meeting click");
        Button button = new Button(getActivity().getApplicationContext());
        button.setText("image here");
        LatLng point = marker.getPosition();
        InfoWindow infoWindow = new InfoWindow(button, point, -47);
        baiduMap.showInfoWindow(infoWindow);
        return true;
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
    @Override
    public void onDestroy() {
        mapView.onDestroy();
        mapView = null;
        super.onDestroy();
    }
    @Override
    public  void onDestroyView() {
        mapView = null;
        super.onDestroyView();
    }
}
