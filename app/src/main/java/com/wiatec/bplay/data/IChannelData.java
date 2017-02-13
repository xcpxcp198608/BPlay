package com.wiatec.bplay.data;


import com.wiatec.bplay.beans.Channel;

import java.util.List;

/**
 * Created by patrick on 2017/1/13.
 */

public interface IChannelData {
    void load(String token , OnLoadListener onLoadListener);
    void showByCountry(String country , OnLoadListener onLoadListener);
    void showByStyle(String style , OnLoadListener onLoadListener);
    interface OnLoadListener {
        void onSuccess(List<Channel> list , boolean finished);
        void onFailure(String e);
    }
}
