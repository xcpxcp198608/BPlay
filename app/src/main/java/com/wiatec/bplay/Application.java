package com.wiatec.bplay;

import android.content.Context;

import com.wiatec.bplay.exception.CrashHandler;
import com.wiatec.bplay.utils.Logger;

import java.net.Authenticator;

/**
 * Created by patrick on 2017/1/13.
 */

public class Application extends android.app.Application {

    private static Context context;
    private CrashHandler crashHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init("----px----");
        context = getApplicationContext();
        crashHandler = CrashHandler.getInstance();
        crashHandler.init(context);
    }

    public static Context getContext(){
         return  context;
    }


}
