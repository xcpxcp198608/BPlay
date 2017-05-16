package com.wiatec.bplay.presenter;

import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.data.IChannelData;
import com.wiatec.bplay.fragment.IFragmentMy;
import com.wiatec.bplay.utils.Logger;

import java.util.List;

/**
 * Created by patrick on 2017/2/13.
 */

public class FragmentMyPresenter extends BasePresenter<IFragmentMy> {

    private IFragmentMy iFragmentMy;


    public FragmentMyPresenter(IFragmentMy iFragmentMy) {
        this.iFragmentMy = iFragmentMy;
    }

    public void loadFavoriteChannel(){
        if(iChannelData != null){
            iChannelData.loadFavorite(new IChannelData.OnLoadListener() {
                @Override
                public void onSuccess(List<ChannelInfo> list, boolean finished) {
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
