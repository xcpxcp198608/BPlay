package com.wiatec.bplay.presenter;

import com.wiatec.bplay.fragment.IFragmentNews;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentNewsPresenter extends BasePresenter<IFragmentNews> {
    private IFragmentNews iFragmentNews;

    public FragmentNewsPresenter(IFragmentNews iFragmentNews) {
        this.iFragmentNews = iFragmentNews;
    }
}
