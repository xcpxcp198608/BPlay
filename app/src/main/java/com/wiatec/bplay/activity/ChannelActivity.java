package com.wiatec.bplay.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.wiatec.bplay.Application;
import com.wiatec.bplay.F;
import com.wiatec.bplay.R;
import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.custom_view.EmotToast;
import com.wiatec.bplay.data.UserContentResolver;
import com.wiatec.bplay.databinding.ActivityChannelBinding;
import com.wiatec.bplay.fragment.FragmentFavorite;
import com.wiatec.bplay.fragment.FragmentLive;
import com.wiatec.bplay.fragment.FragmentMovies;
import com.wiatec.bplay.fragment.FragmentMusic;
import com.wiatec.bplay.fragment.FragmentNews;
import com.wiatec.bplay.fragment.FragmentRadios;
import com.wiatec.bplay.fragment.FragmentSports;
import com.wiatec.bplay.utils.AESUtil;
import com.wiatec.bplay.utils.AppUtils;
import com.wiatec.bplay.utils.Logger;
import com.wiatec.bplay.utils.SPUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by patrick on 2017/4/18.
 */

public class ChannelActivity extends AppCompatActivity {

    private ActivityChannelBinding binding;
    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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

    public void play(List<ChannelInfo> channelInfoList , int position, short isLock){
        String type = channelInfoList.get(position).getType();
        if("live".equals(type)){
            if(isLock == 1) {
                String level = UserContentResolver.get("userLevel");
                int userLevel;
                try {
                    userLevel = Integer.parseInt(level);
                }catch (Exception e){
                    userLevel = 1;
                }
                if (userLevel > 1) {
                    Intent intent = new Intent(ChannelActivity.this, PlayActivity.class);
                    intent.putExtra("channelInfoList", (Serializable) channelInfoList);
                    intent.putExtra("position", position);
                    startActivity(intent);
                } else {
                    String experience = UserContentResolver.get("experience");
                    if ("true".equals(experience)) {
                        Intent intent = new Intent(ChannelActivity.this, PlayActivity.class);
                        intent.putExtra("channelInfoList", (Serializable) channelInfoList);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    } else {
                        startActivity(new Intent(ChannelActivity.this, AdActivity.class));
                    }
                }
            }else{
                Intent intent = new Intent(ChannelActivity.this, PlayActivity.class);
                intent.putExtra("channelInfoList", (Serializable) channelInfoList);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        }else if("app".equals(type)){
            String packageName = AESUtil.decrypt(channelInfoList.get(position).getUrl() , AESUtil.key);
            if(AppUtils.isInstalled(Application.getContext(), packageName)){
                AppUtils.launchApp(this, packageName);
            }else {
                EmotToast.show(ChannelActivity.this,getString(R.string.app_no_install), EmotToast.EMOT_SMILE);
                AppUtils.launchApp(this, F.package_name.market);
            }
        }else if("radio".equals(type)){
            Intent intent = new Intent(ChannelActivity.this , PlayRadioActivity.class);
            intent.putExtra("channelInfoList" , (Serializable) channelInfoList);
            intent.putExtra("position" , position);
            startActivity(intent);
        }else{
            Logger.d("");
        }
    }

}
