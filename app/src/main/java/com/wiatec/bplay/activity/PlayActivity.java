package com.wiatec.bplay.activity;

import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.wiatec.bplay.R;
import com.wiatec.bplay.databinding.ActivityPlayBinding;

import java.io.IOException;

/**
 * Created by patrick on 2017/1/13.
 */

public class PlayActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private MediaPlayer mediaPlayer;
    private SurfaceHolder surfaceHolder;
    private ActivityPlayBinding binding;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_play);
        surfaceHolder = binding.surfaceView.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.addCallback(this);
        url = getIntent().getStringExtra("url");
        if(url == null){
            return;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        play(url);
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
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    binding.progressBar.setVisibility(View.GONE);
                    mediaPlayer.start();
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
}
