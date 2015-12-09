package com.jiangzuomeng.module;

import android.content.ContentValues;
import android.util.JsonToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

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
    public ContentValues makeSQLContentValues() {
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

    public static Comment fromJson(String json, boolean withId) throws JSONException {
        Comment result = new Comment();
        JSONTokener parser = new JSONTokener(json);
        JSONObject jsonObject = (JSONObject) parser.nextValue();
        if (withId)
            result.id = jsonObject.getInt("id");
        result.travelItemId = jsonObject.getInt("travelItemId");
        result.userId = jsonObject.getInt("userId");
        result.text = jsonObject.getString("text");
        result.time = jsonObject.getString("time");
        return result;
    }

    public String toJson(boolean withId) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        if (withId)
            jsonObject.put("id", id);
        jsonObject.put("travelItemId", this.travelItemId);
        jsonObject.put("userId", this.userId);
        jsonObject.put("text", this.text);
        jsonObject.put("time", this.time);

        return jsonObject.toString();
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
