package com.wiatec.bplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wiatec.bplay.presenter.BasePresenter;

import java.lang.ref.WeakReference;

/**
 * Created by patrick on 2017/1/13.
 */

public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment{
    protected  T presenter;

    protected abstract T createPresenter();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.attachView((V)this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }
}
