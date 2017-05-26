package com.wiatec.bplay.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wiatec.bplay.Application;
import com.wiatec.bplay.F;
import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.sql.FavoriteDao;
import com.wiatec.bplay.utils.Logger;
import com.wiatec.bplay.utils.OkHttp.Listener.StringListener;
import com.wiatec.bplay.utils.OkHttp.OkMaster;

import java.io.IOException;
import java.text.Collator;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by patrick on 2017/1/13.
 */

public class ChannelData implements IChannelData{
    private FavoriteDao favoriteDao;

    public ChannelData() {
        favoriteDao = FavoriteDao.getInstance(Application.getContext());
    }

    @Override
    public void load(final OnLoadListener onLoadListener) {
        OkMaster.get(F.url.channel).enqueue(new StringListener() {
            @Override
            public void onSuccess(String s) throws IOException {
                if(s == null){
                    return;
                }
                List<ChannelInfo> list = new Gson().fromJson(s,new TypeToken<List<ChannelInfo>>(){}.getType());
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
    public void loadByCountry(String country, final OnLoadListener onLoadListener) {
        OkMaster.post(F.url.channel_country).parames("country" , country)
                .enqueue(new StringListener() {
                    @Override
                    public void onSuccess(String s) throws IOException {
                        if(s == null){
                            return;
                        }
                        List<ChannelInfo> list = new Gson().fromJson(s,new TypeToken<List<ChannelInfo>>(){}.getType());
                        onLoadListener.onSuccess(list , true);
                    }

                    @Override
                    public void onFailure(String e) {
                        Logger.d(e);
                        onLoadListener.onFailure(e);
                        onLoadListener.onSuccess(null , false);
                    }
                });
    }

    @Override
    public void loadByStyle(String style, final OnLoadListener onLoadListener) {
        OkMaster.post(F.url.channel_style).parames("style" , style)
                .enqueue(new StringListener() {
                    @Override
                    public void onSuccess(String s) throws IOException {
                        if(s == null){
                            return;
                        }
                        List<ChannelInfo> list = new Gson().fromJson(s,new TypeToken<List<ChannelInfo>>(){}.getType());
                        onLoadListener.onSuccess(list , true);
                    }

                    @Override
                    public void onFailure(String e) {
                        Logger.d(e);
                        onLoadListener.onFailure(e);
                        onLoadListener.onSuccess(null , false);
                    }
                });
    }

    @Override
    public void loadFavorite(final OnLoadListener onLoadListener) {
        try {
            Observable.just("1")
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<String, List<ChannelInfo>>() {
                        @Override
                        public List<ChannelInfo> call(String s) {
                            return favoriteDao.queryAll();
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<ChannelInfo>>() {
                        @Override
                        public void call(List<ChannelInfo> channelInfos) {
                            onLoadListener.onSuccess(channelInfos,true);
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
            Logger.d(e.getMessage());
            onLoadListener.onFailure(e.getMessage());
            onLoadListener.onSuccess(null , false);
        }
    }
}
