package com.jiangzuomeng.modals;

import android.content.ContentValues;

/**
 * Created by ekuri-PC on 2015/11/21.
 */
public class Travel implements ManLvTuSQLDataType {
    public static final String TRAVEL_TABLE_NAME = "travel";
    public int id;
    public int userId;
    public String name;

    public Travel (int id, int userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
    }

    public Travel () {

    }


    @Override
    public ContentValues makeSQLContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("userId", this.userId);
        contentValues.put("name", this.name);
        return contentValues;
    }

    public static String makeCreateTableSQLString() {
        return "CREATE TABLE IF NOT EXISTS " + TRAVEL_TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, name TEXT)";
    }

    public String makeQueryByTrvelIdSQLString() {
        return null;
    }

    public String makeQueryByUserIdSQLString() {
        return null;
    }
}
