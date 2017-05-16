package com.wiatec.bplay.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.wiatec.bplay.service.task.CheckLogin;
import com.wiatec.bplay.utils.Logger;

/**
 * Created by patrick on 2017/1/13.
 */

public class CheckLoginService extends Service {

    private CheckLogin checkLogin;
    private MyBinder myBinder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        checkLogin = new CheckLogin();
        new Thread(checkLogin).start();
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if(checkLogin != null){

        }
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder {
        public CheckLoginService getService(){
            return CheckLoginService.this;
        }
    }

}
