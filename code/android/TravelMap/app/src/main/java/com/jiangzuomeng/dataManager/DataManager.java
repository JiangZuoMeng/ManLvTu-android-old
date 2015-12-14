package com.jiangzuomeng.dataManager;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.jiangzuomeng.database.DBManager;
import com.jiangzuomeng.modals.Comment;
import com.jiangzuomeng.networkManager.ManLvTuNetworkDataType;
import com.jiangzuomeng.networkManager.NetworkJsonKeyDefine;
import com.jiangzuomeng.modals.Travel;
import com.jiangzuomeng.modals.TravelItem;
import com.jiangzuomeng.modals.User;
import com.jiangzuomeng.networkManager.NetWorkManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

    public void login(User user, NetworkHandler handler) {
        runThreadByKey(NetworkJsonKeyDefine.LOGIN, handler, user);
    }

    public void addNewUser(User user, Handler handler) {
        runThreadByKey(NetworkJsonKeyDefine.ADD, handler, user);
    }

    public void queryUserByUserName(String userName, Handler handler) {
    }

    public void queryUserByUserId(int id, Handler handler) {
        User user = new User();
        user.id = id;
        runThreadByKey(NetworkJsonKeyDefine.QUERY, handler, user);
    }

    public void updateUser(User user, Handler handler) {
        runThreadByKey(NetworkJsonKeyDefine.UPDATE, handler, user);
    }

    public void removeUserByUserId(int userId, Handler handler) {
        User user = new User();
        user.id = userId;
        runThreadByKey(NetworkJsonKeyDefine.REMOVE, handler, user);
    }



    public void addNewTravel(Travel travel,Handler handler) {
        runThreadByKey(NetworkJsonKeyDefine.ADD, handler, travel);
    }

    public void queryTravelByTravelId(int travelId, Handler handler) {
        Travel travel = new Travel();
        travel.id = travelId;
        runThreadByKey(NetworkJsonKeyDefine.QUERY, handler, travel);
    }

    public void removeTravelByTravelId(int travelId, Handler handler) {
        Travel travel = new Travel();
        travel.id = travelId;
        runThreadByKey(NetworkJsonKeyDefine.REMOVE, handler, travel);
    }

    public void updateTravel(Travel travel, Handler handler) {
        runThreadByKey(NetworkJsonKeyDefine.UPDATE, handler, travel);
    }

    public void queryTravelIdListByUserId(int userId, Handler handler) {
        Travel travel = new Travel();
        travel.userId = userId;
        runThreadByKey(NetworkJsonKeyDefine.QUERY_ALL, handler, travel);
    }



    public void addNewTravelItem(TravelItem travelItem, Handler handler) {
        runThreadByKey(NetworkJsonKeyDefine.ADD, handler, travelItem);
    }

    public void queryTravelItemByTravelItemId(int travelItemid, Handler handler) {
        TravelItem travelItem = new TravelItem();
        travelItem.id = travelItemid;
        runThreadByKey(NetworkJsonKeyDefine.QUERY, handler, travelItem);
    }

    public void queryTravelItemIdListByTravelId(int travelid, Handler handler) {
        TravelItem travelItem = new TravelItem();
        travelItem.travelId = travelid;
        runThreadByKey(NetworkJsonKeyDefine.QUERY_ALL, handler, travelItem);
    }

    public void removeTravelItemByTravelItemId(int travelItemId, Handler handler) {
        TravelItem travelItem = new TravelItem();
        travelItem.id = travelItemId;
        runThreadByKey(NetworkJsonKeyDefine.REMOVE, handler, travelItem);
    }

    public void updateTravelItem(TravelItem travelItem, Handler handler) {
        runThreadByKey(NetworkJsonKeyDefine.UPDATE, handler, travelItem);
    }



    public void addNewComment(Comment comment,Handler handler) {
        runThreadByKey(NetworkJsonKeyDefine.ADD, handler, comment);
    }

    public void queryCommentByCommentId(int commentid, Handler handler) {
        Comment comment = new Comment();
        comment.id = commentid;
        runThreadByKey(NetworkJsonKeyDefine.QUERY, handler, comment);
    }

    public void queryCommentIdListByTravelItemId(int travelItemid, Handler handler) {
        Comment comment = new Comment();
        comment.travelItemId = travelItemid;
        runThreadByKey(NetworkJsonKeyDefine.QUERY_ALL, handler, comment);
    }

    public void removeCommentByCommentId(int commentId, Handler handler) {
        Comment comment = new Comment();
        comment.id = commentId;
        runThreadByKey(NetworkJsonKeyDefine.REMOVE, handler, comment);
    }

    public void updateComment(Comment comment, Handler handler) {
        runThreadByKey(NetworkJsonKeyDefine.UPDATE, handler, comment);
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










    public void runThreadByKey(final String key, final Handler handler, final ManLvTuNetworkDataType manLvTuNetworkDataType) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = manLvTuNetworkDataType.getUrl(key);
                    String dataString = netWorkManager.getDataFromUrl(url);
                    Message message = new Message();
                    message.what = NetworkJsonKeyDefine.NETWORK_OPERATION;
                    Bundle bundle = new Bundle();
                    bundle.putString(NetworkJsonKeyDefine.NETWORK_RESULT_KEY, dataString);
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
