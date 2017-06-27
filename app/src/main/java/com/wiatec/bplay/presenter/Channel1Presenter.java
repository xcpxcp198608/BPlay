package com.wiatec.bplay.presenter;

import com.wiatec.bplay.activity.IChannelActivity1;
import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.beans.ChannelType;
import com.wiatec.bplay.data.ChannelTypeData;
import com.wiatec.bplay.data.IChannelData;
import com.wiatec.bplay.data.IChannelTypeData;
import com.wiatec.bplay.fragment.IFragmentLive;

import java.util.List;

/**
 * Created by patrick on 18/06/2017.
 * create time : 2:19 PM
 */

public class Channel1Presenter extends BasePresenter<IChannelActivity1> {
    private IChannelActivity1 iChannelActivity1;
    private IChannelTypeData iChannelTypeData;


    public Channel1Presenter(IChannelActivity1 iChannelActivity1) {
        this.iChannelActivity1 = iChannelActivity1;
        iChannelTypeData = new ChannelTypeData();
    }

    public void loadChannelType(String token){
        if(iChannelTypeData != null){
            iChannelTypeData.load(token, new IChannelTypeData.OnLoadListener() {
                @Override
                public void onSuccess(List<ChannelType> list) {
                    if (iChannelActivity1 == null) return;
                    iChannelActivity1.loadChannelType(list);
                }

                @Override
                public void onFailure(String e) {

                }
            });
        }
    }

    public void loadChannelByCountry(String country){
        if(iChannelData != null){
            iChannelData.loadByCountry(country, new IChannelData.OnLoadListener() {
                @Override
                public void onSuccess(List<ChannelInfo> list, boolean finished) {
                    if (iChannelActivity1 == null) return;
                    iChannelActivity1.loadChannel(list ,finished);
                }

                @Override
                public void onFailure(String e) {

                }
            });
        }
    }

    public void loadFavoriteChannel(){
        if(iChannelData != null){
            iChannelData.loadFavorite(new IChannelData.OnLoadListener() {
                @Override
                public void onSuccess(List<ChannelInfo> list, boolean finished) {
                    if (iChannelActivity1 == null) return;
                    iChannelActivity1.loadFavoriteChannel(list ,finished);
                }

                @Override
                public void onFailure(String e) {

                }
            });
        }
    }
}
