package com.wiatec.bplay.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.wiatec.bplay.Application;
import com.wiatec.bplay.F;
import com.wiatec.bplay.R;
import com.wiatec.bplay.data.UserContentResolver;
import com.wiatec.bplay.utils.AppUtils;
import com.wiatec.bplay.utils.SPUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * play mp4 ad
 */

public class PlayAdActivity extends AppCompatActivity implements View.OnClickListener{

    private VideoView vv_PlayAd;
    private int time ;
    private LinearLayout llDelay;
    private TextView tvDelayTime ,tvTime;
    private Button btSkip;
    private Subscription subscription;
    private static final int SKIP_TIME = 15;
    private int userLevel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_play_ad);
        vv_PlayAd = (VideoView) findViewById(R.id.vv_play_ad);
        llDelay = (LinearLayout) findViewById(R.id.ll_delay);
        tvDelayTime = (TextView) findViewById(R.id.tv_delay_time);
        btSkip = (Button) findViewById(R.id.bt_skip);
        tvTime = (TextView) findViewById(R.id.tv_time);
        String videoTime = UserContentResolver.get("adTime");
        try {
            time = Integer.parseInt(videoTime);
        }catch (Exception e){
            time = 0;
        }
        vv_PlayAd.setVideoPath(F.path.ad_video);
        vv_PlayAd.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                vv_PlayAd.start();
            }
        });
        vv_PlayAd.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                skipAds();
                return true;
            }
        });
        vv_PlayAd.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                llDelay.setVisibility(View.GONE);
                skipAds();
            }
        });
        btSkip.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String level = UserContentResolver.get("userLevel");
        try {
            userLevel = Integer.parseInt(level);
        }catch (Exception e){
            userLevel = 1;
        }
        if(userLevel >= 3){
            skipAds();
        }
        if(time >0){
            llDelay.setVisibility(View.VISIBLE);
            subscription = Observable.interval(0,1, TimeUnit.SECONDS).take(time)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            int i = (int) (time -1 -aLong);
                            tvDelayTime.setText(i +" s");
                            if(userLevel == 2){
                                int j = (int) (SKIP_TIME -aLong);
                                if(j <0){
                                    j = 0;
                                }
                                tvTime.setText(" "+j + "s");
                                if(time - i > SKIP_TIME){
                                    btSkip.setVisibility(View.VISIBLE);
                                    btSkip.requestFocus();
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_skip:
                skipAds();
                break;
        }
    }

    public void skipAds(){
        release();
        startActivity(new Intent(PlayAdActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
    }

    private void release(){
        if(vv_PlayAd != null ){
            vv_PlayAd.stopPlayback();
        }
        if(subscription != null){
            subscription.unsubscribe();
        }
    }
}
