package com.wiatec.bplay.fragment;

import com.wiatec.bplay.beans.Channel;
import com.wiatec.bplay.beans.ChannelType;

import java.util.List;

/**
 * Created by patrick on 2017/2/10.
 */

public interface IFragmentLive {
    void loadChannelType(List<ChannelType> list);
    void loadChannel(List<Channel> list ,boolean finished);
}
