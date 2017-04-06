package com.wiatec.bplay.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wiatec.bplay.R;
import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.beans.UpdateInfo;
import com.wiatec.bplay.databinding.ActivitySplashBinding;
import com.wiatec.bplay.presenter.SplashPresenter;
import com.wiatec.bplay.utils.AppUtils;
import com.wiatec.bplay.utils.NetUtils;

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
        String model = Build.MODEL;
        if(!"BTVi3".equals(model) && !"MorphoBT E110".equals(model)){
            Toast.makeText(SplashActivity.this,getString(R.string.device_notice),Toast.LENGTH_SHORT).show();
            return;
        }
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
//            presenter.checkToken(token);
            presenter.loadChannel(token);
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
    public void loadChannel(List<ChannelInfo> list  , boolean finished) {
        if(finished){
            startActivity(new Intent(SplashActivity.this , MainActivity.class));
            finish();
        }else{
            startActivity(new Intent(SplashActivity.this , MainActivity.class));
            finish();
        }
    }

    private void showUpdateDialog(final UpdateInfo updateInfo){
        AlertDialog alertDialog = new AlertDialog.Builder(SplashActivity.this).create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        Window window = alertDialog.getWindow();
        if(window == null){
            return;
        }
        window.setContentView(R.layout.dialog_update);
        Button btConfirm = (Button) window.findViewById(R.id.bt_confirm);
        Button btCancel = (Button) window.findViewById(R.id.bt_cancel);
        TextView textView = (TextView) window.findViewById(R.id.tv_info);
        textView.setText(updateInfo.getInfo());
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this , UpdateActivity.class);
                intent.putExtra("updateInfo" , updateInfo);
                startActivity(intent);
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
