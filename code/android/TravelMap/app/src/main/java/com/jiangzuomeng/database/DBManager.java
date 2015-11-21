package com.jiangzuomeng.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiangzuomeng.module.Comment;
import com.jiangzuomeng.module.Travel;
import com.jiangzuomeng.module.TravelItem;
import com.jiangzuomeng.module.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wilbert on 2015/11/21.
 */
public class DBManager {
    private DBHelper databaseHelper;
    private SQLiteDatabase database;

    private User getUserFromCursor(Cursor cursor) {
        User user = new User();
        user.id = cursor.getInt(cursor.getColumnIndex("id"));
        user.username = cursor.getString(cursor.getColumnIndex("username"));
        user.password = cursor.getString(cursor.getColumnIndex("password"));
        user.firstTravelId = cursor.getInt(cursor.getColumnIndex("firstTravelId"));

        return user;
    }

    public DBManager(Context context) {
        databaseHelper = new DBHelper(context);

        // the following code must behind database helper instantiation
        database = databaseHelper.getWritableDatabase();
    }

    public void closeDB() {
        database.close();
    }

    public long addNewUser(User user) {
        ContentValues values = new ContentValues();
        values.put("username", user.username);
        values.put("password", user.password);
        values.put("firstTravelId", user.firstTravelId);
        return database.insert(DBHelper.USER_TABLE_NAME, null, values);
    }

    public User queryUserById(int userId) {

        String[] projection = {
                "id",
                "username",
                "password",
                "firstTravelId",
        };
        Cursor cursor = database.query(DBHelper.USER_TABLE_NAME, projection,
                "WHERE id = " + String.valueOf(userId), null, null, null, null);

        if (cursor.getCount() < 1)
            return null;

        cursor.moveToFirst();
        User user = getUserFromCursor(cursor);
        cursor.close();
        return user;
    }

    public User queryUserByUsername(String username) {
        String[] projection = {
                "id",
                "username",
                "password",
                "firstTravelId",
        };
        Cursor cursor = database.query(DBHelper.USER_TABLE_NAME, projection,
                "username = ?", new String[] {username}, null, null, null);

        if (cursor.getCount() < 1)
            return null;

        cursor.moveToFirst();
        User user = getUserFromCursor(cursor);
        cursor.close();
        return user;
    }

    public List<Integer> queryTravelListByUserId(int Userid) {
        List<Integer> travelList = new ArrayList<>();
        //get the travel list by user id

        return travelList;
    }

    public List<Integer> queryTravelItemListByTravelId(int Travelid) {
        List<Integer> travelItemList = new ArrayList<>();

        return travelItemList;
    }
    public TravelItem queryTravelItemByTravelItemId(int TravelItemid) {
        TravelItem travelItem = null;

        return travelItem;
    }

    public Comment queryCommentByCommentId(int Commentid) {
        Comment comment = null;

        return comment;
    }

    public List<Integer> queryCommentListByTravelItemId(int TravelItemid) {
        List<Integer> commentList = new ArrayList<>();

        return commentList;
    }

    public int queryLikeNumByTravelItemId(int TravelItemid) {
        int likeNum = 0;

        return likeNum;
    }
}
