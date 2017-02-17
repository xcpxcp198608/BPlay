package com.wiatec.bplay.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wiatec.bplay.Application;
import com.wiatec.bplay.R;
import com.wiatec.bplay.activity.AdActivity;
import com.wiatec.bplay.activity.MainActivity;
import com.wiatec.bplay.adapter.ChannelAdapter;
import com.wiatec.bplay.animator.Zoom;
import com.wiatec.bplay.beans.Channel;
import com.wiatec.bplay.databinding.FragmentMoviesBinding;
import com.wiatec.bplay.presenter.FragmentMoviesPresenter;

import java.util.List;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentMovies extends BaseFragment<IFragmentMovies , FragmentMoviesPresenter> implements IFragmentMovies {

    private FragmentMoviesBinding binding;
    private ChannelAdapter channelAdapter;
    private MainActivity activity;

    @Override
    protected FragmentMoviesPresenter createPresenter() {
        return new FragmentMoviesPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_movies , container , false);
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity  = (MainActivity) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.loadChannelMovies("MOVIES");
    }

    @Override
    public void loadChannelMovies(final List<Channel> list) {
        if(channelAdapter == null) {
            channelAdapter = new ChannelAdapter(list);
        }
        binding.rvChannelMovies.setAdapter(channelAdapter);
        binding.rvChannelMovies.setLayoutManager(new GridLayoutManager(getContext(),4));
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
