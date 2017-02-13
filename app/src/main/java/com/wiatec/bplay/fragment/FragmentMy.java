package com.wiatec.bplay.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wiatec.bplay.R;
import com.wiatec.bplay.databinding.FragmentMyBinding;
import com.wiatec.bplay.presenter.FragmentMyPresenter;

/**
 * Created by patrick on 2017/2/13.
 */

public class FragmentMy extends BaseFragment<IFragmentMy , FragmentMyPresenter> implements IFragmentMy {

    private FragmentMyBinding binding;

    @Override
    protected FragmentMyPresenter createPresenter() {
        return new FragmentMyPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_my , container , false);
        return binding.getRoot();
    }
}
