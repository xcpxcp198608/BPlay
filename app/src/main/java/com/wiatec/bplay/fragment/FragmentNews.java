package com.wiatec.bplay.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wiatec.bplay.R;
import com.wiatec.bplay.databinding.FragmentNewsBinding;
import com.wiatec.bplay.presenter.FragmentNewsPresenter;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentNews extends BaseFragment<IFragmentNews , FragmentNewsPresenter> implements IFragmentNews {

    private FragmentNewsBinding binding;

    @Override
    protected FragmentNewsPresenter createPresenter() {
        return new FragmentNewsPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_news , container ,false);
        return binding.getRoot();
    }
}
