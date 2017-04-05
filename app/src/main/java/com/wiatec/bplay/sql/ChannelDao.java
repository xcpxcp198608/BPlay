package com.wiatec.bplay.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wiatec.bplay.beans.ChannelInfo;
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

    public boolean isExists(ChannelInfo channelInfo){
        Cursor cursor = sqLiteDatabase.query(SQLHelper.TABLE_NAME ,null , "name=? or url=?" , new String []{channelInfo.getName() , channelInfo.getUrl()} , null,null,null);
        boolean flag = cursor.moveToNext();
        if(cursor != null){
            cursor.close();
            cursor = null;
        }
        return flag;
    }

    public boolean insert (ChannelInfo channelInfo){
        boolean flag = false;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_id", channelInfo.getId());
            contentValues.put("name", channelInfo.getName());
            contentValues.put("url", channelInfo.getUrl());
            contentValues.put("icon", channelInfo.getIcon());
            contentValues.put("type", channelInfo.getType());
            contentValues.put("country", channelInfo.getCountry());
            contentValues.put("sequence", channelInfo.getSequence());
            contentValues.put("style", channelInfo.getStyle());
            contentValues.put("favorite", channelInfo.getFavorite());
            sqLiteDatabase.insert(SQLHelper.TABLE_NAME , null ,contentValues);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public boolean update(ChannelInfo channelInfo){
        boolean flag = false;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_id", channelInfo.getId());
            contentValues.put("name", channelInfo.getName());
            contentValues.put("url", channelInfo.getUrl());
            contentValues.put("icon", channelInfo.getIcon());
            contentValues.put("type", channelInfo.getType());
            contentValues.put("country", channelInfo.getCountry());
            contentValues.put("sequence", channelInfo.getSequence());
            contentValues.put("style", channelInfo.getStyle());
            sqLiteDatabase.update(SQLHelper.TABLE_NAME,contentValues , "name=?" , new String []{channelInfo.getName()});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public boolean setFavorite(ChannelInfo channelInfo){
        boolean flag = false;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("favorite", channelInfo.getFavorite());
            sqLiteDatabase.update(SQLHelper.TABLE_NAME,contentValues , "name=? and url=?" ,
                    new String []{channelInfo.getName(), channelInfo.getUrl()});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public void insertOrUpdate(ChannelInfo channelInfo){
        if(isExists(channelInfo)){
            update(channelInfo);
        }else{
            insert(channelInfo);
        }
    }

    public void multiInsert(List<ChannelInfo> list){
        for(ChannelInfo channelInfo : list){
            insertOrUpdate(channelInfo);
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

    public List<ChannelInfo> queryByCountry(String country){
        Cursor cursor = null;
        List<ChannelInfo> list = new ArrayList<>();
        try {
            cursor = sqLiteDatabase.query(SQLHelper.TABLE_NAME ,null , "country=?" , new String []{country} , null,null,"sequence");
            while(cursor.moveToNext()){
                ChannelInfo channelInfo = new ChannelInfo();
                channelInfo.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                channelInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
                channelInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                channelInfo.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
                channelInfo.setType(cursor.getString(cursor.getColumnIndex("type")));
                channelInfo.setCountry(cursor.getString(cursor.getColumnIndex("country")));
                channelInfo.setSequence(cursor.getInt(cursor.getColumnIndex("sequence")));
                channelInfo.setStyle(cursor.getString(cursor.getColumnIndex("style")));
                channelInfo.setFavorite(cursor.getString(cursor.getColumnIndex("favorite")));
                list.add(channelInfo);
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

    public List<ChannelInfo> queryByStyle(String style){
        Cursor cursor = null;
        List<ChannelInfo> list  = new ArrayList<>();
        try {
            cursor = sqLiteDatabase.query(SQLHelper.TABLE_NAME ,null , "style=?" , new String []{style} , null,null,"name");
            while(cursor.moveToNext()){
                ChannelInfo channelInfo = new ChannelInfo();
                channelInfo.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                channelInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
                channelInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                channelInfo.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
                channelInfo.setType(cursor.getString(cursor.getColumnIndex("type")));
                channelInfo.setCountry(cursor.getString(cursor.getColumnIndex("country")));
                channelInfo.setSequence(cursor.getInt(cursor.getColumnIndex("sequence")));
                channelInfo.setStyle(cursor.getString(cursor.getColumnIndex("style")));
                channelInfo.setFavorite(cursor.getString(cursor.getColumnIndex("favorite")));
                list.add(channelInfo);
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

    public List<ChannelInfo> queryFavorite(){
        Cursor cursor = null;
        List<ChannelInfo> list  = new ArrayList<>();
        try {
            cursor = sqLiteDatabase.query(SQLHelper.TABLE_NAME ,null , "favorite=?" , new String []{"true"} , null,null,"name");
            while(cursor.moveToNext()){
                ChannelInfo channelInfo = new ChannelInfo();
                channelInfo.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                channelInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
                channelInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                channelInfo.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
                channelInfo.setType(cursor.getString(cursor.getColumnIndex("type")));
                channelInfo.setCountry(cursor.getString(cursor.getColumnIndex("country")));
                channelInfo.setSequence(cursor.getInt(cursor.getColumnIndex("sequence")));
                channelInfo.setStyle(cursor.getString(cursor.getColumnIndex("style")));
                channelInfo.setFavorite(cursor.getString(cursor.getColumnIndex("favorite")));
                list.add(channelInfo);
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

    public ChannelInfo query(String name , String url){
        Cursor cursor = null;
        ChannelInfo channelInfo = new ChannelInfo();
        try {
            cursor = sqLiteDatabase.query(SQLHelper.TABLE_NAME ,null , "name=? and url=?" , new String []{name , url} , null,null,"name");
            while(cursor.moveToNext()) {
                channelInfo.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                channelInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
                channelInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                channelInfo.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
                channelInfo.setType(cursor.getString(cursor.getColumnIndex("type")));
                channelInfo.setCountry(cursor.getString(cursor.getColumnIndex("country")));
                channelInfo.setSequence(cursor.getInt(cursor.getColumnIndex("sequence")));
                channelInfo.setStyle(cursor.getString(cursor.getColumnIndex("style")));
                channelInfo.setFavorite(cursor.getString(cursor.getColumnIndex("favorite")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor != null){
                cursor.close();
                cursor = null;
            }
        }
        return channelInfo;
    }

}
