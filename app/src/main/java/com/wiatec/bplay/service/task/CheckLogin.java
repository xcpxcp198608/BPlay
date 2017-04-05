package com.wiatec.bplay.service.task;

import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wiatec.bplay.Application;
import com.wiatec.bplay.F;
import com.wiatec.bplay.beans.Result;
import com.wiatec.bplay.rx_event.RepeatLogin;
import com.wiatec.bplay.sql.ChannelDao;
import com.wiatec.bplay.utils.Logger;
import com.wiatec.bplay.utils.OkHttp.Listener.StringListener;
import com.wiatec.bplay.utils.OkHttp.OkMaster;
import com.wiatec.bplay.utils.RxBus.RxBus;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by patrick on 2017/2/17.
 */

public class CheckLogin implements Runnable {

    private String userName;
    private String count;
    public Subscription subscription;

    public CheckLogin(String userName,int count) {
        this.userName = userName;
        this.count = String.valueOf(count);
    }

    @Override
    public void run() {
        subscription = Observable
                .interval(0,10000, TimeUnit.MILLISECONDS)
                .repeat()
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        OkMaster.post(F.url.check_login)
                                .parames("count" ,count)
                                .parames("username",userName)
                                .enqueue(new StringListener() {
                                    @Override
                                    public void onSuccess(String s) throws IOException {
                                        if(s == null){
                                            return;
                                        }
                                        Result result = new Gson().fromJson(s,new TypeToken<Result>(){}.getType());
                                        Logger.d(result.toString());
                                        if(result.getCode() != 0){
                                            Intent intent = new Intent();
                                            intent.setAction("com.wiatec.bplay.LOGIN_REPEAT");
                                            RxBus.getDefault().post(new RepeatLogin(1));
                                        }else {

                                        }
                                    }

                                    @Override
                                    public void onFailure(String e) {
                                        Logger.d(e);
                                    }
                                });
                    }
                });
    }
}
