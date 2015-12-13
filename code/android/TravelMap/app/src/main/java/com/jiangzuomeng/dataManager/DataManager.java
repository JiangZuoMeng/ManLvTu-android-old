package com.jiangzuomeng.dataManager;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.jiangzuomeng.database.DBManager;
import com.jiangzuomeng.module.Comment;
import com.jiangzuomeng.module.StaticStrings;
import com.jiangzuomeng.module.Travel;
import com.jiangzuomeng.module.TravelItem;
import com.jiangzuomeng.module.User;
import com.jiangzuomeng.networkManager.NetWorkManager;

import java.io.CharArrayReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wilbert on 2015/11/22.
 */
public class DataManager {
    private static DataManager dataManager = null;
    private DBManager dbManager;
    private NetWorkManager netWorkManager;
    private Bundle bundle = new Bundle();
    public static DataManager getInstance(Context context) {
        if (dataManager == null) {
            dataManager = new DataManager(context);
        }
        return dataManager;
    }

    private DataManager(Context context) {
        dbManager = new DBManager(context);
        netWorkManager = new NetWorkManager();
    }


    public void addNewTravel(Travel travel,Handler handler) {
        runThreadByKey(StaticStrings.ADD_NEW_TRAVEL, handler, travel);
    }
    public void addNewTravelItem(TravelItem travelItem, Handler handler) {
        runThreadByKey(StaticStrings.ADD_NEW_TRAVEL_ITEM, handler, travelItem);
//        return dbManager.addNewTravelItem(travelItem);
    }
    public void addNewUser(User user, Handler handler) {
        runThreadByKey(StaticStrings.ADD_NEW_USER, handler, user);
//        return dbManager.addNewUser(user);
    }
    public void addNewComment(Comment comment,Handler handler) {
        runThreadByKey(StaticStrings.ADD_NEW_COMMENT, handler, comment);
//        return dbManager.addNewComment(comment);
    }

    public void queryUserByUserName(String userName, Handler handler) {
        runThreadByKey(StaticStrings.QUERY_USER_BY_USER_NAME, handler, userName);
/*
        User user = dbManager.queryUserByUsername(userName);
        return user;
*/
    }
    public void queryTravelByTravelId(int travelId, Handler handler) {
        runThreadByKey(StaticStrings.QUERY_TRAVEL_BY_TRAVEL_ID, handler, travelId);
//        return dbManager.queryTravelByTravelId(travelId);
    }
    public TravelItem queryTravelItemByTravelItemId(int travelItemid) {
        TravelItem travelItem = dbManager.queryTravelItemByTravelItemId(travelItemid);

        return travelItem;
    }
    public Comment queryCommentByCommentId(int commentid) {
        Comment comment = dbManager.queryCommentByCommentId(commentid);

        return comment;
    }
    public List<Integer> queryTravelIdListByUserId(int userId) {
        List<Integer> travelList = dbManager.queryTravelIdListByUserId(userId);
        //get the travel list by user id

        return travelList;
    }
    public List<Integer> queryTravelItemIdListByTravelId(int travelid) {
        List<Integer> travelItemIdList = dbManager.queryTravelItemIdListByTravelId(travelid);

        return travelItemIdList;
    }
    public List<Integer> queryCommentIdListByTravelItemId(int travelItemid) {
        List<Integer> commentList = dbManager.queryCommentIdListByTravelItemId(travelItemid);

        return commentList;
    }
    public List<Travel> queryTravelListByUserId(int userId) {
        List<Travel> travelList = dbManager.queryTravelListByUserId(userId);
        //get the travel list by user id

        return  travelList;
    }
    public List<TravelItem> queryTravelItemListByTravelId(int travelId) {
        return dbManager.queryTravelItemListByTravelId(travelId);
    }
    public List<Comment> queryCommentListByTravelItemId(int travelItemId) {
        return dbManager.queryCommentListByTravelItemId(travelItemId);
    }
    public int queryLikeNumByTravelItemId(int TravelItemid) {
        int likeNum = 0;

        return likeNum;
    }

    public int removeUserByUserId(int userId) {
        return dbManager.removeUserByUserId(userId);
    }
    public int removeTravelByTravelId(int travelId) {
        return dbManager.removeTravelByTravelId(travelId);
    }
    public int removeTravelItemByTravelItemId(int travelItemId) {
        return dbManager.removeTravelItemByTravelItemId(travelItemId);
    }
    public int removeCommentByCommentId(int commentId) {
        return dbManager.removeCommentByCommentId(commentId);
    }

    public int updateComment(Comment comment) {
        return dbManager.updateComment(comment);
    }
    public int updateTravelItem(TravelItem travelItem) {
        return dbManager.updateTravelItem(travelItem);
    }
    public int updateTravel(Travel travel) {
        return dbManager.updateTravel(travel);
    }
    public int updateUser(User user) {
        return dbManager.updateUser(user);
    }

    public class MyThread implements Runnable {
        Handler handler;
        public MyThread(Handler handler) {
            this.handler = handler;
        }
        @Override
        public void run() {
            Message message = new Message();
            message.what = StaticStrings.ADD_NEW_TRAVEL_ITEM;
            bundle.clear();
            String jsonString = null;
            try {
                jsonString = netWorkManager.addNewTravelItem(new TravelItem());
                bundle.putString(StaticStrings.ADD_NEW_TRAVEL_ITEM_KEY, jsonString);
                handler.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void runThreadByKey(final int key,final Handler handler, final Object object) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsonString = StaticStrings.ERROR_NETWORK;
                    Message message = new Message();
                    bundle.clear();
                    switch (key) {
                        case StaticStrings.ADD_NEW_TRAVEL:
                            Travel travel = (Travel)object;
                            message.what = StaticStrings.ADD_NEW_TRAVEL;
                            jsonString = netWorkManager.addNewTravel(travel);
                            bundle.putString(StaticStrings.ADD_NEW_TRAVEL_KEY, jsonString);
                            break;
                        case StaticStrings.ADD_NEW_TRAVEL_ITEM:
                            TravelItem travelItem = (TravelItem)object;
                            message.what = StaticStrings.ADD_NEW_TRAVEL_ITEM;
                            jsonString = netWorkManager.addNewTravelItem(travelItem);
                            bundle.putString(StaticStrings.ADD_NEW_TRAVEL_ITEM_KEY, jsonString);
                            break;
                        case StaticStrings.ADD_NEW_USER:
                            User user = (User)object;
                            message.what = StaticStrings.ADD_NEW_USER;
                            jsonString = netWorkManager.addNewUser(user);
                            bundle.putString(StaticStrings.ADD_NEW_USER_KEY, jsonString);
                            break;
                        case StaticStrings.ADD_NEW_COMMENT:
                            Comment comment = (Comment)object;
                            message.what = StaticStrings.ADD_NEW_COMMENT;
                            jsonString = netWorkManager.addNewComment(comment);
                            bundle.putString(StaticStrings.ADD_NEW_COMMENT_KEY, jsonString);
                            break;
                        case StaticStrings.QUERY_USER_BY_USER_NAME:
                            String userName = (String)object;
                            jsonString = netWorkManager.queryUserByUsername(userName);
                            bundle.putString(StaticStrings.QUERY_USER_BY_USER_NAME_KEY, jsonString);
                            break;
                        case StaticStrings.QUERY_TRAVEL_BY_TRAVEL_ID:
                            int travelId = (int)object;
                            jsonString = netWorkManager.queryTravelByTravelId(travelId);
                            bundle.putString(StaticStrings.QUERY_TRAVEL_BY_TRAVEL_ID_KEY, jsonString);
                            break;

                    }
                    message.what = key;
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
