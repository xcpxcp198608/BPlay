package com.wiatec.bplay.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wiatec.bplay.Application;
import com.wiatec.bplay.beans.Channel;

import java.util.List;

/**
 * Created by patrick on 2017/2/10.
 */

public class ChannelDao {
    private SQLiteDatabase sqLiteDatabase;
    private ChannelDao(Context context){
        sqLiteDatabase = new SQLHelper(context).getWritableDatabase();
    }

    private volatile static ChannelDao instance;
    public static ChannelDao getInstance(Context context){
        if(instance == null){
            synchronized (ChannelDao.class){
                if(instance ==null){
                    instance = new ChannelDao(context);
                }
            }
        }
        return instance;
    }

    public boolean isExists(Channel channel){
        Cursor cursor = sqLiteDatabase.query(SQLHelper.TABLE_NAME ,null , "name=?" , new String []{channel.getName()} , null,null,null);
        boolean flag = cursor.moveToNext();
        if(cursor != null){
            cursor.close();
            cursor = null;
        }
        return flag;
    }

    public boolean insert (Channel channel){
        boolean flag = false;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_id",channel.getId());
            contentValues.put("name",channel.getName());
            contentValues.put("url",channel.getUrl());
            contentValues.put("icon",channel.getIcon());
            contentValues.put("type",channel.getType());
            contentValues.put("country",channel.getCountry());
            contentValues.put("sequence",channel.getSequence());
            contentValues.put("style",channel.getStyle());
            sqLiteDatabase.insert(SQLHelper.TABLE_NAME , null ,contentValues);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public boolean update(Channel channel){
        boolean flag = false;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_id",channel.getId());
            contentValues.put("name",channel.getName());
            contentValues.put("url",channel.getUrl());
            contentValues.put("icon",channel.getIcon());
            contentValues.put("type",channel.getType());
            contentValues.put("country",channel.getCountry());
            contentValues.put("sequence",channel.getSequence());
            contentValues.put("style",channel.getStyle());
            sqLiteDatabase.update(SQLHelper.TABLE_NAME,contentValues , "name=?" , new String []{channel.getName()});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public void insertOrUpdate(Channel channel){
        if(isExists(channel)){
            update(channel);
        }else{
            insert(channel);
        }
    }

    public void multiInsert(List<Channel> list){
        for(Channel channel : list){
            insertOrUpdate(channel);
        }
    }

    public boolean deleteAll(){
        boolean flag = false;
        try {
            sqLiteDatabase.delete(SQLHelper.TABLE_NAME ,"_id>?" , new String[]{"0"});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public List<Channel> queryByCountry(String country){
        Cursor cursor = null;
        List<Channel> list = null;
        try {
            cursor = sqLiteDatabase.query(SQLHelper.TABLE_NAME ,null , "country=?" , new String []{country} , null,null,null);
            while(cursor.moveToNext()){
                Channel channel = new Channel();
                channel.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor != null){
                cursor.close();
                cursor = null;
            }
        }
        return list;
    }

}
