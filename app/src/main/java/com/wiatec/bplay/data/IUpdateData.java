package com.wiatec.bplay.data;

import com.wiatec.bplay.beans.UpdateInfo;

/**
 * Created by patrick on 2017/1/13.
 */

public interface IUpdateData {
    void load(OnLoadListener onLoadListener);
    interface OnLoadListener{
        void onSuccess(UpdateInfo updateInfo);
        void onFailure(String e);
    }
}
