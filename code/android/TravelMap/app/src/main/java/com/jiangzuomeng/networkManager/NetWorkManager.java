package com.jiangzuomeng.networkManager;

import android.net.Uri;
import android.util.Log;

import com.jiangzuomeng.modals.Comment;
import com.jiangzuomeng.modals.Travel;
import com.jiangzuomeng.modals.TravelItem;
import com.jiangzuomeng.modals.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wilbert on 2015/11/22.
 */
public class NetWorkManager {
    String host = "192.168.191.1:3000";
    public static final String HTTP = "http";
    public static final String USER = "user";
    public static final String REGISTER = "register";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String TRAVEL = "travel";
    public static final String ADD = "add";
    public static final String USER_ID = "userId";
    public static final String NAME = "name";
    public static final String COMMENT = "comment";
    public static final String TRAVEL_ITEM_ID = "travelItemId";
    public static final String TRAVEL_ITEM = "travelItem";
    public static final String TRAVEL_ID = "travelId";
    public static final String LABEL = "label";
    public static final String TIME = "time";
    public static final String LOCATION_LNG = "locationLng";
    public static final String LOCATION_LAT = "locationLat";
    public static final String LIKE = "like";
    public static final String TEXT = "text";
    public static final String MEDIA = "media";
    public static final String QUERY = "query";
    public static final String ID = "id";
    public static final String REMOVE = "remove";
    public static final String UPDATE = "update";


    /*
    public List<Integer> queryCommentIdListByTravelItemId(int travelItemId) {
        // TODO: 2015/12/11

    }

    public int queryLikeNumByTravelItemId(int TravelItemid) {
        // TODO: 2015/12/11
    }

    public List<Travel> queryTravelListByUserId(int userId) {
        // TODO: 2015/12/11
    }

    public List<TravelItem> queryTravelItemListByTravelId(int travelId) {
        // TODO: 2015/12/11
    }

    public List<Comment> queryCommentListByTravelItemId(int travelItemId) {
        // TODO: 2015/12/11
    }
*/



    public String getStringFromUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String inputLine = null;
        while ((inputLine = bufferedReader.readLine()) != null) {
            stringBuilder.append(inputLine);
        }
        return  stringBuilder.toString();
    }

    public String getDataFromUrl(URL url) throws IOException {
        Log.v("ekuri", "openning uri:" + url.toString());
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String inputLine = null;
        while ((inputLine = bufferedReader.readLine()) != null) {
            stringBuilder.append(inputLine);
        }
        return  stringBuilder.toString();
    }
}
