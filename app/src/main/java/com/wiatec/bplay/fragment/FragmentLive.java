package com.wiatec.bplay.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.wiatec.bplay.Application;
import com.wiatec.bplay.R;
import com.wiatec.bplay.activity.AdActivity;
import com.wiatec.bplay.activity.MainActivity;
import com.wiatec.bplay.adapter.ChannelAdapter;
import com.wiatec.bplay.adapter.ChannelTypeAdapter;
import com.wiatec.bplay.animator.Zoom;
import com.wiatec.bplay.beans.Channel;
import com.wiatec.bplay.beans.ChannelType;
import com.wiatec.bplay.custom_view.GridDividerItemDecoration;
import com.wiatec.bplay.custom_view.GridOffsetsItemDecoration;
import com.wiatec.bplay.custom_view.LinearDividerItemDecoration;
import com.wiatec.bplay.custom_view.RecyclerViewLinearDecoration;
import com.wiatec.bplay.databinding.FragmentLiveBinding;
import com.wiatec.bplay.presenter.FragmentLivePresenter;
import com.wiatec.bplay.sql.ChannelDao;
import com.wiatec.bplay.utils.Logger;

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
        ChannelTypeAdapter channelTypeAdapter = null;
        if(channelTypeAdapter == null){
            channelTypeAdapter = new ChannelTypeAdapter(list);
        }
        binding.rvChannelType.setAdapter(channelTypeAdapter);
        binding.rvChannelType.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvChannelType.addItemDecoration(new RecyclerViewLinearDecoration(getContext() , LinearLayoutManager.VERTICAL , 5,R.color.colorTranslucent));
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
    public void loadChannel(final List<Channel> list , boolean finished) {
        ChannelAdapter channelAdapter = null;
        if(channelAdapter == null){
            channelAdapter = new ChannelAdapter(list);
        }
        binding.rvChannel.setAdapter(channelAdapter);
        binding.rvChannel.setLayoutManager(new GridLayoutManager(getContext() ,5));
        binding.rvChannel.addItemDecoration(new GridOffsetsItemDecoration(GridOffsetsItemDecoration.GRID_OFFSETS_VERTICAL));
        channelAdapter.setOnItemClickListener(new ChannelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext() , AdActivity.class);
                intent.putExtra("channel" ,list.get(position));
                startActivity(intent);
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
