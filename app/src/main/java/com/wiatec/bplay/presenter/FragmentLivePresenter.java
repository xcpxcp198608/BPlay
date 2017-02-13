package com.wiatec.bplay.presenter;

import com.wiatec.bplay.fragment.IFragmentLive;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentLivePresenter extends BasePresenter<IFragmentLive> {
    private IFragmentLive iFragmentLive;

    public FragmentLivePresenter(IFragmentLive iFragmentLive) {
        this.iFragmentLive = iFragmentLive;
    }
}
