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
import com.wiatec.bplay.activity.MainActivity;
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
    private MainActivity activity;

    @Override
    protected FragmentMusicPresenter createPresenter() {
        return new FragmentMusicPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_music , container , false);
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.loadChannelMusic("MUSIC");
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
