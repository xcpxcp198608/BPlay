package com.wiatec.bplay.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wiatec.bplay.F;
import com.wiatec.bplay.beans.Result;
import com.wiatec.bplay.beans.UpdateInfo;
import com.wiatec.bplay.utils.Logger;
import com.wiatec.bplay.utils.OkHttp.Listener.StringListener;
import com.wiatec.bplay.utils.OkHttp.OkMaster;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by patrick on 2017/1/13.
 */

public class UpdateData implements IUpdateData {
    @Override
    public void load(final OnLoadListener onLoadListener) {
        OkMaster.get(F.url.update).enqueue(new StringListener() {
            @Override
            public void onSuccess(String s) throws IOException {
                if(s == null){
                    return;
                }
                UpdateInfo updateInfo = new Gson().fromJson(s , new TypeToken<UpdateInfo>(){}.getType());
                if(updateInfo != null){
                    onLoadListener.onSuccess(updateInfo);
                }
            }

            @Override
            public void onFailure(String e) {
                Logger.d(e);
            }
        });

    }
}
