package com.wiatec.bplay.presenter;

import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.data.IChannelData;
import com.wiatec.bplay.fragment.IFragmentNews;
import com.wiatec.bplay.utils.Logger;

import java.util.List;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentNewsPresenter extends BasePresenter<IFragmentNews> {
    private IFragmentNews iFragmentNews;

    public FragmentNewsPresenter(IFragmentNews iFragmentNews) {
        this.iFragmentNews = iFragmentNews;
    }

    public void loadChannelNews(String style){
        if(iChannelData != null){
            iChannelData.loadByStyle(style, new IChannelData.OnLoadListener() {
                @Override
                public void onSuccess(List<ChannelInfo> list, boolean finished) {
                    iFragmentNews.loadChannelNews(list);
                }

                @Override
                public void onFailure(String e) {
                    Logger.d(e);
                }
            });
        }
    }


}
