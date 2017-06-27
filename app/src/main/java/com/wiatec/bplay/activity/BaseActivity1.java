package com.wiatec.bplay.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.wiatec.bplay.data.UserContentResolver;
import com.wiatec.bplay.presenter.BasePresenter;
import com.wiatec.bplay.utils.Logger;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by patrick on 2017/1/14.
 */

public abstract class BaseActivity1 <V , T extends BasePresenter>  extends AppCompatActivity{

    protected Subscription keyEventSubscription;

    protected  T presenter;
    protected abstract T createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        presenter = createPresenter();
        presenter.attachView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        keyEventMonitor();
    }

    private void keyEventMonitor(){
        Logger.d("start monitor");
        String level = UserContentResolver.get("userLevel");
        int userLevel;
        try {
            userLevel = Integer.parseInt(level);
        }catch (Exception e){
            userLevel = 1;
        }
        if(userLevel <=1){
            keyEventSubscription = Observable.timer(1200 , TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            startActivity(new Intent(BaseActivity1.this , DreamActivity.class));
                        }
                    });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyEventSubscription != null){
            keyEventSubscription.unsubscribe();
        }
//        Logger.d("stop monitor");
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        keyEventMonitor();
//        Toast.makeText(MainActivity.this , "enter dream after 5 minutes " ,Toast.LENGTH_LONG).show();
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(keyEventSubscription != null){
            keyEventSubscription.unsubscribe();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(keyEventSubscription != null){
            keyEventSubscription.unsubscribe();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        if(keyEventSubscription != null){
            keyEventSubscription.unsubscribe();
        }
    }

}
