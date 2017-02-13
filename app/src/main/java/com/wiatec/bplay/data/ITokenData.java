package com.wiatec.bplay.data;

/**
 * Created by patrick on 2017/2/9.
 */

public interface ITokenData {
    void load(String token , OnLoadListener onLoadListener);
    interface OnLoadListener{
        void onSuccess(boolean tokenValid);
        void onFailure(String e);
    }
}
