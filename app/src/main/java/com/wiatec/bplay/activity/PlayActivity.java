package com.wiatec.bplay.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wiatec.bplay.R;
import com.wiatec.bplay.beans.Channel;
import com.wiatec.bplay.sql.ChannelDao;

import java.io.IOException;

/**
 * Created by patrick on 2017/1/13.
 */

public class PlayActivity extends AppCompatActivity implements SurfaceHolder.Callback , View.OnClickListener,CompoundButton.OnCheckedChangeListener {

    private MediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private ProgressBar progressBar;
    private Channel channel;
    private ChannelDao channelDao;
    private FrameLayout flPlay;
    private LinearLayout llController;
    private CheckBox cbFavorite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        flPlay = (FrameLayout) findViewById(R.id.fl_play);
        llController = (LinearLayout) findViewById(R.id.ll_controller);
        cbFavorite = (CheckBox) findViewById(R.id.cb_favorite);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.addCallback(this);

        channel = getIntent().getParcelableExtra("channel");
        if(channel == null){
            return;
        }
        if("true".equals(channel.getFavorite())){
            cbFavorite.setChecked(true);
        }
        channelDao = ChannelDao.getInstance(this);
        flPlay.setOnClickListener(this);
        cbFavorite.setOnCheckedChangeListener(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        play(channel.getUrl());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void play(String url){
        if(mediaPlayer == null){
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.setDisplay(surfaceHolder);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    progressBar.setVisibility(View.GONE);
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mediaPlayer.reset();
                    Toast.makeText(PlayActivity.this,getString(R.string.error_play),Toast.LENGTH_LONG).show();
                    return false;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fl_play:
                if(llController.getVisibility() == View.VISIBLE){
                    llController.setVisibility(View.GONE);
                }else{
                    llController.setVisibility(View.VISIBLE);
                    cbFavorite.requestFocus();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.cb_favorite:
                if(isChecked){
                    channel.setFavorite("true");
                    if(channelDao.setFavorite(channel)){
                        cbFavorite.setChecked(true);
                        Toast.makeText(this,channel.getName()+getString(R.string.add_favorite) ,Toast.LENGTH_SHORT).show();
                    }
                }else{
                    channel.setFavorite("false");
                    if(channelDao.setFavorite(channel)) {
                        Toast.makeText(this, channel.getName() + getString(R.string.remove_favorite), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            if(llController.getVisibility() == View.VISIBLE){
                llController.setVisibility(View.GONE);
                return true;
            }else{
                return super.onKeyDown(keyCode , event);
            }
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
