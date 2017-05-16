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

public class FavoriteDao {
    private SQLiteDatabase sqLiteDatabase;
    private FavoriteDao(Context context){
        sqLiteDatabase = new SQLHelper(context).getWritableDatabase();
    }

    private volatile static FavoriteDao instance;
    public static FavoriteDao getInstance(Context context){
        if(instance == null){
            synchronized (FavoriteDao.class){
                if(instance ==null){
                    instance = new FavoriteDao(context);
                }
            }
        }
        return instance;
    }

    public boolean isExists(ChannelInfo channelInfo){
        Cursor cursor = sqLiteDatabase.query(SQLHelper.TABLE_NAME ,null , "tag=?" ,
                new String []{channelInfo.getTag()} , null,null,null);
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
            contentValues.put("channelId", channelInfo.getChannelId());
            contentValues.put("sequence", channelInfo.getSequence());
            contentValues.put("tag", channelInfo.getTag());
            contentValues.put("name", channelInfo.getName());
            contentValues.put("url", channelInfo.getUrl());
            contentValues.put("icon", channelInfo.getIcon());
            contentValues.put("type", channelInfo.getType());
            contentValues.put("country", channelInfo.getCountry());
            contentValues.put("style", channelInfo.getStyle());
            contentValues.put("visible", channelInfo.getVisible());
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
            contentValues.put("channelId", channelInfo.getChannelId());
            contentValues.put("sequence", channelInfo.getSequence());
            contentValues.put("tag", channelInfo.getTag());
            contentValues.put("name", channelInfo.getName());
            contentValues.put("url", channelInfo.getUrl());
            contentValues.put("icon", channelInfo.getIcon());
            contentValues.put("type", channelInfo.getType());
            contentValues.put("country", channelInfo.getCountry());
            contentValues.put("style", channelInfo.getStyle());
            contentValues.put("visible", channelInfo.getVisible());
            sqLiteDatabase.update(SQLHelper.TABLE_NAME,contentValues , "tag=?" ,
                    new String []{channelInfo.getTag()});
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

    public boolean deleteByTag(ChannelInfo channelInfo){
        boolean flag = false;
        try {
            sqLiteDatabase.delete(SQLHelper.TABLE_NAME ,"tag=?" , new String[]{channelInfo.getTag()});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public List<ChannelInfo> queryAll(){
        Cursor cursor = null;
        List<ChannelInfo> channelInfoList = new ArrayList<>();
        try {
            cursor = sqLiteDatabase.query(SQLHelper.TABLE_NAME ,null , "_id>?" , new String []{"0"} , null,null,"name");
            while(cursor.moveToNext()){
                ChannelInfo channelInfo = new ChannelInfo();
                channelInfo.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                channelInfo.setChannelId(cursor.getInt(cursor.getColumnIndex("channelId")));
                channelInfo.setSequence(cursor.getInt(cursor.getColumnIndex("sequence")));
                channelInfo.setTag(cursor.getString(cursor.getColumnIndex("tag")));
                channelInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
                channelInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                channelInfo.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
                channelInfo.setType(cursor.getString(cursor.getColumnIndex("type")));
                channelInfo.setCountry(cursor.getString(cursor.getColumnIndex("country")));
                channelInfo.setStyle(cursor.getString(cursor.getColumnIndex("style")));
                channelInfo.setVisible(cursor.getInt(cursor.getColumnIndex("visible")));
                channelInfoList.add(channelInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor != null){
                cursor.close();
                cursor = null;
            }
        }
        return channelInfoList;
    }

}
