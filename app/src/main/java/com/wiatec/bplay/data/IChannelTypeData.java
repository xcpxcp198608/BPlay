package com.wiatec.bplay.data;

import com.wiatec.bplay.beans.ChannelType;

import java.util.List;

/**
 * Created by patrick on 2017/2/13.
 */

public interface IChannelTypeData {
    void load(String token , OnLoadListener onLoadListener);
    interface OnLoadListener {
        void onSuccess(List<ChannelType> list);
        void onFailure(String e);
    }
}
