package com.wiatec.bplay.presenter;

import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.data.IChannelData;
import com.wiatec.bplay.fragment.IFragmentMovies;
import com.wiatec.bplay.utils.Logger;

import java.util.List;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentMoviesPresenter extends BasePresenter<IFragmentMovies> {
    private IFragmentMovies iFragmentMovies ;

    public FragmentMoviesPresenter(IFragmentMovies iFragmentMovies) {
        this.iFragmentMovies = iFragmentMovies;
    }

    public void loadChannelMovies(String style){
        if(iChannelData != null){
            iChannelData.loadByStyle(style, new IChannelData.OnLoadListener() {
                @Override
                public void onSuccess(List<ChannelInfo> list, boolean finished) {
                    iFragmentMovies.loadChannelMovies(list);
                }

                @Override
                public void onFailure(String e) {
                    Logger.d(e);
                }
            });
        }
    }
}
