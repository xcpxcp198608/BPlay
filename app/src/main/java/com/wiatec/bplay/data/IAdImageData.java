package com.wiatec.bplay.data;

import com.wiatec.bplay.beans.ImageInfo;

/**
 * Created by patrick on 2017/2/14.
 */

public interface IAdImageData {
    void load(OnLoadListener onLoadListener);
    interface OnLoadListener{
        void onSuccess(ImageInfo imageInfo);
        void onFailure(String e);
    }
}
