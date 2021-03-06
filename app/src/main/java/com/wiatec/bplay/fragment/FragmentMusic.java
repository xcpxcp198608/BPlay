package com.wiatec.bplay.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wiatec.bplay.R;
import com.wiatec.bplay.activity.ChannelActivity;
import com.wiatec.bplay.adapter.ChannelAdapter;
import com.wiatec.bplay.animator.Zoom;
import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.databinding.FragmentMusicBinding;
import com.wiatec.bplay.presenter.FragmentMusicPresenter;

import java.util.List;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentMusic extends BaseFragment<IFragmentMusic , FragmentMusicPresenter> implements IFragmentMusic {

    private FragmentMusicBinding binding;
    private ChannelAdapter channelAdapter;
    private ChannelActivity activity;

    @Override
    protected FragmentMusicPresenter createPresenter() {
        return new FragmentMusicPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_music , container , false);
        presenter.loadChannelMusic("MUSIC");
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ChannelActivity) context;
    }

    @Override
    public void loadChannelMusic(final List<ChannelInfo> list) {
        if(channelAdapter == null) {
            channelAdapter = new ChannelAdapter(list);
        }
        binding.rvChannelMusic.setAdapter(channelAdapter);
        binding.rvChannelMusic.setLayoutManager(new GridLayoutManager(getContext(),4));
        channelAdapter.setOnItemClickListener(new ChannelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                activity.play(list , position, (short)0);
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
