package com.jiangzuomeng.dataManager;

import android.content.Context;
import android.net.Uri;
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
import java.util.StringTokenizer;

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

    public boolean login(String userName, String passWord) throws IOException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(StaticStrings.HTTP)
                .encodedAuthority(StaticStrings.host)
                .appendPath(StaticStrings.USER)
                .appendPath(StaticStrings.LOGIN)
                .appendQueryParameter(StaticStrings.USERNAME, userName)
                .appendQueryParameter(StaticStrings.PASSWORD, passWord);
        URL url = new URL(builder.build().toString());
        String dataString = netWorkManager.getDataFromUrl(url);


    }

    public void addNewUser(User user, Handler handler) {
        runThreadByKey(StaticStrings.ADD, handler, user);
    }

    public void queryUserByUserName(String userName, Handler handler) {
    }

    public void queryUserByUserId(int id, Handler handler) {
        User user = new User();
        user.id = id;
        runThreadByKey(StaticStrings.QUERY, handler, user);
    }

    public void updateUser(User user, Handler handler) {
        runThreadByKey(StaticStrings.UPDATE, handler, user);
    }

    public void removeUserByUserId(int userId, Handler handler) {
        User user = new User();
        user.id = userId;
        runThreadByKey(StaticStrings.REMOVE, handler, user);
    }



    public void addNewTravel(Travel travel,Handler handler) {
        runThreadByKey(StaticStrings.ADD, handler, travel);
    }

    public void queryTravelByTravelId(int travelId, Handler handler) {
        Travel travel = new Travel();
        travel.id = travelId;
        runThreadByKey(StaticStrings.QUERY, handler, travel);
    }

    public void removeTravelByTravelId(int travelId, Handler handler) {
        Travel travel = new Travel();
        travel.id = travelId;
        runThreadByKey(StaticStrings.REMOVE, handler, travel);
    }

    public void updateTravel(Travel travel, Handler handler) {
        runThreadByKey(StaticStrings.UPDATE, handler, travel);
    }

    public void queryTravelIdListByUserId(int userId, Handler handler) {
        Travel travel = new Travel();
        travel.userId = userId;
        runThreadByKey(StaticStrings.QUERY_ALL, handler, travel);
    }



    public void addNewTravelItem(TravelItem travelItem, Handler handler) {
        runThreadByKey(StaticStrings.ADD, handler, travelItem);
    }

    public void queryTravelItemByTravelItemId(int travelItemid, Handler handler) {
        TravelItem travelItem = new TravelItem();
        travelItem.id = travelItemid;
        runThreadByKey(StaticStrings.QUERY, handler, travelItem);
    }

    public void queryTravelItemIdListByTravelId(int travelid, Handler handler) {
        TravelItem travelItem = new TravelItem();
        travelItem.travelId = travelid;
        runThreadByKey(StaticStrings.QUERY_ALL, handler, travelItem);
    }

    public void removeTravelItemByTravelItemId(int travelItemId, Handler handler) {
        TravelItem travelItem = new TravelItem();
        travelItem.id = travelItemId;
        runThreadByKey(StaticStrings.REMOVE, handler, travelItem);
    }

    public void updateTravelItem(TravelItem travelItem, Handler handler) {
        runThreadByKey(StaticStrings.UPDATE, handler, travelItem);
    }



    public void addNewComment(Comment comment,Handler handler) {
        runThreadByKey(StaticStrings.ADD, handler, comment);
    }

    public void queryCommentByCommentId(int commentid, Handler handler) {
        Comment comment = new Comment();
        comment.id = commentid;
        runThreadByKey(StaticStrings.QUERY, handler, comment);
    }

    public void queryCommentIdListByTravelItemId(int travelItemid, Handler handler) {
        Comment comment = new Comment();
        comment.travelItemId = travelItemid;
        runThreadByKey(StaticStrings.QUERY_ALL, handler, comment);
    }

    public void removeCommentByCommentId(int commentId, Handler handler) {
        Comment comment = new Comment();
        comment.id = commentId;
        runThreadByKey(StaticStrings.REMOVE, handler, comment);
    }

    public void updateComment(Comment comment, Handler handler) {
        runThreadByKey(StaticStrings.UPDATE, handler, comment);
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
