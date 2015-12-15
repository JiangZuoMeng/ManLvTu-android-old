package com.jiangzuomeng.dataManager;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.amap.api.maps2d.model.LatLng;
import com.jiangzuomeng.database.DBManager;
import com.jiangzuomeng.modals.Comment;
import com.jiangzuomeng.networkManager.NetworkJsonKeyDefine;
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

    public void login(User user, NetworkHandler handler) {
        try {
            runThreadByUrl(user.getLoginUrl(), handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void registerUser(User user, Handler handler) {
        try {
            runThreadByUrl(user.getAddUrl(), handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void queryUserByUserId(int id, Handler handler) {
        User user = new User();
        user.id = id;
        try {
            runThreadByUrl(user.getQueryUrl(), handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user, Handler handler) {
        try {
            runThreadByUrl(user.getUpdateUrl(), handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserByUserId(int userId, Handler handler) {
        User user = new User();
        user.id = userId;
        try {
            runThreadByUrl(user.getRemoveUrl(), handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void addNewTravel(Travel travel,Handler handler) {
        try {
            runThreadByUrl(travel.getAddUrl(), handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void queryTravelByTravelId(int travelId, Handler handler) {
        Travel travel = new Travel();
        travel.id = travelId;
        try {
            runThreadByUrl(travel.getQueryUrl(), handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void removeTravelByTravelId(int travelId, Handler handler) {
        Travel travel = new Travel();
        travel.id = travelId;
        try {
            runThreadByUrl(travel.getRemoveUrl(), handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void updateTravel(Travel travel, Handler handler) {
        try {
            runThreadByUrl(travel.getUpdateUrl(), handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void queryTravelIdListByUserId(int userId, Handler handler) {
        Travel travel = new Travel();
        travel.userId = userId;
        try {
            runThreadByUrl(travel.getQueryAllUrl(), handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }



    public void addNewTravelItem(TravelItem travelItem, Handler handler) {
        try {
            runThreadByUrl(travelItem.getAddUrl(), handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void queryTravelItemByTravelItemId(int travelItemId, Handler handler) {
        TravelItem travelItem = new TravelItem();
        travelItem.id = travelItemId;
        try {
            runThreadByUrl(travelItem.getQueryUrl(), handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void queryNearbyTravelItem(LatLng currentLocation, Handler handler) {
        double nearDistance = 1.0;
        try {
            runThreadByUrl(TravelItem.getQueryNearbyUrl(currentLocation.latitude - nearDistance,
                    currentLocation.latitude + nearDistance,
                    currentLocation.longitude - nearDistance,
                    currentLocation.longitude + nearDistance), handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void queryTravelItemIdListByTravelId(int travelId, Handler handler) {
        TravelItem travelItem = new TravelItem();
        travelItem.travelId = travelId;
        try {
            runThreadByUrl(travelItem.getQueryAllUrl(), handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void removeTravelItemByTravelItemId(int travelItemId, Handler handler) {
        TravelItem travelItem = new TravelItem();
        travelItem.id = travelItemId;
        try {
            runThreadByUrl(travelItem.getRemoveUrl(), handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void updateTravelItem(TravelItem travelItem, Handler handler) {
        try {
            runThreadByUrl(travelItem.getUpdateUrl(), handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }



    public void addNewComment(Comment comment,Handler handler) {
        try {
            runThreadByUrl(comment.getAddUrl(), handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void queryCommentByCommentId(int commentid, Handler handler) {
        Comment comment = new Comment();
        comment.id = commentid;
        try {
            runThreadByUrl(comment.getQueryUrl(), handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void queryCommentIdListByTravelItemId(int travelItemid, Handler handler) {
        Comment comment = new Comment();
        comment.travelItemId = travelItemid;
        try {
            runThreadByUrl(comment.getQueryAllUrl(), handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void removeCommentByCommentId(int commentId, Handler handler) {
        Comment comment = new Comment();
        comment.id = commentId;
        try {
            runThreadByUrl(comment.getRemoveUrl(), handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void updateComment(Comment comment, Handler handler) {
        try {
            runThreadByUrl(comment.getUpdateUrl(), handler);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void runThreadByUrl(final URL url, final Handler handler) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
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
