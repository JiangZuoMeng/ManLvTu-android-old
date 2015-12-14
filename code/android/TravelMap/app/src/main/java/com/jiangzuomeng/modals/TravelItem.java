package com.jiangzuomeng.modals;

import android.content.ContentValues;
import android.net.Uri;
import android.text.method.HideReturnsTransformationMethod;

import java.lang.annotation.Retention;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ekuri-PC on 2015/11/21.
 */
public class TravelItem extends ManLvTuNetworkDataType implements ManLvTuSQLDataType {
    public static final String TRAVEL_ITEM_TABLE_NAME = "travelItem";
    public int id;
    public int travelId;
    public String label;
    public String time;
    public double locationLng;
    public double locationLat;
    public int like;
    public String text;
    public String media;

    public TravelItem() {

    }

    @Override
    public ContentValues makeSQLContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("travelId", this.travelId);
        contentValues.put("label", this.label);
        contentValues.put("time", this.time);
        contentValues.put("locationLng", this.locationLng);
        contentValues.put("locationLat", this.locationLat);
        contentValues.put("like", this.like);
        contentValues.put("text", this.text);
        contentValues.put("media", this.media);
        return contentValues;
    }

    public static String makeCreateTableSQLString() {
        return "CREATE TABLE IF NOT EXISTS " + TRAVEL_ITEM_TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, travelId INTEGER," +
                "label TEXT, time TEXT, locationLat REAL, locationLng REAL, like INTEGER," +
                "text TEXT, media TEXT)";
    }

    public String makeQueryByTravelItemIdSQLString() {
        return null;
    }

    public String makeQueryByTravelIdSQLString() {
        return null;
    }

    @Override
    public URL getAddUrl() throws MalformedURLException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(StaticStrings.HTTP)
                .encodedAuthority(StaticStrings.host)
                .appendPath(StaticStrings.TRAVEL_ITEM)
                .appendPath(StaticStrings.ADD)
                .appendQueryParameter(StaticStrings.TRAVEL_ID, Integer.toString(travelId))
                .appendQueryParameter(StaticStrings.LABEL, label)
                .appendQueryParameter(StaticStrings.TIME, time)
                .appendQueryParameter(StaticStrings.LOCATION_LAT, Double.toString(locationLat))
                .appendQueryParameter(StaticStrings.LOCATION_LNG, Double.toString(locationLng))
                .appendQueryParameter(StaticStrings.LIKE, Integer.toString(like))
                .appendQueryParameter(StaticStrings.TEXT, text)
                .appendQueryParameter(StaticStrings.MEDIA, media);
        URL url = new URL(builder.build().toString());
        return url;
    }

    @Override
    public URL getQueryUrl() throws MalformedURLException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(StaticStrings.HTTP)
                .encodedAuthority(StaticStrings.host)
                .appendPath(StaticStrings.TRAVEL_ITEM).appendPath(StaticStrings.QUERY)
                .appendQueryParameter(StaticStrings.ID, Integer.toString(id));
        URL url = new URL(builder.build().toString());
        return url;
    }

    @Override
    public URL getUpdateUrl() throws MalformedURLException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(StaticStrings.HTTP)
                .encodedAuthority(StaticStrings.host)
                .appendPath(StaticStrings.TRAVEL_ITEM).appendPath(StaticStrings.UPDATE)
                .appendQueryParameter(StaticStrings.ID, Integer.toString(id))
                .appendQueryParameter(StaticStrings.TRAVEL_ID, Integer.toString(travelId))
                .appendQueryParameter(StaticStrings.LABEL, label)
                .appendQueryParameter(StaticStrings.TIME, time)
                .appendQueryParameter(StaticStrings.LOCATION_LAT, Double.toString(locationLat))
                .appendQueryParameter(StaticStrings.LOCATION_LNG, Double.toString(locationLng))
                .appendQueryParameter(StaticStrings.LIKE, Integer.toString(like))
                .appendQueryParameter(StaticStrings.TEXT, text)
                .appendQueryParameter(StaticStrings.MEDIA, media);
        URL url = new URL(builder.build().toString());
        return url;
    }

    @Override
    public URL getRemoveUrl() throws MalformedURLException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(StaticStrings.HTTP)
                .encodedAuthority(StaticStrings.host)
                .appendPath(StaticStrings.TRAVEL_ITEM).appendPath(StaticStrings.REMOVE)
                .appendQueryParameter(StaticStrings.ID, Integer.toString(id));
        URL url = new URL(builder.build().toString());
        return url;
    }

    @Override
    public URL getQueryAllUrl() throws MalformedURLException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(StaticStrings.HTTP)
                .encodedAuthority(StaticStrings.host)
                .appendPath(StaticStrings.TRAVEL_ITEM).appendPath(StaticStrings.QUERY_ALL)
                .appendQueryParameter(StaticStrings.TRAVEL_ID, Integer.toString(travelId));
        URL url = new URL(builder.build().toString());
        return url;
    }
}
