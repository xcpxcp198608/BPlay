package com.wiatec.bplay.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wiatec.bplay.F;
import com.wiatec.bplay.beans.Result;
import com.wiatec.bplay.utils.OkHttp.Listener.StringListener;
import com.wiatec.bplay.utils.OkHttp.OkMaster;

import java.io.IOException;

/**
 * Created by patrick on 2017/2/9.
 */

public class TokenData implements ITokenData {
    @Override
    public void load(String token, final OnLoadListener onLoadListener) {
        if(token == null){
            return;
        }
        OkMaster.post(F.url.check_token).parames("token" , token).enqueue(new StringListener() {
            @Override
            public void onSuccess(String s) throws IOException {
                if(s == null){
                    return;
                }
                Result result = new Gson().fromJson(s , new TypeToken<Result>(){}.getType());
                boolean tokenValid = result.getCode() == 0 ;
                if(tokenValid){
                    onLoadListener.onSuccess(true);
                }else{
                    onLoadListener.onSuccess(false);
                    onLoadListener.onFailure(result.getStatus());
                }
            }

            @Override
            public void onFailure(String e) {
                onLoadListener.onFailure(e);
            }
        });
    }
}
