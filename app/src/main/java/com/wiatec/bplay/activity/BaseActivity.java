package com.wiatec.bplay.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wiatec.bplay.presenter.BasePresenter;

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

    public abstract T createPresenter();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.attachView(this);

        sharedPreferences = getSharedPreferences("private" , MODE_PRIVATE);
        editor = sharedPreferences.edit();
        token = sharedPreferences.getString("token",null);
        if(token == null){
            startActivity(new Intent(this , LoginActivity.class));
            finish();
        }
        userName = sharedPreferences.getString("userName" , null);
        count = sharedPreferences.getInt("count",0);

    }

    protected void logout(){
        editor.clear();
        editor.commit();
        startActivity(new Intent(this , LoginActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
