package com.wiatec.bplay.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wiatec.bplay.Application;
import com.wiatec.bplay.F;
import com.wiatec.bplay.beans.Channel;
import com.wiatec.bplay.sql.ChannelDao;
import com.wiatec.bplay.utils.Logger;
import com.wiatec.bplay.utils.OkHttp.Listener.StringListener;
import com.wiatec.bplay.utils.OkHttp.OkMaster;

import java.io.IOException;
import java.util.List;

/**
 * Created by patrick on 2017/1/13.
 */

public class ChannelData implements IChannelData{
    private ChannelDao channelDao;

    public ChannelData() {
        channelDao = ChannelDao.getInstance(Application.getContext());
    }

    @Override
    public void load(String token , final OnLoadListener onLoadListener) {
        OkMaster.post(F.url.channel).parames("token" ,token).enqueue(new StringListener() {
            @Override
            public void onSuccess(String s) throws IOException {
                if(s == null){
                    return;
                }
                List<Channel> list = new Gson().fromJson(s,new TypeToken<List<Channel>>(){}.getType());
//                Logger.d(list.toString());
                if(list != null && list.size()>0){
                    channelDao.multiInsert(list);
                }
                onLoadListener.onSuccess(list , true);
            }

            @Override
            public void onFailure(String e) {
                onLoadListener.onFailure(e);
                onLoadListener.onSuccess(null , false);
            }
        });
    }

    @Override
    public void showByCountry(String country, OnLoadListener onLoadListener) {
        try {
            List<Channel> list = channelDao.queryByCountry(country);
            if(list != null && list.size() >0){
                onLoadListener.onSuccess(list ,true);
            }
        }catch (Exception e){
            e.printStackTrace();
            onLoadListener.onFailure(e.getMessage());
        }
    }

    @Override
    public void showByStyle(String style, OnLoadListener onLoadListener) {
        try {
            List<Channel> list = channelDao.queryByStyle(style);
            if(list != null && list.size() >0){
                onLoadListener.onSuccess(list ,true);
            }
        }catch (Exception e){
            e.printStackTrace();
            onLoadListener.onFailure(e.getMessage());
        }
    }

    @Override
    public void showFavorite(OnLoadListener onLoadListener) {
        try {
            List<Channel> list = channelDao.queryFavorite();
            if(list != null && list.size() >0){
                onLoadListener.onSuccess(list ,true);
            }
        }catch (Exception e){
            e.printStackTrace();
            onLoadListener.onFailure(e.getMessage());
        }
    }
}
