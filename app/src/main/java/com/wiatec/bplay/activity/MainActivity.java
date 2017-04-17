package com.wiatec.bplay.activity;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wiatec.bplay.Application;
import com.wiatec.bplay.F;
import com.wiatec.bplay.R;
import com.wiatec.bplay.adapter.FragmentAdapter;
import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.databinding.ActivityMainBinding;
import com.wiatec.bplay.fragment.FragmentLive;
import com.wiatec.bplay.fragment.FragmentMovies;
import com.wiatec.bplay.fragment.FragmentMusic;
import com.wiatec.bplay.fragment.FragmentMy;
import com.wiatec.bplay.fragment.FragmentNews;
import com.wiatec.bplay.fragment.FragmentRadios;
import com.wiatec.bplay.fragment.FragmentSports;
import com.wiatec.bplay.presenter.MainPresenter;
import com.wiatec.bplay.service.CheckLoginService;
import com.wiatec.bplay.utils.AppUtils;
import com.wiatec.bplay.utils.Logger;

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
    private int viewPagerCurrentItem;
    private long backTime;

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setOnEvent(new OnEventListener());
        attachFragment();
//        bindService();
    }

    private void attachFragment (){
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
        binding.viewPagerIndicator.setItem(7 ,0f ,0f);
        String [] titles = {getString(R.string.live) , getString(R.string.news), getString(R.string.movies) , getString(R.string.music) , getString(R.string.sports), getString(R.string.radios),getString(R.string.my)};
        binding.viewPagerIndicator.setTextTitle(titles , R.color.colorTranslucent , R.drawable.img_blue_light,25 , 0xffa3a3a3 , 0xffffffff);
        binding.viewPagerIndicator.attachViewPager(binding.viewPager , viewPagerCurrentItem);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //binding.viewPager.requestFocus();
    }

    private void bindService(){
        Intent intent = new Intent(MainActivity.this , CheckLoginService.class);
        intent.putExtra("userName" , userName);
        intent.putExtra("count" ,count);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public class OnEventListener{
        public void onClick (View view){
            switch (view.getId()){

                default:
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
           showExitDialog();
           return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showExitDialog(){
        final AlertDialog alertDialog = new AlertDialog.Builder(this,R.style.dialog).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        if(window == null){
            return;
        }
        window.setContentView(R.layout.dialog_exit);
        Button btConfirm = (Button) window.findViewById(R.id.bt_confirm);
        Button btCancel = (Button) window.findViewById(R.id.bt_cancel);
        TextView textView = (TextView) window.findViewById(R.id.tv_info);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    public void play(ChannelInfo channelInfo){
        if("live".equals(channelInfo.getType())){
            Intent intent = new Intent(MainActivity.this , PlayActivity.class);
            intent.putExtra("channelInfo" , channelInfo);
            startActivity(intent);
        }else if("app".equals(channelInfo.getType())){
            if(AppUtils.isInstalled(Application.getContext(), channelInfo.getUrl())){
                AppUtils.launchApp(this, channelInfo.getUrl());
            }else {
                Toast.makeText(MainActivity.this,getString(R.string.app_no_install),Toast.LENGTH_SHORT).show();
                AppUtils.launchApp(this, F.package_name.market);
            }
        }else if("radio".equals(channelInfo.getType())){
            Intent intent = new Intent(MainActivity.this , PlayRadioActivity.class);
            intent.putExtra("channelInfo" , channelInfo);
            startActivity(intent);
        }else{
            Logger.d("");
        }
    }

    public void logout1(){
        logout();
        logoutServer();
        finish();
   }

    public String getToken(){
        return token;
    }
}
