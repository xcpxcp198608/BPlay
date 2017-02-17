package com.wiatec.bplay.presenter;

import com.wiatec.bplay.beans.Channel;
import com.wiatec.bplay.data.ChannelData;
import com.wiatec.bplay.data.IChannelData;
import com.wiatec.bplay.fragment.IFragmentSports;
import com.wiatec.bplay.utils.Logger;

import java.util.List;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentSportsPresenter extends BasePresenter<IFragmentSports> {
    private IFragmentSports iFragmentSports;
    private IChannelData iChannelData;

    public FragmentSportsPresenter(IFragmentSports iFragmentSports) {
        this.iFragmentSports = iFragmentSports;
        iChannelData = new ChannelData();
    }

    public void loadChannelSports(String style){
        if(iChannelData!=null){
            iChannelData.showByStyle(style, new IChannelData.OnLoadListener() {
                @Override
                public void onSuccess(List<Channel> list, boolean finished) {
                    iFragmentSports.loadChannelSports(list);
                }

                @Override
                public void onFailure(String e) {
                    Logger.d(e);
                }
            });
        }
    }
}
