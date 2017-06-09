package com.wiatec.bplay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.wiatec.bplay.data.UserContentResolver;
import com.wiatec.bplay.utils.Logger;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 监听用户遥控按键动作
 * 界面完全显示后连续3分钟内无动作，跳转到广告页面
 * 用户按下按键取消监听
 * 按键抬起后继续重新开始监听
 */
public class BaseActivity2 extends AppCompatActivity{

    protected Subscription keyEventSubscription;

    @Override
    protected void onResume() {
        super.onResume();
        keyEventMonitor();
    }

    private void keyEventMonitor(){
//        Logger.d("start monitor");
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
                            startActivity(new Intent(BaseActivity2.this , DreamActivity.class));
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
        if(keyEventSubscription != null){
            keyEventSubscription.unsubscribe();
        }
    }
}
