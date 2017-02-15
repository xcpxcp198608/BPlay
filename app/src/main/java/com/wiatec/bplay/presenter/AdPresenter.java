package com.wiatec.bplay.presenter;

import com.wiatec.bplay.activity.IAdActivity;
import com.wiatec.bplay.beans.ImageInfo;
import com.wiatec.bplay.data.AdImageData;
import com.wiatec.bplay.data.IAdImageData;

/**
 * Created by patrick on 2017/2/14.
 */

public class AdPresenter extends BasePresenter<IAdActivity> {
    private IAdActivity iAdActivity;
    private IAdImageData iAdImageData;

    public AdPresenter(IAdActivity iAdActivity) {
        this.iAdActivity = iAdActivity;
        iAdImageData = new AdImageData();
    }

    public void loadAdImage(){
        if(iAdImageData != null){
            iAdImageData.load(new IAdImageData.OnLoadListener() {
                @Override
                public void onSuccess(ImageInfo imageInfo) {
                    iAdActivity.loadAd(imageInfo);
                }

                @Override
                public void onFailure(String e) {

                }
            });
        }
    }

}
