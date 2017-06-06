package com.wiatec.bplay;

import android.content.Context;

import com.wiatec.bplay.exception.CrashHandler;
import com.wiatec.bplay.utils.Logger;

import java.net.Authenticator;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by patrick on 2017/1/13.
 */

public class Application extends android.app.Application {

    private static Context context;
    private CrashHandler crashHandler;
    private static ThreadPoolExecutor threadPoolExecutor;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init("----px----");
        context = getApplicationContext();
        crashHandler = CrashHandler.getInstance();
        crashHandler.init(context);
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    public static Context getContext(){
         return  context;
    }

    public static ThreadPoolExecutor getThreadPoolExecutor(){
        return threadPoolExecutor;
    }
}
