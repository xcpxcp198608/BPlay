package com.wiatec.bplay;

import android.content.Context;

import com.wiatec.bplay.utils.Logger;

import java.net.Authenticator;

/**
 * Created by patrick on 2017/1/13.
 */

public class Application extends android.app.Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init("----px----");
        context = getApplicationContext();
//        setHttpProxy();
    }

    public static Context getContext(){
         return  context;
    }

    /**
     * 设置系统的网络代理
     */
    private void setHttpProxy(){
        System.setProperty("http.proxyHost","btvota.gobeyondtv.co");
        System.setProperty("http.proxyPort","1723");
    }

}
