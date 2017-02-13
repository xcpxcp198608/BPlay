package com.wiatec.bplay;

import android.content.Context;

import com.wiatec.bplay.utils.Logger;

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
    }

    public static Context getContext(){
         return  context;
    }
}
