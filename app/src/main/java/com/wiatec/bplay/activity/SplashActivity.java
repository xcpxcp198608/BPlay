package com.wiatec.bplay.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.wiatec.bplay.Application;
import com.wiatec.bplay.R;
import com.wiatec.bplay.beans.Channel;
import com.wiatec.bplay.beans.UpdateInfo;
import com.wiatec.bplay.databinding.ActivitySplashBinding;
import com.wiatec.bplay.presenter.SplashPresenter;
import com.wiatec.bplay.sql.ChannelDao;
import com.wiatec.bplay.utils.AppUtils;
import com.wiatec.bplay.utils.Logger;
import com.wiatec.bplay.utils.NetUtils;
import com.wiatec.bplay.utils.SysUtils;

import java.util.List;

/**
 * Created by patrick on 2017/1/13.
 */

public class SplashActivity extends BaseActivity<ISplashActivity , SplashPresenter> implements ISplashActivity {

    ActivitySplashBinding binding;

    @Override
    public SplashPresenter createPresenter() {
        return new SplashPresenter(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_splash);
        binding.tvVersion.setText(AppUtils.getVersionName(SplashActivity.this, getPackageName()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(NetUtils.isConnected(SplashActivity.this)){
            presenter.checkUpdate();
        }else{
            Toast.makeText(SplashActivity.this, getString(R.string.network_error), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void checkUpdate(UpdateInfo updateInfo) {
        if(updateInfo == null){
            return;
        }
        boolean isNeed = updateInfo.getCode() > AppUtils.getVersionCode(SplashActivity.this , getPackageName());
        if(isNeed){
            showUpdateDialog(updateInfo);
        }else{
            presenter.checkToken(token);
        }
    }

    @Override
    public void checkToken(boolean tokenValid) {
        if(tokenValid){
            presenter.loadChannel(token);
        }else{
            startActivity(new Intent(SplashActivity.this , LoginActivity.class));
            finish();
        }
    }

    @Override
    public void loadChannel(List<Channel> list  , boolean finished) {
        if(finished){
            startActivity(new Intent(SplashActivity.this , MainActivity.class));
            finish();
        }

    }

    private void showUpdateDialog(UpdateInfo updateInfo){
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setTitle(getString(R.string.notice));
        builder.setMessage(updateInfo.getInfo());
        builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}
