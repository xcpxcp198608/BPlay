package com.wiatec.bplay.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wiatec.bplay.Application;
import com.wiatec.bplay.beans.Channel;
import com.wiatec.bplay.utils.Logger;

import java.util.ArrayList;
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
        Cursor cursor = sqLiteDatabase.query(SQLHelper.TABLE_NAME ,null , "name=? or url=?" , new String []{channel.getName() ,channel.getUrl()} , null,null,null);
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
            contentValues.put("favorite",channel.getFavorite());
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

    public boolean setFavorite(Channel channel){
        boolean flag = false;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("favorite",channel.getFavorite());
            sqLiteDatabase.update(SQLHelper.TABLE_NAME,contentValues , "name=? and url=?" ,
                    new String []{channel.getName(),channel.getUrl()});
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
        List<Channel> list = new ArrayList<>();
        try {
            cursor = sqLiteDatabase.query(SQLHelper.TABLE_NAME ,null , "country=?" , new String []{country} , null,null,"sequence");
            while(cursor.moveToNext()){
                Channel channel = new Channel();
                channel.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                channel.setName(cursor.getString(cursor.getColumnIndex("name")));
                channel.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                channel.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
                channel.setType(cursor.getString(cursor.getColumnIndex("type")));
                channel.setCountry(cursor.getString(cursor.getColumnIndex("country")));
                channel.setSequence(cursor.getInt(cursor.getColumnIndex("sequence")));
                channel.setStyle(cursor.getString(cursor.getColumnIndex("style")));
                channel.setFavorite(cursor.getString(cursor.getColumnIndex("favorite")));
                list.add(channel);
            }
        }catch (Exception e){
            e.printStackTrace();
            Logger.d(e.getMessage());
        }finally {
            if(cursor != null){
                cursor.close();
                cursor = null;
            }
        }
        return list;
    }

    public List<Channel> queryByStyle(String style){
        Cursor cursor = null;
        List<Channel> list  = new ArrayList<>();
        try {
            cursor = sqLiteDatabase.query(SQLHelper.TABLE_NAME ,null , "style=?" , new String []{style} , null,null,"name");
            while(cursor.moveToNext()){
                Channel channel = new Channel();
                channel.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                channel.setName(cursor.getString(cursor.getColumnIndex("name")));
                channel.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                channel.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
                channel.setType(cursor.getString(cursor.getColumnIndex("type")));
                channel.setCountry(cursor.getString(cursor.getColumnIndex("country")));
                channel.setSequence(cursor.getInt(cursor.getColumnIndex("sequence")));
                channel.setStyle(cursor.getString(cursor.getColumnIndex("style")));
                channel.setFavorite(cursor.getString(cursor.getColumnIndex("favorite")));
                list.add(channel);
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

    public List<Channel> queryFavorite(){
        Cursor cursor = null;
        List<Channel> list  = new ArrayList<>();
        try {
            cursor = sqLiteDatabase.query(SQLHelper.TABLE_NAME ,null , "favorite=?" , new String []{"true"} , null,null,"name");
            while(cursor.moveToNext()){
                Channel channel = new Channel();
                channel.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                channel.setName(cursor.getString(cursor.getColumnIndex("name")));
                channel.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                channel.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
                channel.setType(cursor.getString(cursor.getColumnIndex("type")));
                channel.setCountry(cursor.getString(cursor.getColumnIndex("country")));
                channel.setSequence(cursor.getInt(cursor.getColumnIndex("sequence")));
                channel.setStyle(cursor.getString(cursor.getColumnIndex("style")));
                channel.setFavorite(cursor.getString(cursor.getColumnIndex("favorite")));
                list.add(channel);
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

    public Channel query(String name ,String url){
        Cursor cursor = null;
        Channel channel = new Channel();
        try {
            cursor = sqLiteDatabase.query(SQLHelper.TABLE_NAME ,null , "name=? and url=?" , new String []{name , url} , null,null,"name");
            while(cursor.moveToNext()) {
                channel.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                channel.setName(cursor.getString(cursor.getColumnIndex("name")));
                channel.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                channel.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
                channel.setType(cursor.getString(cursor.getColumnIndex("type")));
                channel.setCountry(cursor.getString(cursor.getColumnIndex("country")));
                channel.setSequence(cursor.getInt(cursor.getColumnIndex("sequence")));
                channel.setStyle(cursor.getString(cursor.getColumnIndex("style")));
                channel.setFavorite(cursor.getString(cursor.getColumnIndex("favorite")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor != null){
                cursor.close();
                cursor = null;
            }
        }
        return channel;
    }

}
