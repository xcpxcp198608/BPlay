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
import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.sql.FavoriteDao;
import com.wiatec.bplay.utils.AESUtil;
import com.wiatec.bplay.utils.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Created by patrick on 2017/1/13.
 */

public class PlayActivity extends AppCompatActivity implements SurfaceHolder.Callback , View.OnClickListener,CompoundButton.OnCheckedChangeListener {

    private MediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private ProgressBar progressBar;
    private ChannelInfo channelInfo;
    private FavoriteDao favoriteDao;
    private FrameLayout flPlay;
    private LinearLayout llController;
    private CheckBox cbFavorite;
    private List<ChannelInfo> channelInfoList;
    private int position;

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

        channelInfoList = (List<ChannelInfo>) getIntent().getSerializableExtra("channelInfoList");
        position = getIntent().getIntExtra("position" , 0);
        channelInfo = channelInfoList.get(position);
        if(channelInfo == null){
            return;
        }
        Logger.d(channelInfo.toString());
        favoriteDao = FavoriteDao.getInstance(this);
        if(favoriteDao.isExists(channelInfo)){
            cbFavorite.setChecked(true);
        }
        flPlay.setOnClickListener(this);
        cbFavorite.setOnCheckedChangeListener(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        play(AESUtil.decrypt(channelInfo.getUrl() , AESUtil.key));
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void play(String url){
        progressBar.setVisibility(View.VISIBLE);
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
                    Toast.makeText(PlayActivity.this,"now play "+channelInfo.getName() ,Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mediaPlayer.reset();
                    progressBar.setVisibility(View.GONE);
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
                    if(favoriteDao.insert(channelInfo)){
                        cbFavorite.setChecked(true);
                        Toast.makeText(this, channelInfo.getName()+getString(R.string.add_favorite) ,Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(favoriteDao.deleteByTag(channelInfo)) {
                        Toast.makeText(this, channelInfo.getName() + getString(R.string.remove_favorite), Toast.LENGTH_SHORT).show();
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
            }
        }
        if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT){
            position -- ;
            if(position <= 0){
                position = channelInfoList.size();
            }
            channelInfo = channelInfoList.get(position);
            play(AESUtil.decrypt(channelInfo.getUrl() , AESUtil.key));
        }
        if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT){
            position ++ ;
            if(position >= channelInfoList.size()){
                position = 0;
            }
            channelInfo = channelInfoList.get(position);
            play(AESUtil.decrypt(channelInfo.getUrl() , AESUtil.key));
        }
        return super.onKeyDown(keyCode, event);
    }
}
