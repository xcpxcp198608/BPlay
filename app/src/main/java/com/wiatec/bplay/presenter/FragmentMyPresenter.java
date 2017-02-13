package com.wiatec.bplay.presenter;

import com.wiatec.bplay.fragment.IFragmentMy;

/**
 * Created by patrick on 2017/2/13.
 */

public class FragmentMyPresenter extends BasePresenter<IFragmentMy> {

    private IFragmentMy iFragmentMy;

    public FragmentMyPresenter(IFragmentMy iFragmentMy) {
        this.iFragmentMy = iFragmentMy;
    }
}
