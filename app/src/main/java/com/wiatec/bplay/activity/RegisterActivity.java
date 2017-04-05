package com.wiatec.bplay.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.wiatec.bplay.Application;
import com.wiatec.bplay.R;
import com.wiatec.bplay.beans.Result;
import com.wiatec.bplay.databinding.ActivityRegisterBinding;
import com.wiatec.bplay.presenter.RegisterPresenter;
import com.wiatec.bplay.utils.Logger;

/**
 * Created by patrick on 2017/1/13.
 */

public class RegisterActivity extends BaseActivity1<IRegisterActivity ,RegisterPresenter> implements IRegisterActivity {

    ActivityRegisterBinding binding;
    private String userName;

    @Override
    public RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_register);
        binding.setOnEvent(new OnEventListener());
    }

    public class OnEventListener {
        public void onClick(View view){
            switch (view.getId()){
                case R.id.bt_register:
                    userName = binding.etUsername.getText().toString().trim();
                    String password = binding.etPassword.getText().toString().trim();
                    String password1 = binding.etPassword1.getText().toString().trim();
                    String email = binding.etEmail.getText().toString().trim();
                    if(TextUtils.isEmpty(userName)){
                        Toast.makeText(RegisterActivity.this, getString(R.string.username_empty), Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(TextUtils.isEmpty(password)){
                        Toast.makeText(RegisterActivity.this, getString(R.string.password_empty), Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(!checkPassword(password , password1)){
                        Toast.makeText(RegisterActivity.this, getString(R.string.password_different), Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(TextUtils.isEmpty(email)){
                        Toast.makeText(RegisterActivity.this, getString(R.string.email_empty), Toast.LENGTH_LONG).show();
                        return;
                    }
                    presenter.register(userName , password , email);
                    break;
            }
        }
    }

    private boolean checkPassword(String password ,String password1){
        if(password.equals(password1)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void register(final boolean registerSuccess , Result result) {
        if(registerSuccess){
            editor.putString("userName" , userName);
            editor.commit();
            Toast.makeText(Application.getContext(), result.getStatus(), Toast.LENGTH_LONG).show();
            startActivity(new Intent(RegisterActivity.this , LoginActivity.class));
            finish();
        }else{
            Toast.makeText(Application.getContext(), result.getStatus(), Toast.LENGTH_LONG).show();
        }
    }
}
