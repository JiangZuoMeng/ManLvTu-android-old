package com.jiangzuomeng.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ekuri-PC on 2015/11/21.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ManLvTu.db";
    public static final int DATABASE_VERSION = 1;
    public static final String USER_TABLE_NAME = "user";
    public static final String TRAVEL_TABLE_NAME = "travel";
    public static final String TRAVEL_ITEM_TABLE_NAME = "travelItem";
    public static final String COMMENT_TABLE_NAME = "comment";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // user table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, firstTravelId INTEGER)");

        // travel table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TRAVEL_TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, mainTravelItemId INTEGER, firstTravelItemId INTEGER," +
                "firstTravelId INTEGER, lastTravelId INTEGER, nextTravelId INTEGER)");

        // travel item table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TRAVEL_ITEM_TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, firstCommentId INTEGER, lastTravelItemId INTEGER," +
                "nextTravelItemId INTEGER, label TEXT, time TEXT, locationLat REAL, locationLng REAL, like INTEGER," +
                "text TEXT, image TEXT)");

        // comment table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + COMMENT_TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, lastCommentId INTEGER, nextCommentId INTEGER," +
                "fromWhosId INTEGER, text TEXT, time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
