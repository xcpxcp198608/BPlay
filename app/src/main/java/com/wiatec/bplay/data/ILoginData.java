package com.wiatec.bplay.data;

import com.wiatec.bplay.beans.Result;

/**
 * Created by patrick on 2017/2/9.
 */

public interface ILoginData {
    void login(String userName ,String password ,OnLoadListener onLoadListener);
    void register(String userName ,String password ,String email,OnLoadListener onLoadListener);
    interface OnLoadListener{
        void onSuccess(boolean isSuccess , Result result);
        void onFailure(String e);
    }
}
