package com.wiatec.bplay.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.common.collect.ForwardingList;
import com.wiatec.bplay.R;
import com.wiatec.bplay.adapter.FragmentAdapter;
import com.wiatec.bplay.databinding.ActivityMainBinding;
import com.wiatec.bplay.fragment.FragmentLive;
import com.wiatec.bplay.fragment.FragmentMovies;
import com.wiatec.bplay.fragment.FragmentMusic;
import com.wiatec.bplay.fragment.FragmentMy;
import com.wiatec.bplay.fragment.FragmentNews;
import com.wiatec.bplay.fragment.FragmentRadios;
import com.wiatec.bplay.fragment.FragmentSports;
import com.wiatec.bplay.presenter.MainPresenter;
import com.wiatec.bplay.sql.ChannelDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<IMainActivity , MainPresenter> implements IMainActivity {

    private ActivityMainBinding binding;
    private FragmentLive fragmentLive;
    private FragmentMovies fragmentMovies;
    private FragmentMusic fragmentMusic;
    private FragmentNews fragmentNews;
    private FragmentSports fragmentSports;
    private FragmentRadios fragmentRadios;
    private FragmentMy fragmentMy;
    private List<Fragment> fragmentList;
    private FragmentAdapter fragmentAdapter;

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setOnEvent(new OnEventListener());
        if(fragmentLive == null){
            fragmentLive = new FragmentLive();
        }
        if(fragmentMovies == null){
            fragmentMovies = new FragmentMovies();
        }
        if(fragmentMusic == null){
            fragmentMusic = new FragmentMusic();
        }
        if(fragmentNews == null){
            fragmentNews = new FragmentNews();
        }
        if(fragmentSports == null){
            fragmentSports = new FragmentSports();
        }
        if(fragmentRadios == null){
            fragmentRadios = new FragmentRadios();
        }
        if (fragmentMy == null) {
            fragmentMy = new FragmentMy();
        }
        if(fragmentList == null){
            fragmentList = new ArrayList<>();
        }
        fragmentList.add(fragmentLive);
        fragmentList.add(fragmentNews);
        fragmentList.add(fragmentMovies);
        fragmentList.add(fragmentMusic);
        fragmentList.add(fragmentSports);
        fragmentList.add(fragmentRadios);
        fragmentList.add(fragmentMy);
        if(fragmentAdapter == null){
            fragmentAdapter = new FragmentAdapter(getSupportFragmentManager() , fragmentList);
        }
        binding.viewPager.setAdapter(fragmentAdapter);
        binding.viewPager.setCurrentItem(0);
        binding.viewPagerIndicator.setItem(7 ,1/6f ,1/6f);
        String [] titles = {getString(R.string.live) , getString(R.string.news), getString(R.string.movies) , getString(R.string.music) , getString(R.string.sports), getString(R.string.radios),getString(R.string.my)};
        binding.viewPagerIndicator.setButtonTitle(titles , R.color.colorTranslucent , R.drawable.blue_light,25 , 0xffa3a3a3 , 0xffffffff);
        binding.viewPagerIndicator.attachViewPager(binding.viewPager , 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public class OnEventListener{
        public void onClick (View view){
            switch (view.getId()){

                default:
                    break;
            }
        }
    }

    public void logout1(){
       logout();
   }

    public String getToken(){
        return token;
    }
}
