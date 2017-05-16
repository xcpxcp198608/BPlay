package com.wiatec.bplay.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wiatec.bplay.R;
import com.wiatec.bplay.activity.ChannelActivity;
import com.wiatec.bplay.adapter.ChannelFavoriteAdapter;
import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.databinding.FragmentMyBinding;
import com.wiatec.bplay.presenter.FragmentMyPresenter;

import java.util.List;

/**
 * Created by patrick on 2017/2/13.
 */

public class FragmentMy extends BaseFragment<IFragmentMy , FragmentMyPresenter> implements IFragmentMy {

    private FragmentMyBinding binding;
    private ChannelActivity activity;
    private ChannelFavoriteAdapter favoriteAdapter;

    @Override
    protected FragmentMyPresenter createPresenter() {
        return new FragmentMyPresenter(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ChannelActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_my , container , false);
        binding.setOnEvent(new OnEventListener());
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.loadFavoriteChannel();
    }

    @Override
    public void loadFavoriteChannel(final List<ChannelInfo> list) {
        favoriteAdapter = new ChannelFavoriteAdapter(list);
        binding.rvChannelFavorite.setAdapter(favoriteAdapter);
        binding.rvChannelFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteAdapter.setOnItemClickListener(new ChannelFavoriteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                activity.play(list , position);
            }
        });
    }

    public class OnEventListener{
        public void onClick (View view){
            switch (view.getId()){
                case R.id.bt_logout:
//                    activity.logout1();
                    break;
                case R.id.ibt1:
//                    startActivity(new Intent(getContext() , WebViewActivity.class));
                    break;
                default:
                    break;
            }
        }
    }
}
