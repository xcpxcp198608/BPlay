package com.wiatec.bplay.presenter;

import com.wiatec.bplay.fragment.IFragmentMusic;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentMusicPresenter extends BasePresenter<IFragmentMusic> {
    private IFragmentMusic iFragmentMusic;

    public FragmentMusicPresenter(IFragmentMusic iFragmentMusic) {
        this.iFragmentMusic = iFragmentMusic;
    }
}
