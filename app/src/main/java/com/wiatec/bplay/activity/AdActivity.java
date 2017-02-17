package com.wiatec.bplay.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.wiatec.bplay.R;
import com.wiatec.bplay.beans.Channel;
import com.wiatec.bplay.beans.ImageInfo;
import com.wiatec.bplay.databinding.ActivityAdBinding;
import com.wiatec.bplay.presenter.AdPresenter;
import com.wiatec.bplay.utils.OkHttp.OkMaster;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by patrick on 2017/2/14.
 */

public class AdActivity extends BaseActivity<IAdActivity , AdPresenter> implements IAdActivity{

    private ActivityAdBinding binding;
    private int delayTime = 6;
    private Subscription subscription;
    private Channel channel;
    private String link;

    @Override
    public AdPresenter createPresenter() {
        return new AdPresenter(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_ad);
        binding.setOnEvent(new OnEventListener());
        channel = getIntent().getParcelableExtra("channel");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (channel == null) {
            return;
        }
        presenter.loadAdImage();
        delay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(subscription != null){
            subscription.unsubscribe();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscription != null){
            subscription.unsubscribe();
        }
    }

    @Override
    public void loadAd(ImageInfo imageInfo) {
        Glide.with(AdActivity.this)
                .load(imageInfo.getUrl())
                .placeholder(R.mipmap.bplay_logo)
                .error(R.mipmap.bplay_logo)
                .dontAnimate()
                .into(binding.ivAd);
        link = imageInfo.getLink();
    }

    private void showLink(String link){
        if(link == null){
            return;
        }
        try {
            startActivity(new Intent(Intent.ACTION_VIEW , Uri.parse(link)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void delay(){
        subscription = Observable.interval(0,1000, TimeUnit.MILLISECONDS)
                .take(delayTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        goPlay();
                    }

                    @Override
                    public void onError(Throwable e) {
                        goPlay();
                    }

                    @Override
                    public void onNext(Long aLong) {
                        int time = (int) (delayTime-1-aLong);
                        binding.llDelay.setVisibility(View.VISIBLE);
                        binding.tvDelayTime.setText(time + "s");
                    }
                });
    }

    private void goPlay(){
        Intent intent = new Intent();
        String type = channel.getType();
        if("live".equals(type)){
            intent.setClass(AdActivity.this,PlayActivity.class);
        }else if("radio".equals(type)){
            intent.setClass(AdActivity.this,PlayRadioActivity.class);
        }else{
            intent.setClass(AdActivity.this,PlayActivity.class);
        }
        intent.putExtra("channel" ,channel);
        startActivity(intent);
        finish();
    }

    public class OnEventListener{
        public void onClick(View view){
            switch (view.getId()){
                case R.id.bt_detail:
                    showLink(link);
                    break;
                case R.id.bt_pass:
                    goPlay();
                    break;
                default:
                    break;
            }
        }
    }

}
