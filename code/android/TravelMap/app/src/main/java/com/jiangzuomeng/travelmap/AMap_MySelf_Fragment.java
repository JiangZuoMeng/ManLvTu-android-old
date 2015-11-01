package com.jiangzuomeng.travelmap;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wilbert on 2015/10/30.
 */
public class AMap_MySelf_Fragment extends Fragment implements LocationSource, AMapLocationListener, AMap.OnMarkerClickListener,
        GeocodeSearch.OnGeocodeSearchListener {
    private MapView mapView;
    private AMap aMap;
    private OnLocationChangedListener mlistener;
    private LocationManagerProxy locationManagerProxy;
    private int []imageIds;
    private LayoutInflater layoutInflater;
    private Marker current_marker = null;
    private GeocodeSearch geocoderSearch;
    private List<Marker> markers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amap, container, false);
        mapView = (MapView)view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        layoutInflater = LayoutInflater.from(getActivity());
        setUpMap();
        initData();
        return view;
    }

    private void initData() {
        markers = new ArrayList<>();
        imageIds = new int[]{
                R.mipmap.test1, R.mipmap.test2, R.mipmap.test3,
                R.mipmap.test4
        };
    }

    private void setUpMap() {
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_bookmark_black_24dp));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.setOnMarkerClickListener(this);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示

        aMap.getUiSettings().setZoomGesturesEnabled(false);
        aMap.getUiSettings().setScrollGesturesEnabled(false);
        aMap.setMyLocationEnabled(false);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(4);
        aMap.animateCamera(cameraUpdate);
        CameraUpdate update = CameraUpdateFactory.changeLatLng(new LatLng(30, 104));
        aMap.animateCamera(update);
        addMarkers();
        // aMap.setMyLocationType()
    }

    private void addMarkers() {
        geocoderSearch = new GeocodeSearch(getActivity());
        geocoderSearch.setOnGeocodeSearchListener(this);
        GeocodeQuery query = new GeocodeQuery("广州", "广州");// 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，
        geocoderSearch.getFromLocationNameAsyn(query);// 设置同步地理编码请求
        query = new GeocodeQuery("上海", "上海");
        geocoderSearch.getFromLocationNameAsyn(query);
        query = new GeocodeQuery("北京", "北京");
        geocoderSearch.getFromLocationNameAsyn(query);
        query = new GeocodeQuery("汕头", "汕头");
        geocoderSearch.getFromLocationNameAsyn(query);
        query = new GeocodeQuery("珠海", "珠海");
        geocoderSearch.getFromLocationNameAsyn(query);
        query = new GeocodeQuery("南京 ", "南京");
        geocoderSearch.getFromLocationNameAsyn(query);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mlistener = onLocationChangedListener;
        if (locationManagerProxy == null) {
            locationManagerProxy = LocationManagerProxy.getInstance(getActivity());
			/*
			 * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方式
			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
			 */
            locationManagerProxy.requestLocationData(
                    LocationProviderProxy.AMapNetwork, 2000, 10, this);
        }}

    @Override
    public void deactivate() {
        mlistener = null;
        if (locationManagerProxy != null) {
            locationManagerProxy.removeUpdates(this);
            locationManagerProxy.destroy();
        }
        locationManagerProxy = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mlistener != null && aMapLocation != null) {
            mlistener.onLocationChanged(aMapLocation);// 显示系统小蓝点
            //添加marker
            LatLng latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            current_marker = aMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bookmark_black_24dp))
                    .title("my location"));
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
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
        deactivate();
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
    public boolean onMarkerClick(Marker marker) {
        Log.v("wilbert", "marker clicked");
        //// TODO: 2015/11/1 跳转到每一项单独的旅程 返回值true表示默认操作(显示信息窗口)不显示
        return true;
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    //根据地理编码查询结果 回调函数
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        if (i == 0) {
            LatLonPoint point = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint();

            markers.add(aMap.addMarker(new MarkerOptions().position(new LatLng(point.getLatitude(), point.getLongitude()))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bookmark_black_24dp))
                    .title("my location")));
        }
    }
}
