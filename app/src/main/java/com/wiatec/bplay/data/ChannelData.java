package com.wiatec.bplay.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wiatec.bplay.Application;
import com.wiatec.bplay.F;
import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.sql.ChannelDao;
import com.wiatec.bplay.utils.Logger;
import com.wiatec.bplay.utils.OkHttp.Listener.StringListener;
import com.wiatec.bplay.utils.OkHttp.OkMaster;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
                List<ChannelInfo> list = new Gson().fromJson(s,new TypeToken<List<ChannelInfo>>(){}.getType());
                //Logger.d(list.toString());
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
    public void showByCountry(String country, final OnLoadListener onLoadListener) {
        try {
            Observable.just(country)
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<String, List<ChannelInfo>>() {
                        @Override
                        public List<ChannelInfo> call(String s) {
                            return channelDao.queryByCountry(s);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<ChannelInfo>>() {
                        @Override
                        public void call(List<ChannelInfo> channelInfos) {
                            if(channelInfos != null && channelInfos.size() >0){
                                onLoadListener.onSuccess(channelInfos,true);
                            }
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
            onLoadListener.onFailure(e.getMessage());
        }
    }

    @Override
    public void showByStyle(String style, final OnLoadListener onLoadListener) {
        try {
            Observable.just(style)
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<String, List<ChannelInfo>>() {
                        @Override
                        public List<ChannelInfo> call(String s) {
                            return channelDao.queryByStyle(s);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<ChannelInfo>>() {
                        @Override
                        public void call(List<ChannelInfo> channelInfos) {
                            if(channelInfos != null && channelInfos.size() >0){
                                onLoadListener.onSuccess(channelInfos,true);
                            }
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
            onLoadListener.onFailure(e.getMessage());
        }
    }

    @Override
    public void showFavorite(final OnLoadListener onLoadListener) {
        try {
            Observable.just("1")
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<String, List<ChannelInfo>>() {
                        @Override
                        public List<ChannelInfo> call(String s) {
                            return channelDao.queryFavorite();
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<ChannelInfo>>() {
                        @Override
                        public void call(List<ChannelInfo> channelInfos) {
                            if(channelInfos != null && channelInfos.size() >0){
                                onLoadListener.onSuccess(channelInfos,true);
                            }
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
            onLoadListener.onFailure(e.getMessage());
        }
    }
}
