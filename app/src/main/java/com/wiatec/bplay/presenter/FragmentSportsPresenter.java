package com.wiatec.bplay.presenter;

import com.wiatec.bplay.fragment.IFragmentSports;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentSportsPresenter extends BasePresenter<IFragmentSports> {
    private IFragmentSports iFragmentSports;

    public FragmentSportsPresenter(IFragmentSports iFragmentSports) {
        this.iFragmentSports = iFragmentSports;
    }
}
