package com.jiangzuomeng.module;

import android.content.ContentValues;

/**
 * Created by ekuri-PC on 2015/11/21.
 */
public class TravelItem implements ManLvTuSQLDataType {
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
    public ContentValues makeInsertSQLContentValues() {
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
}
