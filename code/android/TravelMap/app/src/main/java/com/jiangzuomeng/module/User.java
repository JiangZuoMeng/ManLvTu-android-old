package com.jiangzuomeng.module;

/**
 * Created by ekuri-PC on 2015/11/21.
 */
public class User {
    public int id;
    public String username;
    public String password;
    public int firstTravelId;

    public User(int id, String username, String password, int firstTravelId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstTravelId = firstTravelId;
    }
}
