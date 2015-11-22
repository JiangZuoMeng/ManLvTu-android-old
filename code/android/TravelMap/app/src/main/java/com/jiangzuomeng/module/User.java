package com.jiangzuomeng.module;

import android.content.ContentValues;

/**
 * Created by ekuri-PC on 2015/11/21.
 */
public class User implements ManLvTuSQLDataType {
    public static final String USER_TABLE_NAME = "user";
    public int id;
    public String username;
    public String password;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User() {

    }

    @Override
    public ContentValues makeInsertSQLContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", this.username);
        contentValues.put("password", this.password);
        return contentValues;
    }

    public static String makeCreateTableSQLString() {
        return "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)";
    }


    public String makeQueryByUsernameSQLString() {
        return null;
    }

    public String makeQueryByUserIdSQLString() {
        return null;
    }
}
