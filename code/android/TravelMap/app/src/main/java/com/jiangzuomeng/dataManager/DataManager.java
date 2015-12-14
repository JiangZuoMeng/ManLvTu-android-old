package com.jiangzuomeng.dataManager;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.jiangzuomeng.database.DBManager;
import com.jiangzuomeng.modals.Comment;
import com.jiangzuomeng.modals.ManLvTuNetworkDataType;
import com.jiangzuomeng.modals.StaticStrings;
import com.jiangzuomeng.modals.Travel;
import com.jiangzuomeng.modals.TravelItem;
import com.jiangzuomeng.modals.User;
import com.jiangzuomeng.networkManager.NetWorkManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
    }
    public void addNewTravelItem(TravelItem travelItem, Handler handler) {
//        return dbManager.addNewTravelItem(travelItem);
    }
    public void addNewUser(User user, Handler handler) {
        runThreadByKey(StaticStrings.ADD, handler, user);
//        return dbManager.addNewUser(user);
    }
    public void addNewComment(Comment comment,Handler handler) {
//        return dbManager.addNewComment(comment);
    }

    public void queryUserByUserName(String userName, Handler handler) {
    }

    public void queryUserByUserId(int id, Handler handler) {

    }
    public void queryTravelByTravelId(int travelId, Handler handler) {
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

    public void runThreadByKey(final String key, final Handler handler, final ManLvTuNetworkDataType manLvTuNetworkDataType) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = manLvTuNetworkDataType.getUrl(key);
                    String dataString = netWorkManager.getDataFromUrl(url);
                    Message message = new Message();
                    message.what = StaticStrings.NETWORK_OPERATION;
                    Bundle bundle = new Bundle();
                    bundle.putString(StaticStrings.NETWORK_RESULT_KEY, dataString);
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
