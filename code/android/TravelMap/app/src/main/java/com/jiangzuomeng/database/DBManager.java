package com.jiangzuomeng.database;

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
    public DBManager() {

    }

    public static User queryUserById(int Userid) {
        User user = null;
        //get the user from database

        return user;
    }

    public static List<Integer> queryTravelListIdByUserId(int Userid) {
        List<Integer> travelList = new ArrayList<>();
        //get the travel list by user id

        return  travelList;
    }

    public static List<Travel> queryTravelListByUserId(int Userid) {
        List<Travel> travelList = new ArrayList<>();
        //get the travel list by user id

        return  travelList;
    }

    public static List<Integer> queryTravelItemListIdByTravelId(int Travelid) {
        List<Integer> travelItemList = new ArrayList<>();

        return travelItemList;
    }
    public static TravelItem queryTravelItemByTravelItemId(int TravelItemid) {
        TravelItem travelItem = null;

        return travelItem;
    }

    public static Comment queryCommentByCommentId(int Commentid) {
        Comment comment = null;

        return comment;
    }

    public static List<Integer> queryCommentListIdByTravelItemId(int TravelItemid) {
        List<Integer> commentList = new ArrayList<>();

        return commentList;
    }

    public  static int queryLikeNumByTravelItemId(int TravelItemid) {
        int likeNum = 0;

        return likeNum;
    }
}
