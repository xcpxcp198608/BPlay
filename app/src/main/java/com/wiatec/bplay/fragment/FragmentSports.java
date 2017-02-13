package com.wiatec.bplay.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wiatec.bplay.R;
import com.wiatec.bplay.databinding.FragmentSportsBinding;
import com.wiatec.bplay.presenter.FragmentSportsPresenter;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentSports extends BaseFragment<IFragmentSports , FragmentSportsPresenter> implements IFragmentSports {

    private FragmentSportsBinding binding;

    @Override
    protected FragmentSportsPresenter createPresenter() {
        return new FragmentSportsPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_sports , container, false);
        return binding.getRoot();
    }
}
