package com.example.shiva.stockmanagement;

import android.net.Uri;

public class Util {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Users.db";

    public static final String TAB_NAME = "User";

    public static final String COL_ID = "_ID";
    public static final String COL_ITEMNAME = "ITEMNAME";
    public static final String COL_QTY = "QTY";


    public static final String CREATE_TAB_QUERY = "create table User(" +
            "_ID integer primary key autoincrement," +
            "ITEMNAME varchar(256)," +
            "QTY varchar(256)" +
            ")";

    public static final Uri USER_URI = Uri.parse("content://a.b.c.d/"+TAB_NAME);

}
