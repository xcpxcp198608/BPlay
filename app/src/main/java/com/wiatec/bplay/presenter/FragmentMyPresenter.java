package com.wiatec.bplay.presenter;

import com.wiatec.bplay.beans.Channel;
import com.wiatec.bplay.data.ChannelData;
import com.wiatec.bplay.data.IChannelData;
import com.wiatec.bplay.fragment.IFragmentMy;
import com.wiatec.bplay.utils.Logger;

import java.util.List;

/**
 * Created by patrick on 2017/2/13.
 */

public class FragmentMyPresenter extends BasePresenter<IFragmentMy> {

    private IFragmentMy iFragmentMy;
    private IChannelData iChannelData;

    public FragmentMyPresenter(IFragmentMy iFragmentMy) {
        this.iFragmentMy = iFragmentMy;
        iChannelData = new ChannelData();
    }

    public void loadFavoriteChannel(){
        if(iChannelData != null){
            iChannelData.showFavorite(new IChannelData.OnLoadListener() {
                @Override
                public void onSuccess(List<Channel> list, boolean finished) {
                    iFragmentMy.loadFavoriteChannel(list);
                }

                @Override
                public void onFailure(String e) {
                    Logger.d(e);
                }
            });
        }
    }
}
