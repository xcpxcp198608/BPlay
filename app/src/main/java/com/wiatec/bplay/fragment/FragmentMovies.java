package com.wiatec.bplay.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wiatec.bplay.R;
import com.wiatec.bplay.activity.MainActivity;
import com.wiatec.bplay.databinding.FragmentMoviesBinding;
import com.wiatec.bplay.presenter.FragmentMoviesPresenter;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentMovies extends BaseFragment<IFragmentMovies , FragmentMoviesPresenter> implements IFragmentMovies {

    private FragmentMoviesBinding binding;
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
        activity = (MainActivity) context;
    }
}
