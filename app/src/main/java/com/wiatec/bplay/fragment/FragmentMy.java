package com.wiatec.bplay.fragment;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wiatec.bplay.R;
import com.wiatec.bplay.activity.MainActivity;
import com.wiatec.bplay.beans.Channel;
import com.wiatec.bplay.databinding.FragmentMyBinding;
import com.wiatec.bplay.presenter.FragmentMyPresenter;
import com.wiatec.bplay.utils.Logger;

import java.util.List;

/**
 * Created by patrick on 2017/2/13.
 */

public class FragmentMy extends BaseFragment<IFragmentMy , FragmentMyPresenter> implements IFragmentMy {

    private FragmentMyBinding binding;
    private MainActivity activity;

    @Override
    protected FragmentMyPresenter createPresenter() {
        return new FragmentMyPresenter(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_my , container , false);
        binding.setOnEvent(new OnEventListener());
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.loadFavoriteChannel();
    }

    @Override
    public void loadFavoriteChannel(List<Channel> list) {
        Logger.d(list.toString());
    }

    public class OnEventListener{
        public void onClick (View view){
            switch (view.getId()){
                case R.id.bt_logout:
                    activity.logout1();
                    break;
                default:
                    break;
            }
        }

    }
}
