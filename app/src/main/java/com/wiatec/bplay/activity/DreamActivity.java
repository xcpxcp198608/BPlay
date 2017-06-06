package com.wiatec.bplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wiatec.bplay.R;
import com.wiatec.bplay.beans.ImageInfo;
import com.wiatec.bplay.presenter.AdPresenter;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by patrick on 06/06/2017.
 * create time : 10:57 AM
 */

public class DreamActivity extends BaseActivity<IAdActivity , AdPresenter> implements IAdActivity {

    private Subscription subscription;
    private ImageView ivDream;

    @Override
    public AdPresenter createPresenter() {
        return new AdPresenter(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream);
        ivDream = (ImageView) findViewById(R.id.iv_dream);
        presenter.loadAdImage();
    }

    @Override
    protected void onStart() {
        super.onStart();
        subscription = Observable.timer(30 , TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    @Override
    public void loadAd(ImageInfo imageInfo) {
        Glide.with(DreamActivity.this)
                .load(imageInfo.getUrl())
                .placeholder(R.drawable.ad_bksound)
                .error(R.drawable.ad_bksound)
                .dontAnimate()
                .into(ivDream);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(subscription != null){
            subscription.unsubscribe();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(subscription != null){
            subscription.unsubscribe();
        }
    }
}
