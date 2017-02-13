package com.wiatec.bplay.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wiatec.bplay.R;
import com.wiatec.bplay.databinding.FragmentMusicBinding;
import com.wiatec.bplay.presenter.FragmentMusicPresenter;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentMusic extends BaseFragment<IFragmentMusic , FragmentMusicPresenter> implements IFragmentMusic {

    private FragmentMusicBinding binding;

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
}
