package com.wiatec.bplay.activity;

import com.wiatec.bplay.beans.Channel;
import com.wiatec.bplay.beans.UpdateInfo;

import java.util.List;

/**
 * Created by patrick on 2017/1/13.
 */

public interface ISplashActivity {
    void checkUpdate(UpdateInfo updateInfo);
    void checkToken(boolean tokenValid);
    void loadChannel(List<Channel> list , boolean finished);
}
