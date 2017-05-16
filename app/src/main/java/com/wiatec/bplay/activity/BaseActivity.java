package com.wiatec.bplay.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wiatec.bplay.Application;
import com.wiatec.bplay.F;
import com.wiatec.bplay.R;
import com.wiatec.bplay.beans.Result;
import com.wiatec.bplay.presenter.BasePresenter;
import com.wiatec.bplay.rx_event.CheckLoginEvent;
import com.wiatec.bplay.service.CheckLoginService;
import com.wiatec.bplay.utils.Logger;
import com.wiatec.bplay.utils.OkHttp.OkMaster;
import com.wiatec.bplay.utils.RxBus.RxBus;
import com.wiatec.bplay.utils.SPUtils;
import com.wiatec.bplay.utils.SysUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by patrick on 2017/1/13.
 */

public abstract class BaseActivity<V ,T extends BasePresenter> extends AppCompatActivity {
    protected T presenter;
    protected String userName;
    private Subscription rxBusSubscription;

    public abstract T createPresenter();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.attachView(this);
        rxBusSubscription = RxBus.getDefault().toObservable(CheckLoginEvent.class)
                .subscribe(new Action1<CheckLoginEvent>() {
                    @Override
                    public void call(CheckLoginEvent checkLoginEvent) {
                        if(checkLoginEvent.getCode() == 1) {
                            showRepeatLoginDialog();
                        }
                    }
                });
        String mac = SysUtils.getWifiMac1(this);
        SPUtils.put(this , "mac" , mac);
        bindService(new Intent(this , CheckLoginService.class) , serviceConnection ,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void showRepeatLoginDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        Window window = alertDialog.getWindow();
        if(window == null){
            return;
        }
        window.setContentView(R.layout.dialog_login);
        Button btConfirm = (Button) window.findViewById(R.id.bt_confirm);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                startActivity(new Intent(Application.getContext() , LoginActivity.class));
            }
        });
        if(rxBusSubscription != null){
            rxBusSubscription.unsubscribe();
        }
    }



    protected void logout(){
        SPUtils.put(Application.getContext() ,"token" ,"");
        SPUtils.put(Application.getContext() ,"count" ,0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        if(rxBusSubscription != null){
            rxBusSubscription.unsubscribe();
        }
        unbindService(serviceConnection);
    }

    protected void logoutServer(){
        OkMaster.post(F.url.logout)
                .parames("username",userName)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Logger.d(e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response == null){
                            return;
                        }
                        Result result = new Gson().fromJson(response.body().string(),new TypeToken<Result>(){}.getType());
                        Logger.d(result.toString());
                    }
                });
    }
}
