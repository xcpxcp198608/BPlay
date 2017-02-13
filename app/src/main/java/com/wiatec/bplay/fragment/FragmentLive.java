package com.wiatec.bplay.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wiatec.bplay.R;
import com.wiatec.bplay.databinding.FragmentLiveBinding;
import com.wiatec.bplay.presenter.FragmentLivePresenter;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentLive extends BaseFragment<IFragmentLive ,FragmentLivePresenter> implements IFragmentLive {

    private FragmentLiveBinding binding;

    @Override
    protected FragmentLivePresenter createPresenter() {
        return new FragmentLivePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater ,R.layout.fragment_live , container , false);
        return binding.getRoot();
    }

    public class OnEventListener {
        public void onClick(View view){

        }
    }
}
