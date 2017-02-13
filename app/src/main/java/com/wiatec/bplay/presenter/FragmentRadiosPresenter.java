package com.wiatec.bplay.presenter;

import com.wiatec.bplay.fragment.IFragmentRadios;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentRadiosPresenter extends BasePresenter<IFragmentRadios> {
    private IFragmentRadios iFragmentRadios ;

    public FragmentRadiosPresenter(IFragmentRadios iFragmentRadios) {
        this.iFragmentRadios = iFragmentRadios;
    }
}
