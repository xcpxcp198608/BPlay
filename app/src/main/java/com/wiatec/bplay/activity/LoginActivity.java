package com.wiatec.bplay.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.wiatec.bplay.Application;
import com.wiatec.bplay.R;
import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.beans.Result;
import com.wiatec.bplay.custom_view.EmotToast;
import com.wiatec.bplay.databinding.ActivityLoginBinding;
import com.wiatec.bplay.presenter.LoginPresenter;
import com.wiatec.bplay.utils.Logger;
import com.wiatec.bplay.utils.SPUtils;

import java.util.List;

/**
 * Created by patrick on 2017/1/13.
 */

public class LoginActivity extends BaseActivity1<ILoginActivity , LoginPresenter> implements ILoginActivity {

    private ActivityLoginBinding binding;
    private String userName;
    private String password;
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
        userName = (String) SPUtils.get(Application.getContext() ,"userName" ,"");
        binding.etUsername.setText(userName);
        binding.etUsername.setSelection(userName.length());
    }

    public class OnEventListener{
        public void onClick(View view){
            switch (view.getId()){
                case R.id.bt_login:
                    userName = binding.etUsername.getText().toString().trim();
                    password = binding.etPassword.getText().toString().trim();
                    if(TextUtils.isEmpty(userName)){
                        EmotToast.show(LoginActivity.this, getString(R.string.username_empty), EmotToast.EMOT_SAD);
                        return;
                    }
                    if(TextUtils.isEmpty(password)){
                        EmotToast.show(LoginActivity.this, getString(R.string.password_empty), EmotToast.EMOT_SAD);
                        return;
                    }
                    binding.progressBar.setVisibility(View.VISIBLE);
                    presenter.login(userName , password);
                    break;
            }
        }
    }

    @Override
    public void login(Result result) {
        if(result.getCode() == Result.CODE_LOGIN_SUCCESS){
            SPUtils.put(Application.getContext() ,"userLevel" ,result.getUserLevel());
            SPUtils.put(Application.getContext() ,"currentLoginCount",result.getLoginCount());
            SPUtils.put(Application.getContext() ,"userName" , userName);
            SPUtils.put(Application.getContext(),"lastName" ,result.getExtra());
            binding.progressBar.setVisibility(View.GONE);
            EmotToast.show(LoginActivity.this, result.getStatus(), EmotToast.EMOT_SMILE);
            finish();
        }else{
            binding.progressBar.setVisibility(View.GONE);
            EmotToast.show(LoginActivity.this, result.getStatus(), EmotToast.EMOT_SAD);
        }
    }

}
