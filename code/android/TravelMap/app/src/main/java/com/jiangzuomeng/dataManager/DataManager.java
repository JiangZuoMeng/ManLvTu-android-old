package com.jiangzuomeng.dataManager;

import android.content.Context;

import com.jiangzuomeng.database.DBManager;
import com.jiangzuomeng.module.Comment;
import com.jiangzuomeng.module.Travel;
import com.jiangzuomeng.module.TravelItem;
import com.jiangzuomeng.module.User;
import com.jiangzuomeng.networkManager.NetWorkManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wilbert on 2015/11/22.
 */
public class DataManager {
    private static DataManager dataManager = null;
    private DBManager dbManager;
    private NetWorkManager netWorkManager;

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

    public long addNewTravel(Travel travel) {
        return dbManager.addNewTravel(travel);
    }
    public long addNewTravelItem(TravelItem travelItem) {
        return dbManager.addNewTravelItem(travelItem);
    }
    public long addNewUser(User user) {
        return dbManager.addNewUser(user);
    }
    public long addNewComment(Comment comment) {
        return dbManager.addNewComment(comment);
    }

    public User queryUserByUserName(String userName) {
        User user = dbManager.queryUserByUsername(userName);
        return user;
    }
    public Travel queryTravelByTravelId(int travelId) {
        return dbManager.queryTravelByTravelId(travelId);
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
}
