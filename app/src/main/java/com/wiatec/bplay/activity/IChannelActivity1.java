package com.wiatec.bplay.activity;

import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.beans.ChannelType;

import java.util.List;

/**
 * Created by patrick on 18/06/2017.
 * create time : 2:18 PM
 */

public interface IChannelActivity1 {

    void loadChannelType(List<ChannelType> list);
    void loadChannel(List<ChannelInfo> list , boolean finished);
    void loadFavoriteChannel(List<ChannelInfo> list , boolean finished);
}
