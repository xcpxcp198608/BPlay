package com.wiatec.bplay.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wiatec.bplay.presenter.BasePresenter;

/**
 * Created by patrick on 2017/1/14.
 */

public abstract class BaseActivity1 <V , T extends BasePresenter>  extends AppCompatActivity{

    protected  T presenter;
    protected abstract T createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
