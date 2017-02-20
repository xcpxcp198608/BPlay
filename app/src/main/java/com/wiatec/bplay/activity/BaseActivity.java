package com.wiatec.bplay.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wiatec.bplay.Application;
import com.wiatec.bplay.F;
import com.wiatec.bplay.R;
import com.wiatec.bplay.beans.Result;
import com.wiatec.bplay.presenter.BasePresenter;
import com.wiatec.bplay.rx_event.RepeatLogin;
import com.wiatec.bplay.service.task.CheckLogin;
import com.wiatec.bplay.utils.Logger;
import com.wiatec.bplay.utils.OkHttp.Listener.StringListener;
import com.wiatec.bplay.utils.OkHttp.OkMaster;
import com.wiatec.bplay.utils.RxBus.RxBus;

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
    protected String token;
    protected String userName;
    protected int count;
    protected SharedPreferences sharedPreferences ;
    protected SharedPreferences.Editor editor;
    private Subscription rxBusSubscription;

    public abstract T createPresenter();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.attachView(this);

        sharedPreferences = getSharedPreferences("private" , MODE_PRIVATE);
        editor = sharedPreferences.edit();
        token = sharedPreferences.getString("token","");
        if(TextUtils.isEmpty(token)){
            startActivity(new Intent(this , LoginActivity.class));
            finish();
        }
        userName = sharedPreferences.getString("userName" , null);
        count = sharedPreferences.getInt("count",0);
        rxBusSubscription = RxBus.getDefault().toObservable(RepeatLogin.class)
                .subscribe(new Action1<RepeatLogin>() {
                    @Override
                    public void call(RepeatLogin repeatLogin) {
                        if(repeatLogin.getCode() == 1) {
                            showRepeatLoginDialog();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

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
            }
        });
        if(rxBusSubscription != null){
            rxBusSubscription.unsubscribe();
        }
    }

    protected void logout(){
        editor.putString("token" ,"");
        editor.putInt("count",0);
        editor.commit();
        startActivity(new Intent(this , LoginActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        if(rxBusSubscription != null){
            rxBusSubscription.unsubscribe();
        }
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
