package com.wiatec.bplay.fragment;

import com.wiatec.bplay.beans.ChannelInfo;

import java.util.List;

/**
 * Created by patrick on 2017/4/19.
 */

public interface IFragment {
    void loadChannel(List<ChannelInfo>list);
}
