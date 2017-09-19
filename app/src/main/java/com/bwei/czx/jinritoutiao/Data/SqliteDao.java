package com.bwei.czx.jinritoutiao.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bwei.czx.jinritoutiao.Bean.Cache;
import com.bwei.czx.jinritoutiao.Bean.ScData;
import com.bwei.czx.jinritoutiao.Bean.User;
import com.bwei.czx.jinritoutiao.Bean.YscData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czx on 2017/9/6.
 */

public class SqliteDao {

    private MySqlitHelper helper;

    public SqliteDao(Context context) {
        helper = new MySqlitHelper(context);
    }
    public void add(String title,String url,String type){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("url",url);
        values.put("type",type);
        db.insert("user",null,values);
        db.close();
    }
    public void delete(String title){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from user where title = ?",new Object[]{title});
        db.close();
    }
    public void update(String title){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("update user set type = 2 where title = ?",new Object[]{title});
        db.close();
    }
    public void update2(String title){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("update user set type = 1 where title = ?",new Object[]{title});
        db.close();
    }
    public List<User> select(){
        List<User> list = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from user where type = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{"1"});
        while(cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            list.add(new User(title,url,type));
        }
        db.close();
        return list;
    }
    public List<User> select2(){
        List<User> list = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from user where type = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{"2"});
        while(cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            list.add(new User(title,url,type));
        }
        db.close();
        return list;
    }


    /**
     *添加收藏的数据
     */
    public void addsc(String title,String url,String type,String img1,String img2,String img3,String video){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("url",url);
        values.put("type",type);
        values.put("img1",img1);
        values.put("img2",img2);
        values.put("img3",img3);
        values.put("video",video);
        db.insert("shoucang",null,values);
        db.close();
    }
    //删除收藏的数据
    public void deletesc(String title){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from shoucang where title = ?",new Object[]{title});
        db.close();
    }

    //查询收藏全部的数据
    public List<YscData> selectScAll(){
        List<YscData> list = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from shoucang ";
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String img = cursor.getString(cursor.getColumnIndex("img1"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            list.add(new YscData(title,img,url));
        }
        db.close();
        return list;
    }
    //根据title查询收藏数据
    public Cursor selectSc(String fondtitle){
        List<ScData> list = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from shoucang where title = "+ fondtitle;
        Cursor cursor = db.rawQuery(sql, null);
        db.close();
        return cursor;
    }
    //缓存增加
    public void addcache(String title,String json){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("json",json);
        db.insert("cache",null,values);
        db.close();
    }
    //缓存修改
    public void updatecache(String title,String json){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("update cache set json = ? where title = ?",new Object[]{json,title});
        db.close();
    }
    //缓存查询
    public List<Cache> selectchche(String tit){
        List<Cache> list = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from cache where title = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{tit});
        while(cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String json = cursor.getString(cursor.getColumnIndex("json"));
            list.add(new Cache(title,json));
        }
        db.close();
        return list;
    }

}
