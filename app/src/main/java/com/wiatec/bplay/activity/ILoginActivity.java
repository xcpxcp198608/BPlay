package com.wiatec.bplay.activity;

import com.wiatec.bplay.beans.Channel;
import com.wiatec.bplay.beans.Result;

import java.util.List;

/**
 * Created by patrick on 2017/2/9.
 */

public interface ILoginActivity {
    void login (boolean loginSuccess , Result result);
    void loadChannel(List<Channel> list , boolean finished);
}
