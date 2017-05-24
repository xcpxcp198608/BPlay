package com.wiatec.bplay.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.wiatec.bplay.F;
import com.wiatec.bplay.R;
import com.wiatec.bplay.animator.Zoom;
import com.wiatec.bplay.databinding.ActivityMainBinding;
import com.wiatec.bplay.utils.AppUtils;
import com.wiatec.bplay.utils.SPUtils;

/**
 * Created by patrick on 2017/4/18.
 */

public class MainActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    private ActivityMainBinding binding ;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main);
        binding.setOnEvent(new OnEventListener());
        intent = new Intent();
        setFocusListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        String lastName = (String) SPUtils.get(MainActivity.this , "lastName" , "");
        if(!TextUtils.isEmpty(lastName)){
            binding.tvUser.setText(getString(R.string.hi) + " " +lastName);
        }
    }

    public class OnEventListener {
        public void onClick (View view){
            switch (view.getId()){
                case R.id.ibt_btv:
                    AppUtils.launchApp(MainActivity.this , F.package_name.btv);
                    break;
                case R.id.ibt_plus:
                    intent.setClass(MainActivity.this , ChannelActivity.class);
                    intent.putExtra("type","live");
                    startActivity(intent);
                    break;
                case R.id.ibt_tv:
                    intent.setClass(MainActivity.this , MovieActivity.class);
                    startActivity(intent);
                    break;
                case R.id.ibt_person:
                    intent.setClass(MainActivity.this , LoginActivity.class);
                    startActivity(intent);
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

    private void showAppShortcutDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this,R.style.dialog).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        if(window == null){
            return;
        }
        window.setContentView(R.layout.dialog_app_shortcut);
        ImageButton ibtTVHouse = (ImageButton) window.findViewById(R.id.ibt_tv_house);
        ImageButton ibtShowBox = (ImageButton) window.findViewById(R.id.ibt_show_box);
        ibtTVHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.launchApp(MainActivity.this , F.package_name.tv_house);
            }
        });
        ibtShowBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.launchApp(MainActivity.this , F.package_name.show_box);
            }
        });
        ibtTVHouse.setOnFocusChangeListener(this);
        ibtShowBox.setOnFocusChangeListener(this);
    }

    private void setFocusListener(){
        binding.etSearch.setOnFocusChangeListener(this);
        binding.ibtPerson.setOnFocusChangeListener(this);
        binding.ibtBtv.setOnFocusChangeListener(this);
        binding.ibtPlus.setOnFocusChangeListener(this);
        binding.ibtTv.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            Zoom.zoomIn10to13(v);
        }else{
            Zoom.zoomIn13to10(v);
        }
    }
}
