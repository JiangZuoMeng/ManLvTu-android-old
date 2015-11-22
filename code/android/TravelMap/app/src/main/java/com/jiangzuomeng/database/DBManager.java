package com.jiangzuomeng.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiangzuomeng.module.Comment;
import com.jiangzuomeng.module.ManLvTuSQLDataType;
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
        ContentValues values = user.makeInsertSQLContentValues();
        return database.insert(User.USER_TABLE_NAME, null, values);
    }
    public long addNewTravel(Travel travel) {
        ContentValues values = travel.makeInsertSQLContentValues();
        return database.insert(Travel.TRAVEL_TABLE_NAME, null, values);
    }
    public long addNewTravelItem(TravelItem travelItem) {
        ContentValues values = travelItem.makeInsertSQLContentValues();
        return database.insert(TravelItem.TRAVEL_ITEM_TABLE_NAME, null, values);
    }
    public long addNewComment(Comment comment) {
        ContentValues values = comment.makeInsertSQLContentValues();
        return database.insert(Comment.COMMENT_TABLE_NAME, null, values);
    }

    public User queryUserById(int userId) {
        Cursor cursor = database.query(User.USER_TABLE_NAME, null,
                "WHERE id = " + String.valueOf(userId), null, null, null, null);

        if (cursor.getCount() < 1)
            return null;

        cursor.moveToFirst();
        User user = getUserFromCursor(cursor);
        cursor.close();
        return user;
    }
    public User queryUserByUsername(String username) {
        Cursor cursor = database.query(User.USER_TABLE_NAME, null,
                "username = ?", new String[] {username}, null, null, null);

        if (cursor.getCount() < 1)
            return null;

        cursor.moveToFirst();
        User user = getUserFromCursor(cursor);
        cursor.close();
        return user;
    }
    public Travel queryTravelByTravelId(int travelId) {
        Cursor cursor = database.query(Travel.TRAVEL_TABLE_NAME, null,
                "id = ?", new String[] {String.valueOf(travelId)}, null, null, null);

        if (cursor.getCount() < 1)
            return null;

        cursor.moveToFirst();
        Travel travel = new Travel();
        travel.id = cursor.getInt(cursor.getColumnIndex("id"));
        travel.userId = cursor.getInt(cursor.getColumnIndex("userId"));
        travel.name = cursor.getString(cursor.getColumnIndex("name"));
        cursor.close();
        return travel;
    }
    public TravelItem queryTravelItemByTravelItemId(int travelItemId) {
        Cursor cursor = database.query(TravelItem.TRAVEL_ITEM_TABLE_NAME, null,
                "id = ?", new String[] {String.valueOf(travelItemId)}, null, null, null);

        if (cursor.getCount() < 1)
            return null;

        cursor.moveToFirst();
        TravelItem travelItem = new TravelItem();
        travelItem.id = cursor.getInt(cursor.getColumnIndex("id"));
        travelItem.travelId = cursor.getInt(cursor.getColumnIndex("travelId"));
        travelItem.label = cursor.getString(cursor.getColumnIndex("label"));
        travelItem.like = cursor.getInt(cursor.getColumnIndex("like"));
        travelItem.locationLat = cursor.getDouble(cursor.getColumnIndex("locationLat"));
        travelItem.locationLng = cursor.getDouble(cursor.getColumnIndex("locationLng"));
        travelItem.text = cursor.getString(cursor.getColumnIndex("text"));
        travelItem.media = cursor.getString(cursor.getColumnIndex("media"));
        travelItem.time = cursor.getString(cursor.getColumnIndex("time"));
        cursor.close();
        return travelItem;
    }
    public Comment queryCommentByCommentId(int commentId) {
        Cursor cursor = database.query(Comment.COMMENT_TABLE_NAME, null,
                "id = ?", new String[] {String.valueOf(commentId)}, null, null, null);

        if (cursor.getCount() < 1)
            return null;

        cursor.moveToFirst();
        Comment comment = new Comment();
        comment.id = cursor.getInt(cursor.getColumnIndex("id"));
        comment.travelItemId = cursor.getInt(cursor.getColumnIndex("travelItemId"));
        comment.userId = cursor.getInt(cursor.getColumnIndex("userId"));
        comment.text = cursor.getString(cursor.getColumnIndex("text"));
        cursor.close();
        return comment;
    }
    public List<Integer> queryTravelIdListByUserId(int userId) {
        List<Integer> travelIdList = new ArrayList<>();

        Cursor cursor = database.query(Travel.TRAVEL_TABLE_NAME, new String[]{"id"},
                "userId = ?", new String[]{String.valueOf(userId)}, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            travelIdList.add(cursor.getInt(cursor.getColumnIndex("id")));
            cursor.moveToNext();
        }

        return travelIdList;
    }
    public List<Integer> queryTravelItemIdListByTravelId(int travelId) {
        List<Integer> travelItemIdList = new ArrayList<>();

        Cursor cursor = database.query(TravelItem.TRAVEL_ITEM_TABLE_NAME, new String[]{"id"},
                "travelId = ?", new String[]{String.valueOf(travelId)}, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            travelItemIdList.add(cursor.getInt(cursor.getColumnIndex("id")));
            cursor.moveToNext();
        }

        return travelItemIdList;
    }
    public List<Integer> queryCommentIdListByTravelItemId(int travelItemId) {
        List<Integer> commentIdList = new ArrayList<>();

        Cursor cursor = database.query(Comment.COMMENT_TABLE_NAME, new String[]{"id"},
                "travelItemId = ?", new String[]{String.valueOf(travelItemId)}, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            commentIdList.add(cursor.getInt(cursor.getColumnIndex("id")));
            cursor.moveToNext();
        }

        return commentIdList;
    }
    public int queryLikeNumByTravelItemId(int TravelItemid) {
        int likeNum = 0;

        return likeNum;
    }

    public List<Travel> queryTravelListByUserId(int userId) {
        List<Integer>
    }
}
