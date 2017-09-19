package com.bwei.czx.jinritoutiao.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by czx on 2017/9/6.
 */

public class MySqlitHelper extends SQLiteOpenHelper {
    public MySqlitHelper(Context context) {
        super(context, "czxjrtt1507", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table user(title varchar(20),url varchar(20),type varchar(20))";
        db.execSQL(sql);
        String sql1 = "create table shoucang(title varchar(20),url varchar(20),type varchar(20),img1 varchar(20),img2 varchar(20),img3 varchar(20),video varchar(20))";
        db.execSQL(sql1);
        String sql2 = "create table cache(title varchar(20),json text)";
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
