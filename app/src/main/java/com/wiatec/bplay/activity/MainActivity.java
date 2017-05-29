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
            switch (v.getId()){
                case R.id.ibt_btv:
                    binding.ibtBtv.setImageResource(R.drawable.ic_btv);
                    break;
                case R.id.ibt_plus:
                    binding.ibtPlus.setImageResource(R.drawable.ic_plus1);
                    break;
                case R.id.ibt_tv:
                    binding.ibtTv.setImageResource(R.drawable.ic_movie);
                    break;
                default:
                    break;
            }
            Zoom.zoomIn10to13(v);
        }else{
            switch (v.getId()){
                case R.id.ibt_btv:
                    binding.ibtBtv.setImageResource(R.drawable.ic_btv3);
                    break;
                case R.id.ibt_plus:
                    binding.ibtPlus.setImageResource(R.drawable.ic_plus3);
                    break;
                case R.id.ibt_tv:
                    binding.ibtTv.setImageResource(R.drawable.ic_movie3);
                    break;
                default:
                    break;
            }
            Zoom.zoomIn13to10(v);
        }
    }
}
