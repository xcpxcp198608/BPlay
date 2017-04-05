package com.wiatec.bplay.presenter;

import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.data.IChannelData;
import com.wiatec.bplay.fragment.IFragmentMusic;
import com.wiatec.bplay.utils.Logger;

import java.util.List;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentMusicPresenter extends BasePresenter<IFragmentMusic> {
    private IFragmentMusic iFragmentMusic;

    public FragmentMusicPresenter(IFragmentMusic iFragmentMusic) {
        this.iFragmentMusic = iFragmentMusic;
    }

    public void loadChannelMusic(String style){
        if(iChannelData!=null){
            iChannelData.showByStyle(style, new IChannelData.OnLoadListener() {
                @Override
                public void onSuccess(List<ChannelInfo> list, boolean finished) {
                    iFragmentMusic.loadChannelMusic(list);
                }

                @Override
                public void onFailure(String e) {
                    Logger.d(e);
                }
            });
        }
    }
}
