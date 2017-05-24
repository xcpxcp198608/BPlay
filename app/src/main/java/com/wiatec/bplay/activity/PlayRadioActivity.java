package com.wiatec.bplay.activity;

import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.wiatec.bplay.Application;
import com.wiatec.bplay.R;
import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.databinding.ActivityPlayRadioBinding;
import com.wiatec.bplay.utils.AESUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by patrick on 2017/2/17.
 */

public class PlayRadioActivity extends AppCompatActivity {

    private ActivityPlayRadioBinding binding;
    private MediaPlayer mediaPlayer;
    private ChannelInfo channelInfo;
    private Subscription subscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play_radio);
        channelInfo = getIntent().getParcelableExtra("channelInfo");
        if(channelInfo == null){
            return;
        }
        channelInfo.setUrl(AESUtil.decrypt(channelInfo.getUrl() , AESUtil.key));
        if(mediaPlayer == null){
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.reset();
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            mediaPlayer.setDataSource(channelInfo.getUrl());
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    voiceViewStart();
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mediaPlayer.reset();
                    Toast.makeText(PlayRadioActivity.this,getString(R.string.error_play),Toast.LENGTH_LONG).show();
                    return false;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void voiceViewStart(){
        subscription = Observable.interval(0,200, TimeUnit.MILLISECONDS)
                .repeat()
                .map(new Func1<Long, Object>() {
                    @Override
                    public Object call(Long aLong) {
                        binding.voiceSpectrumView.start();
                        return null;
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {

                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer!= null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer =null;
        }
        if(subscription != null){
            subscription.unsubscribe();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!= null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer =null;
        }
        if(subscription != null){
            subscription.unsubscribe();
        }
    }
}
