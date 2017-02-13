package com.wiatec.bplay.presenter;

import com.wiatec.bplay.fragment.IFragmentMovies;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentMoviesPresenter extends BasePresenter<IFragmentMovies> {
    private IFragmentMovies iFragmentMovies ;

    public FragmentMoviesPresenter(IFragmentMovies iFragmentMovies) {
        this.iFragmentMovies = iFragmentMovies;
    }
}
