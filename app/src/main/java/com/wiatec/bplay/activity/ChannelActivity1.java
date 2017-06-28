package com.wiatec.bplay.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;

import com.wiatec.bplay.Application;
import com.wiatec.bplay.F;
import com.wiatec.bplay.R;
import com.wiatec.bplay.adapter.ChannelAdapter;
import com.wiatec.bplay.adapter.ChannelTypeAdapter;
import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.beans.ChannelType;
import com.wiatec.bplay.custom_view.EmojiToast;
import com.wiatec.bplay.data.UserContentResolver;
import com.wiatec.bplay.databinding.ActivityChannel1Binding;
import com.wiatec.bplay.presenter.Channel1Presenter;
import com.wiatec.bplay.utils.AESUtil;
import com.wiatec.bplay.utils.AppUtils;
import com.wiatec.bplay.utils.Logger;
import com.wiatec.bplay.utils.SPUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrick on 18/06/2017.
 * create time : 2:18 PM
 */

public class ChannelActivity1 extends BaseActivity1<IChannelActivity1 , Channel1Presenter> implements IChannelActivity1 {

    private ActivityChannel1Binding binding;
    private List<ChannelInfo> channelInfoList = new ArrayList<>();
    private short isLock;
    private int mPosition;

    @Override
    protected Channel1Presenter createPresenter() {
        return new Channel1Presenter(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_channel1);
        presenter.loadChannelType("");
        binding.rvChannelType.setNextFocusRightId(R.id.rv_channel);
        binding.rvChannel.setNextFocusDownId(R.id.ibt_focus);
        binding.ibtFocus.setNextFocusDownId(R.id.rv_channel);
        binding.ibtFocus.setNextFocusUpId(R.id.rv_channel);
    }

    public class OnEventListener{
        public void onClick(View view){

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void loadChannelType(final List<ChannelType> list) {
        ChannelTypeAdapter channelTypeAdapter = new ChannelTypeAdapter(list);
        binding.rvChannelType.setAdapter(channelTypeAdapter);
        binding.rvChannelType.setLayoutManager(new LinearLayoutManager(this));
        channelTypeAdapter.setOnItemSelectedListener(new ChannelTypeAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position ,boolean hasFocus) {
                int channelTypeCount = binding.rvChannelType.getChildCount();
                for(int i=0 ; i < channelTypeCount ; i++){
                    View v = binding.rvChannelType.getChildAt(i);
                    v.setBackgroundResource(R.drawable.bg_item_channel_type);
                }
                view.setBackgroundResource(R.drawable.bg_item_channel_type_focus);
                if(hasFocus) {
                    ChannelType channelType = list.get(position);
                    isLock = channelType.getIsLock();
                    String country = channelType.getName();
                    binding.rvChannel.setVisibility(View.GONE);
                    binding.tvLoadError.setVisibility(View.VISIBLE);
                    binding.tvLoadError.setText(getString(R.string.data_loading));
                    if(channelType.getFlag() == 1){
                        presenter.loadFavoriteChannel();
                    }else {
                        presenter.loadChannelByCountry(country);
                    }
                    SPUtils.put(ChannelActivity1.this , "country" , country);
                }
            }
        });
    }

    @Override
    public void loadChannel(final List<ChannelInfo> list, boolean finished) {
        channelInfoList = list;
        String country = (String) SPUtils.get(Application.getContext() , "country" , "");
        if(list == null || list.size() <= 0){
            binding.rvChannel.setVisibility(View.GONE);
            binding.tvLoadError.setVisibility(View.VISIBLE);
            if("FAVORITE".equals(country)){
                binding.tvLoadError.setText(getString(R.string.favorite_empty));
            }else {
                binding.tvLoadError.setText(getString(R.string.data_load_error));
            }
        }else {
            if(list.get(0).getCountry().equals(country)) {
                binding.rvChannel.setVisibility(View.VISIBLE);
                binding.tvLoadError.setVisibility(View.GONE);
                ChannelAdapter channelAdapter = new ChannelAdapter(list);
                binding.rvChannel.setAdapter(channelAdapter);
                binding.rvChannel.setLayoutManager(new GridLayoutManager(this, 5));
                channelAdapter.setOnItemClickListener(new ChannelAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        play(list, position, isLock);
                    }
                });
                channelAdapter.setOnItemSelectedListener(new ChannelAdapter.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(View view, int position) {
                        channelInfoList = list;
                        mPosition = position;
                    }
                });
            }
        }
    }

    @Override
    public void loadFavoriteChannel(final List<ChannelInfo> list, boolean finished) {
        channelInfoList = list;
        if(list == null || list.size() <= 0){
            binding.rvChannel.setVisibility(View.GONE);
            binding.tvLoadError.setVisibility(View.VISIBLE);
            binding.tvLoadError.setText(getString(R.string.favorite_empty));
        }else {
            binding.rvChannel.setVisibility(View.VISIBLE);
            binding.tvLoadError.setVisibility(View.GONE);
            ChannelAdapter channelAdapter = new ChannelAdapter(list);
            binding.rvChannel.setAdapter(channelAdapter);
            binding.rvChannel.setLayoutManager(new GridLayoutManager(this, 5));
            channelAdapter.setOnItemClickListener(new ChannelAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    play(list, position, isLock);
                }
            });
        }
    }

    public void play(List<ChannelInfo> channelInfoList , int position, short isLock){
        Intent intent = new Intent(ChannelActivity1.this, PlayActivity2.class);
        intent.putExtra("channelInfoList", (Serializable) channelInfoList);
        intent.putExtra("position", position);
        intent.putExtra("isLock", isLock);
        startActivity(intent);
//        String type = channelInfoList.get(position).getType();
//        if("live".equals(type)){
//            if(isLock == 1) {
//                String level = UserContentResolver.get("userLevel");
//                int userLevel;
//                try {
//                    userLevel = Integer.parseInt(level);
//                }catch (Exception e){
//                    userLevel = 1;
//                }
//                if (userLevel > 1) {
//                    Intent intent = new Intent(ChannelActivity1.this, PlayActivity.class);
//                    intent.putExtra("channelInfoList", (Serializable) channelInfoList);
//                    intent.putExtra("position", position);
//                    startActivity(intent);
//                } else {
//                    String experience = UserContentResolver.get("experience");
//                    if ("true".equals(experience)) {
//                        Intent intent = new Intent(ChannelActivity1.this, PlayActivity.class);
//                        intent.putExtra("channelInfoList", (Serializable) channelInfoList);
//                        intent.putExtra("position", position);
//                        startActivity(intent);
//                    } else {
//                        startActivity(new Intent(ChannelActivity1.this, AdActivity.class));
//                    }
//                }
//            }else{
//                Intent intent = new Intent(ChannelActivity1.this, PlayActivity.class);
//                intent.putExtra("channelInfoList", (Serializable) channelInfoList);
//                intent.putExtra("position", position);
//                startActivity(intent);
//            }
//        }else if("app".equals(type)){
//            String packageName = AESUtil.decrypt(channelInfoList.get(position).getUrl() , AESUtil.key);
//            if(AppUtils.isInstalled(Application.getContext(), packageName)){
//                AppUtils.launchApp(this, packageName);
//            }else {
//                EmojiToast.show(ChannelActivity1.this,getString(R.string.app_no_install), EmojiToast.EMOJI_SMILE);
//                AppUtils.launchApp(this, F.package_name.market);
//            }
//        }else if("radio".equals(type)){
//            Intent intent = new Intent(ChannelActivity1.this , PlayRadioActivity.class);
//            intent.putExtra("channelInfoList" , (Serializable) channelInfoList);
//            intent.putExtra("position" , position);
//            startActivity(intent);
//        }else{
//            Logger.d("");
//        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(event.getKeyCode() == KeyEvent.KEYCODE_MEDIA_PLAY){
            play(channelInfoList , mPosition , isLock);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
