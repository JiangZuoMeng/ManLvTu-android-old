package com.jiangzuomeng.networkManager;

/**
 * Created by wilbert on 2015/12/11.
 */
public class NetworkJsonKeyDefine {
//    public static final String host = "192.168.150.1:3000";
    public static final String host = "192.168.191.1:3000";
    public static final String HTTP = "http";
    public static final String NETWORK_RESULT_KEY = "network_result_key";

    public static final String TARGET_KEY = "target";
    public static final String USER = "user";
    public static final String TRAVEL = "travel";
    public static final String TRAVEL_ITEM = "travelItem";
    public static final String COMMENT = "comment";

    public static final String DATA_KEY = "data";
    public static final String ID = "id";
    public static final String USER_ID = "userId";
    public static final String NAME = "name";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String TRAVEL_ITEM_ID = "travelItemId";
    public static final String TRAVEL_ID = "travelId";
    public static final String LABEL = "label";
    public static final String TIME = "time";
    public static final String LOCATION_LNG = "locationLng";
    public static final String LOCATION_LAT = "locationLat";
    public static final String LIKE = "like";
    public static final String TEXT = "text";
    public static final String MEDIA = "media";

    public static final String LOCATION_LAT_LOWER_BOUND = "locationLatLowerBound";
    public static final String LOCATION_LAT_UPPER_BOUND = "locationLatUpperBound";
    public static final String LOCATION_LNG_LOWER_BOUND = "locationLngLowerBound";
    public static final String LOCATION_LNG_UPPER_BOUND = "locationLngUpperBound";

    public static final String REQUEST_KEY = "request";
    public static final String ADD = "add";
    public static final String QUERY = "query";
    public static final String REMOVE = "remove";
    public static final String UPDATE = "update";
    public static final String QUERY_ALL = "queryAll";
    public static final String LOGIN = "login";
    public static final String REGISTER = "register";
    public static final String QUERY_NEARBY= "queryNearby";

    public static final String RESULT_KEY = "result";
    public static final String RESULT_SUCCESS = "success";
    public static final String RESULT_FAILED = "failed";
    public static final String RESULT_SERVER_FAILED = "server failed";

    public static final int NETWORK_OPERATION = 718392;
    public static final int BEGIN = 718393;
    public static final int ADD_NEW_TRAVEL = BEGIN;
    public static final int ADD_NEW_TRAVEL_ITEM = BEGIN+1;
    public static final int ADD_NEW_USER = BEGIN+2;
    public static final int ADD_NEW_COMMENT = BEGIN+3;
    public static final int QUERY_USER_BY_USER_NAME = BEGIN+4;
    public static final int QUERY_TRAVEL_BY_TRAVEL_ID = BEGIN+5;
    public static final int QUERY_TRAVEL_ITEM_BY_TRAVEL_ITEM_ID = BEGIN+6;

    public static final String ERROR_NETWORK = "error network";
    public static final String ADD_NEW_TRAVEL_KEY = "addNewTravelKey";
    public static final String ADD_NEW_TRAVEL_ITEM_KEY = "addNewTravelItemKey";
    public static final String ADD_NEW_USER_KEY = "addNewUserKey";
    public static final String ADD_NEW_COMMENT_KEY = "addNewCommentKey";
    public static final String QUERY_USER_BY_USER_NAME_KEY = "queryUserByUserName";
    public static final String QUERY_TRAVEL_BY_TRAVEL_ID_KEY = "queryTravelByTravelId";
    public static final String QUERY_TRAVEL_ITEM_BY_TRAVEL_ITEM_ID_KEY ="queryTravelItemByTravelItemId";
}
