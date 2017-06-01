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
import com.wiatec.bplay.databinding.FragmentFavoriteBinding;
import com.wiatec.bplay.databinding.FragmentMoviesBinding;
import com.wiatec.bplay.presenter.FragmentFavoritePresenter;

import java.util.List;

/**
 * Created by patrick on 2017/4/19.
 */

public class FragmentFavorite extends BaseFragment<IFragment ,FragmentFavoritePresenter> implements IFragment {

    private FragmentFavoriteBinding binding;
    private ChannelAdapter channelAdapter;
    private ChannelActivity activity;

    @Override
    protected FragmentFavoritePresenter createPresenter() {
        return new FragmentFavoritePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_favorite , container ,false);
        presenter.loadFavoriteChannel();
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ChannelActivity) context;
    }

    @Override
    public void loadChannel(final List<ChannelInfo> list) {
        if(channelAdapter ==null) {
            channelAdapter = new ChannelAdapter(list);
        }
        binding.rvChannelFavorite.setAdapter(channelAdapter);
        binding.rvChannelFavorite.setLayoutManager(new GridLayoutManager(getContext(),4));
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
