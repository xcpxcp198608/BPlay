package com.wiatec.bplay.presenter;

import com.wiatec.bplay.activity.IMainActivity;

/**
 * Created by patrick on 2017/2/10.
 */

public class MainPresenter extends BasePresenter <IMainActivity>{
    private IMainActivity iMainActivity;

    public MainPresenter(IMainActivity iMainActivity) {
        this.iMainActivity = iMainActivity;
    }
}
