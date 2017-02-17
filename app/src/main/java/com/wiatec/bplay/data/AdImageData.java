package com.wiatec.bplay.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wiatec.bplay.F;
import com.wiatec.bplay.activity.IAdActivity;
import com.wiatec.bplay.beans.ImageInfo;
import com.wiatec.bplay.utils.Logger;
import com.wiatec.bplay.utils.OkHttp.Listener.StringListener;
import com.wiatec.bplay.utils.OkHttp.OkMaster;

import java.io.IOException;

/**
 * Created by patrick on 2017/2/14.
 */

public class AdImageData implements IAdImageData {
    @Override
    public void load(final OnLoadListener onLoadListener) {
        OkMaster.get(F.url.ad_image)
                .enqueue(new StringListener() {
                    @Override
                    public void onSuccess(String s) throws IOException {
                        if(s ==null){
                            return;
                        }
                        ImageInfo imageInfo = new Gson().fromJson(s,new TypeToken<ImageInfo>(){}.getType());
                        onLoadListener.onSuccess(imageInfo);
                    }

                    @Override
                    public void onFailure(String e) {
                        Logger.d(e);
                    }
                });
    }
}
