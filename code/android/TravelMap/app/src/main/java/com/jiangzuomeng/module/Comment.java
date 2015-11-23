package com.jiangzuomeng.module;

import android.content.ContentValues;

/**
 * Created by ekuri-PC on 2015/11/21.
 */
public class Comment implements ManLvTuSQLDataType {
    public static final String COMMENT_TABLE_NAME = "comment";
    public int id;
    public int userId;
    public int travelItemId;
    public String text;
    public String time;

    public Comment() {

    }

    @Override
    public ContentValues makeInsertSQLContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("userId", this.userId);
        contentValues.put("travelItemId", this.travelItemId);
        contentValues.put("text", this.text);
        contentValues.put("time", this.time);
        return contentValues;
    }

    public static String makeCreateTableSQLString() {
        return "CREATE TABLE IF NOT EXISTS " + COMMENT_TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER," +
                "text TEXT, time TEXT)";
    }

    public String makeQueryByUserIdSQLString() {
        return null;
    }

    public String makeQueryByCommentIdSQLString() {
        return null;
    }

    public String makeQueryByTravelItemIdSQLString() {
        return null;
    }
}
