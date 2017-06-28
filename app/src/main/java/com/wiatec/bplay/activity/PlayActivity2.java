package com.wiatec.bplay.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.wiatec.bplay.Application;
import com.wiatec.bplay.F;
import com.wiatec.bplay.R;
import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.custom_view.EmojiToast;
import com.wiatec.bplay.play.PlayManager;
import com.wiatec.bplay.sql.FavoriteDao;
import com.wiatec.bplay.utils.AESUtil;
import com.wiatec.bplay.utils.AppUtils;
import com.wiatec.bplay.utils.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Created by patrick on 2017/1/13.
 */

public class PlayActivity2 extends AppCompatActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener, PlayManager.PlayListener , SurfaceHolder.Callback{

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer mediaPlayer;
    private ProgressBar progressBar;
    private FavoriteDao favoriteDao;
    private FrameLayout flPlay;
    private LinearLayout llController;
    private CheckBox cbFavorite;
    private List<ChannelInfo> channelInfoList;
    private int position;
    private boolean isLock;
    private PlayManager playManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play2);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        flPlay = (FrameLayout) findViewById(R.id.fl_play);
        llController = (LinearLayout) findViewById(R.id.ll_controller);
        cbFavorite = (CheckBox) findViewById(R.id.cb_favorite);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        favoriteDao = FavoriteDao.getInstance(this);
        flPlay.setOnClickListener(this);

        channelInfoList = (List<ChannelInfo>) getIntent().getSerializableExtra("channelInfoList");
        position = getIntent().getIntExtra("position" , 0);
        short lock = getIntent().getShortExtra("isLock", (short)1);
        isLock = lock == 1;
        playManager = new PlayManager(channelInfoList, position, isLock);
        playManager.setPlayListener(this);

        if(favoriteDao.isExists(playManager.getChannelInfo())){
            cbFavorite.setChecked(true);
        }
        cbFavorite.setOnCheckedChangeListener(this);
    }

    @Override
    public void play(String url) {
        playVideo(url);
    }

    @Override
    public void playAd() {
        startActivity(new Intent(PlayActivity2.this, AdActivity.class));
        finish();
    }

    @Override
    public void launchApp(String packageName) {
        if(AppUtils.isInstalled(Application.getContext(), packageName)){
            AppUtils.launchApp(this, packageName);
            finish();
        }else {
            EmojiToast.show(PlayActivity2.this,getString(R.string.app_no_install), EmojiToast.EMOJI_SMILE);
            AppUtils.launchApp(this, F.package_name.market);
            finish();
        }
    }

    private void playVideo(final String url){
        Logger.d(url);
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
//                    EmojiToast.show(PlayActivity.this, getString(R.string.playing)+" "+channelInfo.getName() , EmojiToast.EMOJI_SMILE);
                    mediaPlayer.start();
                    progressBar.setVisibility(View.GONE);
                }
            });
            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
//                    Logger.d("onInfo-->" + what + "<--->" + extra);
                    if(what == MediaPlayer.MEDIA_INFO_BUFFERING_START){
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    if(what == MediaPlayer.MEDIA_INFO_BUFFERING_END){
                        progressBar.setVisibility(View.GONE);
                    }
                    return false;
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    progressBar.setVisibility(View.VISIBLE);
                    Logger.d(what + "<--->" + extra);
//                    EmojiToast.show(PlayActivity.this, MediaPlayerErrorUtil.getError(what), EmojiToast.EMOJI_SAD);
                    playVideo(url);
                    return true;
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Logger.d("onComplete");
                    if(mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    playVideo(url);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        playManager.dispatchChannel();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
    }

    private void release(){
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
                ChannelInfo channelInfo = playManager.getChannelInfo();
                if(isChecked){
                    if(favoriteDao.insert(channelInfo)){
                        cbFavorite.setChecked(true);
                        EmojiToast.show(PlayActivity2.this, channelInfo.getName()+ " " +getString(R.string.add_favorite) , EmojiToast.EMOJI_SMILE);
                    }
                }else{
                    if(favoriteDao.deleteByTag(channelInfo)) {
                        EmojiToast.show(PlayActivity2.this, channelInfo.getName()+ " " + getString(R.string.remove_favorite), EmojiToast.EMOJI_SAD);
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
        if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT || event.getKeyCode() == KeyEvent.KEYCODE_MEDIA_PREVIOUS){
            playManager.previousChannel();
        }
        if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT || event.getKeyCode() == KeyEvent.KEYCODE_MEDIA_NEXT){
            playManager.nextChannel();
        }
        return super.onKeyDown(keyCode, event);
    }
}
