package com.wiatec.bplay.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wiatec.bplay.R;
import com.wiatec.bplay.databinding.FragmentRadiosBinding;
import com.wiatec.bplay.presenter.FragmentRadiosPresenter;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentRadios extends BaseFragment<IFragmentRadios , FragmentRadiosPresenter> implements IFragmentRadios {

    private FragmentRadiosBinding binding;

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
}
