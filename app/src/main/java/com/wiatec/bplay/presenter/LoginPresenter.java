package com.wiatec.bplay.presenter;

import com.wiatec.bplay.activity.ILoginActivity;
import com.wiatec.bplay.beans.Channel;
import com.wiatec.bplay.beans.Result;
import com.wiatec.bplay.data.ChannelData;
import com.wiatec.bplay.data.IChannelData;
import com.wiatec.bplay.data.ILoginData;
import com.wiatec.bplay.data.LoginData;
import com.wiatec.bplay.utils.Logger;

import java.util.List;

/**
 * Created by patrick on 2017/2/9.
 */

public class LoginPresenter extends BasePresenter<ILoginActivity> {
    private ILoginActivity iLoginActivity;
    private ILoginData iLoginData;
    private IChannelData iChannelData;

    public LoginPresenter(ILoginActivity iLoginActivity) {
        this.iLoginActivity = iLoginActivity;
        iLoginData = new LoginData();
        iChannelData = new ChannelData();
    }

    public void login(String userName , String password){
        if(iLoginData != null){
            iLoginData.login(userName, password, new ILoginData.OnLoadListener() {
                @Override
                public void onSuccess(boolean isSuccess , Result result) {
                    iLoginActivity.login(isSuccess ,result);
                }

                @Override
                public void onFailure(String e) {
                    Logger.d(e);
                }
            });
        }
    }

    public void loadChannel (String token){
        if(iChannelData != null){
            iChannelData.load(token, new IChannelData.OnLoadListener() {
                @Override
                public void onSuccess(List<Channel> list, boolean finished) {
                    iLoginActivity.loadChannel(list ,finished);
                }

                @Override
                public void onFailure(String e) {
                    Logger.d(e);
                }
            });
        }
    }
}
