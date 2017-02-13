package com.wiatec.bplay.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.wiatec.bplay.R;
import com.wiatec.bplay.beans.Channel;
import com.wiatec.bplay.beans.Result;
import com.wiatec.bplay.databinding.ActivityLoginBinding;
import com.wiatec.bplay.presenter.LoginPresenter;
import com.wiatec.bplay.utils.Logger;

import java.util.List;

/**
 * Created by patrick on 2017/1/13.
 */

public class LoginActivity extends BaseActivity1<ILoginActivity , LoginPresenter> implements ILoginActivity {

    private ActivityLoginBinding binding;
    private String userName;
    private String token;

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_login);
        binding.setOnEvent(new OnEventListener());
        userName = sharedPreferences.getString("userName","");
        binding.etUsername.setText(userName);
        binding.etUsername.setSelection(userName.length());
    }

    public class OnEventListener{
        public void onClick(View view){
            switch (view.getId()){
                case R.id.bt_register:
                    startActivity(new Intent(LoginActivity.this , RegisterActivity.class));
                    break;
                case R.id.bt_login:
                    userName = binding.etUsername.getText().toString().trim();
                    String password = binding.etPassword.getText().toString().trim();
                    if(TextUtils.isEmpty(userName)){
                        Toast.makeText(LoginActivity.this, getString(R.string.username_empty), Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(TextUtils.isEmpty(password)){
                        Toast.makeText(LoginActivity.this, getString(R.string.password_empty), Toast.LENGTH_LONG).show();
                        return;
                    }
                    binding.progressBar.setVisibility(View.VISIBLE);
                    presenter.login(userName , password);
                    break;
            }
        }
    }

    @Override
    public void login(boolean loginSuccess , Result result) {
        if(loginSuccess){
            String resultInfo = (String) result.getInfo();
            String [] res = resultInfo.split("/");
            token = res[0];
            int count = Integer.parseInt(res[1]);
            Logger.d(token + count);
            editor.putString("token" ,token);
            editor.putInt("count",count);
            editor.putString("userName" , userName);
            editor.commit();
            presenter.loadChannel(token);
        }else{
            binding.progressBar.setVisibility(View.GONE);
            Toast.makeText(LoginActivity.this, getString(R.string.login_fail), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void loadChannel(List<Channel> list, boolean finished) {
        binding.progressBar.setVisibility(View.GONE);
        startActivity(new Intent(LoginActivity.this ,MainActivity.class));
        finish();
    }
}
