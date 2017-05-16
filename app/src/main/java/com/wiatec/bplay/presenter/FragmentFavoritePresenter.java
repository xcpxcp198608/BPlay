package com.wiatec.bplay.presenter;

import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.data.IChannelData;
import com.wiatec.bplay.fragment.IFragment;
import com.wiatec.bplay.utils.Logger;

import java.util.List;

/**
 * Created by patrick on 2017/4/19.
 */

public class FragmentFavoritePresenter extends BasePresenter<IFragment> {

    private IFragment iFragment;

    public FragmentFavoritePresenter(IFragment iFragment) {
        this.iFragment = iFragment;
    }

    public void loadFavoriteChannel(){
        if(iChannelData != null){
            iChannelData.loadFavorite(new IChannelData.OnLoadListener() {
                @Override
                public void onSuccess(List<ChannelInfo> list, boolean finished) {
                    iFragment.loadChannel(list);
                }

                @Override
                public void onFailure(String e) {
                    Logger.d(e);
                }
            });
        }
    }
}
