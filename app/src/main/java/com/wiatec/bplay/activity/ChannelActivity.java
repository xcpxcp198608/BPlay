package com.wiatec.bplay.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.wiatec.bplay.Application;
import com.wiatec.bplay.F;
import com.wiatec.bplay.R;
import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.databinding.ActivityChannelBinding;
import com.wiatec.bplay.fragment.FragmentFavorite;
import com.wiatec.bplay.fragment.FragmentLive;
import com.wiatec.bplay.fragment.FragmentMovies;
import com.wiatec.bplay.fragment.FragmentMusic;
import com.wiatec.bplay.fragment.FragmentNews;
import com.wiatec.bplay.fragment.FragmentRadios;
import com.wiatec.bplay.fragment.FragmentSports;
import com.wiatec.bplay.utils.AppUtils;
import com.wiatec.bplay.utils.Logger;

/**
 * Created by patrick on 2017/4/18.
 */



public class ChannelActivity extends AppCompatActivity {

    private ActivityChannelBinding binding;
    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this ,R.layout.activity_channel);
        type = getIntent().getStringExtra("type");
        setFragment(type);
    }

    private void setFragment(String type) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if ("live".equals(type)) {
            FragmentLive fragmentLive = new FragmentLive();
            fragmentTransaction.add(R.id.fl_channel, fragmentLive);
        }else if("news".equals(type)){
            FragmentNews fragmentNews = new FragmentNews();
            fragmentTransaction.add(R.id.fl_channel, fragmentNews);
        }else if("movies".equals(type)){
            FragmentMovies fragmentMovies = new FragmentMovies();
            fragmentTransaction.add(R.id.fl_channel, fragmentMovies);
        }else if("music".equals(type)){
            FragmentMusic fragmentMusic = new FragmentMusic();
            fragmentTransaction.add(R.id.fl_channel, fragmentMusic);
        }else if("sports".equals(type)){
            FragmentSports fragmentSports = new FragmentSports();
            fragmentTransaction.add(R.id.fl_channel, fragmentSports);
        }else if("radios".equals(type)){
            FragmentRadios fragmentRadios = new FragmentRadios();
            fragmentTransaction.add(R.id.fl_channel, fragmentRadios);
        }else if("favorite".equals(type)){
            FragmentFavorite fragmentFavorite = new FragmentFavorite();
            fragmentTransaction.add(R.id.fl_channel, fragmentFavorite);
        }
        fragmentTransaction.commit();
    }

    public void play(ChannelInfo channelInfo){
        if("live".equals(channelInfo.getType())){
            Intent intent = new Intent(ChannelActivity.this , PlayActivity.class);
            intent.putExtra("channelInfo" , channelInfo);
            startActivity(intent);
        }else if("app".equals(channelInfo.getType())){
            if(AppUtils.isInstalled(Application.getContext(), channelInfo.getUrl())){
                AppUtils.launchApp(this, channelInfo.getUrl());
            }else {
                Toast.makeText(ChannelActivity.this,getString(R.string.app_no_install),Toast.LENGTH_SHORT).show();
                AppUtils.launchApp(this, F.package_name.market);
            }
        }else if("radio".equals(channelInfo.getType())){
            Intent intent = new Intent(ChannelActivity.this , PlayRadioActivity.class);
            intent.putExtra("channelInfo" , channelInfo);
            startActivity(intent);
        }else{
            Logger.d("");
        }
    }

}
