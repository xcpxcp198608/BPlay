package com.wiatec.bplay.data;


import com.wiatec.bplay.beans.ChannelInfo;

import java.util.List;

/**
 * Created by patrick on 2017/1/13.
 */

public interface IChannelData {
    void load(OnLoadListener onLoadListener);
    void loadByCountry(String country , OnLoadListener onLoadListener);
    void loadByStyle(String style , OnLoadListener onLoadListener);
    void loadFavorite(OnLoadListener onLoadListener);
    interface OnLoadListener {
        void onSuccess(List<ChannelInfo> list , boolean finished);
        void onFailure(String e);
    }
}
