package com.wiatec.bplay.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wiatec.bplay.Application;
import com.wiatec.bplay.F;
import com.wiatec.bplay.beans.Result;
import com.wiatec.bplay.utils.Logger;
import com.wiatec.bplay.utils.OkHttp.Listener.StringListener;
import com.wiatec.bplay.utils.OkHttp.OkMaster;
import com.wiatec.bplay.utils.SPUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by patrick on 2017/2/9.
 */

public class LoginData implements ILoginData {
    @Override
    public void login(String userName, String password , final OnLoadListener onLoadListener) {
        String mac = (String) SPUtils.get(Application.getContext() , "mac" ,""+System.currentTimeMillis());
        OkMaster.post(F.url.login)
                .parames("userInfo.userName",userName)
                .parames("userInfo.password",password)
                .parames("userInfo.level","10")
                .parames("deviceInfo.mac",mac)
                .enqueue(new StringListener() {
                    @Override
                    public void onSuccess(String s) throws IOException {
                        if(s == null){
                            return;
                        }
                        Result result = new Gson().fromJson(s , new TypeToken<Result>(){}.getType());
                        if(result == null){
                            return;
                        }
                        onLoadListener.onSuccess(result);
                    }

                    @Override
                    public void onFailure(String e) {
                        onLoadListener.onFailure(e);
                    }
                });
    }

}
