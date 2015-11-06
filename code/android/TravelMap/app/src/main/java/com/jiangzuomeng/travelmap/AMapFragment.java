package com.jiangzuomeng.travelmap;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.Projection;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.jiangzuomeng.Adapter.ShowPictureAdapter;

/**
 * Created by wilbert on 2015/10/30.
 */
public class AMapFragment extends Fragment implements LocationSource, AMapLocationListener, AMap.OnMarkerClickListener,
        AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter, AMap.OnMapClickListener{
    private MapView mapView;
    private AMap aMap;
    private OnLocationChangedListener mlistener;
    private LocationManagerProxy locationManagerProxy;
    private int []imageIds;
    private LayoutInflater layoutInflater;
    private Marker current_marker = null;
    private static String Maptype;
    private LinearLayout linearLayout;
    public  static void setType(String t) {
        Maptype = t;

    }
    private ShowPictureAdapter showPictureAdapter;
    HorizontalScrollView horizontalScrollView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amap, container, false);
        mapView = (MapView)view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        layoutInflater = LayoutInflater.from(getActivity());
        setUpMap();
        initData();
        showPictureAdapter = new ShowPictureAdapter(layoutInflater);
        return view;
    }

    private void initData() {
        imageIds = new int[]{
            R.mipmap.test1, R.mipmap.test2, R.mipmap.test3,
                R.mipmap.test4
        };
    }

    private void setUpMap() {
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.mipmap.location_marker));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(0.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.setOnMarkerClickListener(this);
        aMap.setOnInfoWindowClickListener(this);
        aMap.setInfoWindowAdapter(this);
        aMap.setOnMapClickListener(this);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示

        aMap.getUiSettings().setZoomGesturesEnabled(false);
        aMap.getUiSettings().setScrollGesturesEnabled(false);
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // aMap.setMyLocationType()
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
/*            current_marker = aMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_pin_drop_black_24dp))
                    .title("my location"));*/
            current_marker = aMap.addMarker(new MarkerOptions().position(latLng).title("my location"));
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
        if (marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
            return  true;
        }
        Log.v("wilbert", "popup show");
        View popView = layoutInflater.inflate(R.layout.popup_window_meeting, null);
        linearLayout = (LinearLayout)popView.findViewById(R.id.meeting_linear_layout);
        AddMarksPicturesHere();
        PopupWindow popupWindow = new PopupWindow(popView, ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        Projection projection = aMap.getProjection();
        Point point = projection.toScreenLocation(marker.getPosition());
        popupWindow.showAtLocation(mapView, Gravity.CENTER, 0, 0);
        return true;
        //return false;
    }
    //点击marker之后出现的
    @Override
    public View getInfoWindow(Marker marker) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.popup_window_meeting, null);
        Log.v("wilbert", "return view");
        horizontalScrollView = (HorizontalScrollView)view.findViewById(R.id.horizontalScrollView);
        linearLayout = (LinearLayout)view.findViewById(R.id.meeting_linear_layout);

        AddMarksPicturesHere();

        return view;
    }

    private void AddMarksPicturesHere() {
        ImageView imageView = new ImageView(getActivity().getApplicationContext());
        imageView.setImageResource(R.mipmap.test2_show);
        imageView.setPadding(5, 5, 5, 5);
        linearLayout.addView(imageView);

        imageView = new ImageView(getActivity().getApplicationContext());
        imageView.setImageResource(R.mipmap.test1_show);
        imageView.setPadding(5, 5, 5, 5);
        linearLayout.addView(imageView);

        imageView = new ImageView(getActivity().getApplicationContext());
        imageView.setImageResource(R.mipmap.test4_show);
        imageView.setPadding(5, 5, 5, 5);
        linearLayout.addView(imageView);

        final int width = imageView.getWidth();
        final int height = imageView.getHeight();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("wilbert", "onclick");
            }
        });

        imageView = new ImageView(getActivity().getApplicationContext());
        imageView.setImageResource(R.mipmap.test4_show);
        imageView.setPadding(5, 5, 5, 5);
        linearLayout.addView(imageView);
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.v("wilbert", marker.isInfoWindowShown()+" ");
        if (marker.isInfoWindowShown())
            marker.hideInfoWindow();
        Log.v("wilbert", "oninfowindowclick");
    }

    @Override
    public void onMapClick(LatLng latLng) {
        //if (current_marker != null && current_marker.isInfoWindowShown())
            current_marker.hideInfoWindow();
        Log.v("wilbert", "map click " + current_marker.isInfoWindowShown());
        //horizontalScrollView.fullScroll(View.FOCUS_RIGHT);
    }

}
