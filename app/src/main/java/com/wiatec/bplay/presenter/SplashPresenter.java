package com.wiatec.bplay.presenter;

import com.wiatec.bplay.activity.ISplashActivity;
import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.beans.UpdateInfo;
import com.wiatec.bplay.data.ChannelData;
import com.wiatec.bplay.data.IChannelData;
import com.wiatec.bplay.data.ITokenData;
import com.wiatec.bplay.data.IUpdateData;
import com.wiatec.bplay.data.TokenData;
import com.wiatec.bplay.data.UpdateData;
import com.wiatec.bplay.utils.Logger;

import java.util.List;

/**
 * Created by patrick on 2017/1/13.
 */

public class SplashPresenter extends BasePresenter<ISplashActivity> {
    private ISplashActivity iSplashActivity;
    private IUpdateData iUpdateData;
    private IChannelData iChannelData;
    private ITokenData iTokenData;

    public SplashPresenter(ISplashActivity iSplashActivity) {
        this.iSplashActivity = iSplashActivity;
        iUpdateData = new UpdateData();
        iChannelData = new ChannelData();
        iTokenData = new TokenData();
    }

    public void checkUpdate(){
        if(iUpdateData != null){
            iUpdateData.load(new IUpdateData.OnLoadListener() {
                @Override
                public void onSuccess(UpdateInfo updateInfo) {
                    iSplashActivity.checkUpdate(updateInfo);
                }

                @Override
                public void onFailure(String e) {
                    Logger.d(e);
                }
            });
        }
    }

    public void checkToken (String token) {
        if(iTokenData != null){
            iTokenData.load(token, new ITokenData.OnLoadListener() {
                @Override
                public void onSuccess(boolean tokenValid) {
                    iSplashActivity.checkToken(tokenValid);
                }

                @Override
                public void onFailure(String e) {
                    Logger.d(e);
                }
            });
        }
    }

    public void loadChannel(String token){
        if(iChannelData != null){
            iChannelData.load(token ,new IChannelData.OnLoadListener() {
                @Override
                public void onSuccess(List<ChannelInfo> list , boolean finished) {
                    iSplashActivity.loadChannel(list ,finished);
                }

                @Override
                public void onFailure(String e) {
                    Logger.d(e);
                }
            });
        }
    }
}
