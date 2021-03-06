package com.wiatec.bplay.service.task;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wiatec.bplay.Application;
import com.wiatec.bplay.F;
import com.wiatec.bplay.beans.Result;
import com.wiatec.bplay.rx_event.CheckLoginEvent;
import com.wiatec.bplay.utils.Logger;
import com.wiatec.bplay.utils.NetUtils;
import com.wiatec.bplay.utils.OkHttp.Listener.StringListener;
import com.wiatec.bplay.utils.OkHttp.OkMaster;
import com.wiatec.bplay.utils.RxBus.RxBus;
import com.wiatec.bplay.utils.SPUtils;

import java.io.IOException;

/**
 * Created by patrick on 2017/3/14.
 */

public class CheckLogin implements Runnable {

    private  int currentLoginCount;
    private  String userName;
    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            check();
        }
    }

    public void check(){
        if(!NetUtils.isConnected(Application.getContext())){
            return;
        }
        currentLoginCount = (int) SPUtils.get(Application.getContext() , "currentLoginCount" , 0);
        userName = (String) SPUtils.get(Application.getContext(),"userName" , "");
        if(TextUtils.isEmpty(userName)){
            Logger.d("no userName do not execute check");
            return;
        }
        if(currentLoginCount == 0){
            Logger.d("login count error , does not execute check");
            return;
        }
        OkMaster.get(F.url.check_repeat)
                .parames("count", currentLoginCount+"")
                .parames("userInfo.userName",userName)
                .enqueue(new StringListener() {
                    @Override
                    public void onSuccess(String s) throws IOException {
                        if(s==null){
                            return;
                        }
                        Result result = new Gson().fromJson(s,new TypeToken<Result>(){}.getType());
                        if(result == null){
                            return;
                        }
//                        Logger.d(result.toString());
                        if(result.getCode() == Result.CODE_LOGIN_SUCCESS){
                            SPUtils.put(Application.getContext() , "userLevel" ,result.getUserLevel());
                            SPUtils.put(Application.getContext() , "experience" , result.getExtra());
                            RxBus.getDefault().post(new CheckLoginEvent(CheckLoginEvent.CODE_LOGIN_NORMAL));
                        }else{
                            RxBus.getDefault().post(new CheckLoginEvent(CheckLoginEvent.CODE_LOGIN_REPEAT));
                        }
                    }

                    @Override
                    public void onFailure(String e) {
                        Logger.d(e);
                    }
                });
    }
}
