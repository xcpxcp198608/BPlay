package com.wiatec.bplay.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wiatec.bplay.F;
import com.wiatec.bplay.beans.ChannelType;
import com.wiatec.bplay.utils.Logger;
import com.wiatec.bplay.utils.OkHttp.Listener.StringListener;
import com.wiatec.bplay.utils.OkHttp.OkMaster;

import java.io.IOException;
import java.util.List;

/**
 * Created by patrick on 2017/2/13.
 */

public class ChannelTypeData implements IChannelTypeData{
    @Override
    public void load(String token , final OnLoadListener onLoadListener) {
        OkMaster.post(F.url.channel_type).parames("token",token)
                .enqueue(new StringListener() {
                    @Override
                    public void onSuccess(String s) throws IOException {
                        if(s == null){
                            return;
                        }
                        List<ChannelType> list = new Gson().fromJson(s,new TypeToken<List<ChannelType>>(){}.getType());
                        if(list !=null && list.size()>0){
                            onLoadListener.onSuccess(list);
                        }
                    }

                    @Override
                    public void onFailure(String e) {
                        Logger.d(e);
                    }
                });
    }
}
