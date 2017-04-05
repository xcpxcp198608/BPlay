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
import com.wiatec.bplay.databinding.FragmentRadiosBinding;
import com.wiatec.bplay.presenter.FragmentRadiosPresenter;

import java.util.List;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentRadios extends BaseFragment<IFragmentRadios , FragmentRadiosPresenter> implements IFragmentRadios {

    private FragmentRadiosBinding binding;
    private ChannelAdapter channelAdapter;
    private MainActivity activity;

    @Override
    protected FragmentRadiosPresenter createPresenter() {
        return new FragmentRadiosPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_radios , container ,false);
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
        presenter.loadChannelRadios("RADIOS");
    }
    @Override
    public void loadChannelRadios(final List<ChannelInfo> list) {
        if(channelAdapter == null) {
            channelAdapter = new ChannelAdapter(list);
        }
        binding.rvChannelRadios.setAdapter(channelAdapter);
        binding.rvChannelRadios.setLayoutManager(new GridLayoutManager(getContext(),4));
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
