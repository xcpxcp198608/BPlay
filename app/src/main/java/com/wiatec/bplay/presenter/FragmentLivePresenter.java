package com.wiatec.bplay.presenter;

import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.beans.ChannelType;
import com.wiatec.bplay.data.ChannelTypeData;
import com.wiatec.bplay.data.IChannelData;
import com.wiatec.bplay.data.IChannelTypeData;
import com.wiatec.bplay.fragment.IFragmentLive;

import java.util.List;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentLivePresenter extends BasePresenter<IFragmentLive> {
    private IFragmentLive iFragmentLive;
    private IChannelTypeData iChannelTypeData;


    public FragmentLivePresenter(IFragmentLive iFragmentLive) {
        this.iFragmentLive = iFragmentLive;
        iChannelTypeData = new ChannelTypeData();
    }

    public void loadChannelType(String token){
        if(iChannelTypeData != null){
            iChannelTypeData.load(token, new IChannelTypeData.OnLoadListener() {
                @Override
                public void onSuccess(List<ChannelType> list) {
                    iFragmentLive.loadChannelType(list);
                }

                @Override
                public void onFailure(String e) {

                }
            });
        }
    }

    public void loadChannelByCountry(String country){
        if(iChannelData != null){
            iChannelData.showByCountry(country, new IChannelData.OnLoadListener() {
                @Override
                public void onSuccess(List<ChannelInfo> list, boolean finished) {
                    iFragmentLive.loadChannel(list ,finished);
                }

                @Override
                public void onFailure(String e) {

                }
            });
        }
    }
}
