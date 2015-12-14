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

    public String addNewUser(User user) throws IOException {
        URL url = user.getAddUrl();
        return getStringFromUrl(url);
    }

    public String addNewTravel(Travel travel) throws IOException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(HTTP)
                .encodedAuthority(host)
                .appendPath(TRAVEL)
                .appendPath(ADD)
                .appendQueryParameter(USER_ID, Integer.toString(travel.userId))
                .appendQueryParameter(NAME, travel.name);
        URL url = new URL(builder.build().toString());
        return getStringFromUrl(url);
    }

    public String addNewTravelItem(TravelItem travelItem) throws IOException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(HTTP)
                .encodedAuthority(host)
                .appendPath(TRAVEL_ITEM)
                .appendPath(ADD)
                .appendQueryParameter(TRAVEL_ID, Integer.toString(travelItem.travelId))
                .appendQueryParameter(LABEL, travelItem.label)
                .appendQueryParameter(TIME, travelItem.time)
                .appendQueryParameter(LOCATION_LAT, Double.toString(travelItem.locationLat))
                .appendQueryParameter(LOCATION_LNG, Double.toString(travelItem.locationLng))
                .appendQueryParameter(LIKE, Integer.toString(travelItem.like))
                .appendQueryParameter(TEXT, travelItem.text)
                .appendQueryParameter(MEDIA, travelItem.media);
        URL url = new URL(builder.build().toString());
        return getStringFromUrl(url);
    }

    public String addNewComment(Comment comment) throws IOException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(HTTP)
                .encodedAuthority(host)
                .appendPath(COMMENT).appendPath(ADD)
                .appendQueryParameter(TRAVEL_ITEM_ID, Integer.toString(comment.travelItemId))
                .appendQueryParameter(USER_ID, Integer.toString(comment.userId))
                .appendQueryParameter(TIME, comment.time)
                .appendQueryParameter(TEXT, comment.text);
        URL url = new URL(builder.build().toString());
        return getStringFromUrl(url);
    }

    public String queryUserById(int userId) throws IOException {
        return "test";
        // TODO: 2015/12/11
        /*Uri.Builder builder = new Uri.Builder();
        builder.scheme(HTTP);
        URL url = new URL(builder.build().toString());
        return getStringFromUrl(url);*/
    }

    public String queryUserByUsername(String username) {
        // TODO: 2015/12/11
        return "test    ";
    }

    public String queryTravelByTravelId(int travelId) throws IOException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(HTTP)
                .encodedAuthority(host)
                .appendPath(TRAVEL).appendPath(QUERY)
                .appendQueryParameter(ID, Integer.toString(travelId));
        URL url = new URL(builder.build().toString());
        return getStringFromUrl(url);
    }

    public String queryTravelItemByTravelItemId(int travelItemId) throws IOException {
        Uri.Builder builder = Uri.parse(host).buildUpon();
        builder.scheme(HTTP)
                .encodedAuthority(host)
                .appendPath(TRAVEL_ITEM).appendPath(QUERY)
                .appendQueryParameter(ID, Integer.toString(travelItemId));
        URL url = new URL(builder.build().toString());
        return getStringFromUrl(url);
    }

    public String queryCommentByCommentId(int commentId) throws IOException {
        Uri.Builder builder = Uri.parse(host).buildUpon();
        builder.scheme(HTTP)
                .encodedAuthority(host)
                .appendPath(COMMENT).appendPath(QUERY)
                .appendQueryParameter(ID, Integer.toString(commentId));
        URL url = new URL(builder.build().toString());
        return getStringFromUrl(url);
    }

    /*public List<Integer> queryTravelIdListByUserId(int userId) {
        // TODO: 2015/12/11

    }

    public List<Integer> queryTravelItemIdListByTravelId(int travelId) {
        // TODO: 2015/12/11

    }

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
    public String removeUserByUserId(int userId) throws IOException {
        Uri.Builder builder = Uri.parse(host).buildUpon();
        builder.scheme(HTTP)
                .encodedAuthority(host)
                .appendPath(USER).appendPath(REMOVE)
                .appendQueryParameter(ID, Integer.toString(userId));
        URL url = new URL(builder.build().toString());
        return getStringFromUrl(url);
    }

    public String removeTravelByTravelId(int travelId) throws IOException {
        Uri.Builder builder = Uri.parse(host).buildUpon();
        builder.scheme(HTTP)
                .encodedAuthority(host)
                .appendPath(TRAVEL).appendPath(REMOVE)
                .appendQueryParameter(ID, Integer.toString(travelId));
        URL url = new URL(builder.build().toString());
        return getStringFromUrl(url);
    }

    public String removeTravelItemByTravelItemId(int travelItemId) throws IOException {
        Uri.Builder builder = Uri.parse(host).buildUpon();
        builder.scheme(HTTP)
                .encodedAuthority(host)
                .appendPath(TRAVEL_ITEM).appendPath(REMOVE)
                .appendQueryParameter(ID, Integer.toString(travelItemId));
        URL url = new URL(builder.build().toString());
        return getStringFromUrl(url);
    }

    public String removeCommentByCommentId(int commentId) throws IOException {
        Uri.Builder builder = Uri.parse(host).buildUpon();
        builder.scheme(HTTP)
                .encodedAuthority(host)
                .appendPath(COMMENT).appendPath(REMOVE)
                .appendQueryParameter(ID, Integer.toString(commentId));
        URL url = new URL(builder.build().toString());
        return getStringFromUrl(url);
    }

    public String updateComment(Comment comment) throws IOException {
        Uri.Builder builder = Uri.parse(host).buildUpon();
        builder.scheme(HTTP)
                .encodedAuthority(host)
                .appendPath(COMMENT).appendPath(UPDATE)
                .appendQueryParameter(ID, Integer.toString(comment.id))
                .appendQueryParameter(TRAVEL_ITEM_ID, Integer.toString(comment.travelItemId))
                .appendQueryParameter(USER_ID, Integer.toString(comment.userId))
                .appendQueryParameter(TIME, comment.time)
                .appendQueryParameter(TEXT, comment.text);
        URL url = new URL(builder.build().toString());
        return getStringFromUrl(url);
    }

    public String updateTravelItem(TravelItem travelItem) throws IOException {
        Uri.Builder builder = Uri.parse(host).buildUpon();
        builder.scheme(HTTP)
                .encodedAuthority(host)
                .appendPath(TRAVEL_ITEM).appendPath(UPDATE)
                .appendQueryParameter(ID, Integer.toString(travelItem.id))
                .appendQueryParameter(TRAVEL_ID, Integer.toString(travelItem.travelId))
                .appendQueryParameter(LABEL, travelItem.label)
                .appendQueryParameter(TIME, travelItem.time)
                .appendQueryParameter(LOCATION_LAT, Double.toString(travelItem.locationLat))
                .appendQueryParameter(LOCATION_LNG, Double.toString(travelItem.locationLng))
                .appendQueryParameter(LIKE, Integer.toString(travelItem.like))
                .appendQueryParameter(TEXT, travelItem.text)
                .appendQueryParameter(MEDIA, travelItem.media);
        URL url = new URL(builder.build().toString());
        return getStringFromUrl(url);
    }

    public String updateTravel(Travel travel) throws IOException {
        Uri.Builder builder = Uri.parse(host).buildUpon();
        builder.scheme(HTTP)
                .encodedAuthority(host)
                .appendPath(TRAVEL).appendPath(UPDATE)
                .appendQueryParameter(ID, Integer.toString(travel.id))
                .appendQueryParameter(USER_ID, Integer.toString(travel.userId))
                .appendQueryParameter(NAME, travel.name);
        URL url = new URL(builder.build().toString());
        return getStringFromUrl(url);
    }

    public String updateUser(User user) throws IOException {
        Uri.Builder builder = Uri.parse(host).buildUpon();
        builder.scheme(HTTP)
                .encodedAuthority(host)
                .appendPath(USER).appendPath(UPDATE)
                .appendQueryParameter(ID, Integer.toString(user.id))
                .appendQueryParameter(USERNAME, user.username)
                .appendQueryParameter(PASSWORD, user.password);
        URL url = new URL(builder.build().toString());
        return getStringFromUrl(url);
    }


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