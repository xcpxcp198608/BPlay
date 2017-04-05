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

import com.wiatec.bplay.R;
import com.wiatec.bplay.activity.MainActivity;
import com.wiatec.bplay.adapter.ChannelAdapter;
import com.wiatec.bplay.adapter.ChannelTypeAdapter;
import com.wiatec.bplay.animator.Zoom;
import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.beans.ChannelType;
import com.wiatec.bplay.databinding.FragmentLiveBinding;
import com.wiatec.bplay.presenter.FragmentLivePresenter;

import java.util.List;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentLive extends BaseFragment<IFragmentLive ,FragmentLivePresenter> implements IFragmentLive {

    private FragmentLiveBinding binding;
    private MainActivity activity;

    @Override
    protected FragmentLivePresenter createPresenter() {
        return new FragmentLivePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater ,R.layout.fragment_live , container , false);
        presenter.loadChannelType(activity.getToken());
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
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
        channelTypeAdapter.setOnItemClickListener(new ChannelTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                for(int i=0 ; i<binding.rvChannelType.getChildCount() ; i++){
                    View v = binding.rvChannelType.getChildAt(i);
                    v.setBackgroundResource(R.drawable.channel_type_bg);
                }
                view.setBackgroundResource(R.drawable.channel_type_bg_focus);
                String country = list.get(position).getName();
                presenter.loadChannelByCountry(country);
            }
        });
        channelTypeAdapter.setOnItemSelectedListener(new ChannelTypeAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position ,boolean hasFocus) {
                for(int i=0 ; i<binding.rvChannelType.getChildCount() ; i++){
                    View v = binding.rvChannelType.getChildAt(i);
                    v.setBackgroundResource(R.drawable.channel_type_bg);
                }
                view.setBackgroundResource(R.drawable.channel_type_bg_focus);
                if(hasFocus) {
                    String country = list.get(position).getName();
                    presenter.loadChannelByCountry(country);
                }
            }
        });
        presenter.loadChannelByCountry("CHINA");
    }

    @Override
    public void loadChannel(final List<ChannelInfo> list , boolean finished) {
        ChannelAdapter channelAdapter = new ChannelAdapter(list);
        binding.rvChannel.setAdapter(channelAdapter);
        binding.rvChannel.setLayoutManager(new GridLayoutManager(getContext() ,5));
        channelAdapter.setOnItemClickListener(new ChannelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                activity.play(list.get(position));
            }
        });
        channelAdapter.setOnItemSelectedListener(new ChannelAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position) {
                Zoom.zoomIn09to10(view);
            }
        });
    }


}
