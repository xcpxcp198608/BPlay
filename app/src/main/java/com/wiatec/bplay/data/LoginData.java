package com.wiatec.bplay.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wiatec.bplay.F;
import com.wiatec.bplay.beans.Result;
import com.wiatec.bplay.utils.Logger;
import com.wiatec.bplay.utils.OkHttp.Listener.StringListener;
import com.wiatec.bplay.utils.OkHttp.OkMaster;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by patrick on 2017/2/9.
 */

public class LoginData implements ILoginData {
    @Override
    public void login(String userName, String password, final OnLoadListener onLoadListener) {
        OkMaster.post(F.url.login)
                .parames("username",userName)
                .parames("password",password)
                .enqueue(new StringListener() {
                    @Override
                    public void onSuccess(String s) throws IOException {
                        if(s == null){
                            return;
                        }
                        Result result = new Gson().fromJson(s , new TypeToken<Result>(){}.getType());
                        boolean isSuccess = result.getCode() == 0;
                        onLoadListener.onSuccess(isSuccess ,result);
                        if(!isSuccess){
                            onLoadListener.onFailure(result.getStatus());
                        }
                    }

                    @Override
                    public void onFailure(String e) {
                        onLoadListener.onFailure(e);
                    }
                });
    }

    @Override
    public void register(String userName, String password, String email, final OnLoadListener onLoadListener) {
        OkMaster.post(F.url.register)
                .parames("username",userName)
                .parames("password",password)
                .parames("email" ,email)
                .enqueue(new StringListener() {
                    @Override
                    public void onSuccess(String s) throws IOException {
                        if(s==null){
                            return;
                        }
                        Result result = new Gson().fromJson(s , new TypeToken<Result>(){}.getType());
                        boolean isSuccess = result.getCode() == 8;
                        Logger.d(result.toString());
                        Logger.d(isSuccess+"");
                        onLoadListener.onSuccess(isSuccess ,result);
                        if(!isSuccess){
                            onLoadListener.onFailure(result.getStatus());
                        }
                    }

                    @Override
                    public void onFailure(String e) {
                        Logger.d(e);
                    }
                });

    }
}
