package com.wiatec.bplay.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.wiatec.bplay.R;
import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.beans.ImageInfo;
import com.wiatec.bplay.databinding.ActivityAdBinding;
import com.wiatec.bplay.presenter.AdPresenter;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by patrick on 2017/2/14.
 */

public class AdActivity extends BaseActivity<IAdActivity , AdPresenter> implements IAdActivity{

    private ActivityAdBinding binding;

    @Override
    public AdPresenter createPresenter() {
        return new AdPresenter(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_ad);
        binding.setOnEvent(new OnEventListener());
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.loadAdImage();
    }

    @Override
    public void loadAd(ImageInfo imageInfo) {
        Glide.with(AdActivity.this)
                .load(imageInfo.getUrl())
                .placeholder(R.drawable.ad_bksound)
                .error(R.drawable.ad_bksound)
                .dontAnimate()
                .into(binding.ivAd);
    }

    public class OnEventListener{
        public void onClick(View view){

        }
    }

}
