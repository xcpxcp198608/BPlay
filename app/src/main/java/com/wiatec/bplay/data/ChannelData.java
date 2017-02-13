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
    @Override
    public void load(String token , final OnLoadListener onLoadListener) {
        OkMaster.post(F.url.channel).parames("token" ,token).enqueue(new StringListener() {
            @Override
            public void onSuccess(String s) throws IOException {
                if(s == null){
                    return;
                }
                List<Channel> list = new Gson().fromJson(s,new TypeToken<List<Channel>>(){}.getType());
                Logger.d(list.toString());
                ChannelDao channelDao = ChannelDao.getInstance(Application.getContext());
                channelDao.multiInsert(list);
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

    }

    @Override
    public void showByStyle(String style, OnLoadListener onLoadListener) {

    }
}
