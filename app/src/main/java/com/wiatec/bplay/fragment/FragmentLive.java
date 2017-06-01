package com.wiatec.bplay.fragment;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wiatec.bplay.Application;
import com.wiatec.bplay.R;
import com.wiatec.bplay.activity.ChannelActivity;
import com.wiatec.bplay.adapter.ChannelAdapter;
import com.wiatec.bplay.adapter.ChannelTypeAdapter;
import com.wiatec.bplay.animator.Zoom;
import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.beans.ChannelType;
import com.wiatec.bplay.databinding.FragmentLiveBinding;
import com.wiatec.bplay.presenter.FragmentLivePresenter;
import com.wiatec.bplay.utils.Logger;
import com.wiatec.bplay.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentLive extends BaseFragment<IFragmentLive ,FragmentLivePresenter> implements IFragmentLive {

    private FragmentLiveBinding binding;
    private ChannelActivity activity;
    private ChannelAdapter channelAdapter;
    private List<ChannelInfo> channelInfoList = new ArrayList<>();
    private short isLock;

    @Override
    protected FragmentLivePresenter createPresenter() {
        return new FragmentLivePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater ,R.layout.fragment_live , container , false);
        presenter.loadChannelType("");
        binding.rvChannelType.setNextFocusRightId(R.id.rv_channel);
        binding.rvChannel.setNextFocusDownId(R.id.ibt_focus);
        binding.ibtFocus.setNextFocusDownId(R.id.rv_channel);
        binding.ibtFocus.setNextFocusUpId(R.id.rv_channel);
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ChannelActivity) context;
    }

    public class OnEventListener {
        public void onClick(View view){
            switch (view.getId()){
                default:
                    break;
            }
        }
    }

    @Override
    public void loadChannelType(final List<ChannelType> list) {
        ChannelTypeAdapter channelTypeAdapter = new ChannelTypeAdapter(list);
        binding.rvChannelType.setAdapter(channelTypeAdapter);
        binding.rvChannelType.setLayoutManager(new LinearLayoutManager(getContext()));
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
                    isLock = list.get(position).getIsLock();
                    String country = list.get(position).getName();
                    binding.rvChannel.setVisibility(View.GONE);
                    binding.tvLoadError.setVisibility(View.VISIBLE);
                    binding.tvLoadError.setText(getString(R.string.data_loading));
                    int flag = list.get(position).getFlag();
                    if(flag == 1){
                        presenter.loadFavoriteChannel();
                    }else {
                        presenter.loadChannelByCountry(country);
                    }
                    SPUtils.put(getContext() , "country" , country);
                }
            }
        });
    }

    @Override
    public void loadChannel(final List<ChannelInfo> list , boolean finished) {
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
                channelInfoList.clear();
                channelInfoList.addAll(list);
                if(channelAdapter == null){
                    channelAdapter = new ChannelAdapter(channelInfoList);
                    binding.rvChannel.setAdapter(channelAdapter);
                }
                channelAdapter.notifyDataSetChanged();
                binding.rvChannel.setLayoutManager(new GridLayoutManager(getContext(), 5));
                channelAdapter.setOnItemClickListener(new ChannelAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        activity.play(list, position, isLock);
                    }
                });
            }
        }
    }

    @Override
    public void loadFavoriteChannel(final List<ChannelInfo> list, boolean finished) {
        if(list == null || list.size() <= 0){
            binding.rvChannel.setVisibility(View.GONE);
            binding.tvLoadError.setVisibility(View.VISIBLE);
            binding.tvLoadError.setText(getString(R.string.favorite_empty));
        }else {
            binding.rvChannel.setVisibility(View.VISIBLE);
            binding.tvLoadError.setVisibility(View.GONE);
            channelInfoList.clear();
            channelInfoList.addAll(list);
            if(channelAdapter == null){
                channelAdapter = new ChannelAdapter(channelInfoList);
                binding.rvChannel.setAdapter(channelAdapter);
            }
            channelAdapter.notifyDataSetChanged();
            binding.rvChannel.setLayoutManager(new GridLayoutManager(getContext(), 5));
            channelAdapter.setOnItemClickListener(new ChannelAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    activity.play(list, position, isLock);
                }
            });
        }
    }
}
