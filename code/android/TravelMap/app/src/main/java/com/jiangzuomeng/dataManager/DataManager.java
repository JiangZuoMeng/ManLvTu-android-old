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

    public User queryUserByUserName(String userName) {
        User user = null;
        return user;
    }
    public List<Integer> queryTravelIdListByUserId(int userId) {
        List<Integer> travelList = dbManager.queryTravelIdListByUserId(userId);
        //get the travel list by user id

        return travelList;
    }

    public List<Travel> queryTravelListByUserId(int userId) {
        List<Travel> travelList = dbManager.queryTravelListByUserId(userId);
        //get the travel list by user id

        return  travelList;
    }


    public List<Integer> queryTravelItemIdListByTravelId(int travelid) {
        List<Integer> travelItemIdList = dbManager.queryTravelItemIdListByTravelId(travelid);

        return travelItemIdList;
    }
    public TravelItem queryTravelItemByTravelItemId(int travelItemid) {
        TravelItem travelItem = dbManager.queryTravelItemByTravelItemId(travelItemid);

        return travelItem;
    }

    public Comment queryCommentByCommentId(int commentid) {
        Comment comment = dbManager.queryCommentByCommentId(commentid);

        return comment;
    }

    public List<Integer> queryCommentListIdByTravelItemId(int travelItemid) {
        List<Integer> commentList = dbManager.queryCommentListIdByTravelItemId(travelItemid);

        return commentList;
    }

    public int queryLikeNumByTravelItemId(int TravelItemid) {
        int likeNum = 0;

        return likeNum;
    }

}
