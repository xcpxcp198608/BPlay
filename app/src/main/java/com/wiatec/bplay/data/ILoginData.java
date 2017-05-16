package com.wiatec.bplay.data;

import com.wiatec.bplay.beans.Result;

/**
 * Created by patrick on 2017/2/9.
 */

public interface ILoginData {
    void login(String userName ,String password ,OnLoadListener onLoadListener);
    interface OnLoadListener{
        void onSuccess(Result result);
        void onFailure(String e);
    }
}
