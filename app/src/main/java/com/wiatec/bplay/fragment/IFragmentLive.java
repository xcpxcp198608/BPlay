package com.wiatec.bplay.fragment;

import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.beans.ChannelType;

import java.util.List;

/**
 * Created by patrick on 2017/2/10.
 */

public interface IFragmentLive {
    void loadChannelType(List<ChannelType> list);
    void loadChannel(List<ChannelInfo> list , boolean finished);
    void loadFavoriteChannel(List<ChannelInfo> list , boolean finished);
}
