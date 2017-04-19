package com.wiatec.bplay.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.wiatec.bplay.R;
import com.wiatec.bplay.animator.Zoom;
import com.wiatec.bplay.databinding.ActivityMain1Binding;

/**
 * Created by patrick on 2017/4/18.
 */

public class MainActivity1 extends AppCompatActivity implements View.OnFocusChangeListener {

    private ActivityMain1Binding binding ;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main1);
        binding.setOnEvent(new OnEventListener());
        intent = new Intent();
        setFocusListener();
    }

    public class OnEventListener {
        public void onClick (View view){
            switch (view.getId()){
                case R.id.ibt_live:
                    intent.setClass(MainActivity1.this , ChannelActivity.class);
                    intent.putExtra("type","live");
                    startActivity(intent);
                    break;
                case R.id.ibt_news:
                    intent.setClass(MainActivity1.this , ChannelActivity.class);
                    intent.putExtra("type","news");
                    startActivity(intent);
                    break;
                case R.id.ibt_movies:
                    intent.setClass(MainActivity1.this , ChannelActivity.class);
                    intent.putExtra("type","movies");
                    startActivity(intent);
                    break;
                case R.id.ibt_music:
                    intent.setClass(MainActivity1.this , ChannelActivity.class);
                    intent.putExtra("type","music");
                    startActivity(intent);
                    break;
                case R.id.ibt_sports:
                    intent.setClass(MainActivity1.this , ChannelActivity.class);
                    intent.putExtra("type","sports");
                    startActivity(intent);
                    break;
                case R.id.ibt_radios:
                    intent.setClass(MainActivity1.this , ChannelActivity.class);
                    intent.putExtra("type","radios");
                    startActivity(intent);
                case R.id.ibt_favorite:
                    intent.setClass(MainActivity1.this , ChannelActivity.class);
                    intent.putExtra("type","favorite");
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

    private void setFocusListener(){
        binding.etSearch.setOnFocusChangeListener(this);
        binding.ibtPerson.setOnFocusChangeListener(this);
        binding.ibtLive.setOnFocusChangeListener(this);
        binding.ibtNews.setOnFocusChangeListener(this);
        binding.ibtMovies.setOnFocusChangeListener(this);
        binding.ibtMusic.setOnFocusChangeListener(this);
        binding.ibtSports.setOnFocusChangeListener(this);
        binding.ibtRadios.setOnFocusChangeListener(this);
        binding.ibt1.setOnFocusChangeListener(this);
        binding.ibt2.setOnFocusChangeListener(this);
        binding.ibtFavorite.setOnFocusChangeListener(this);

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            Zoom.zoomIn10to11(v);
        }else{
            Zoom.zoomIn11to10(v);
        }
    }
}
