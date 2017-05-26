package com.wiatec.bplay.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.wiatec.bplay.Application;
import com.wiatec.bplay.F;
import com.wiatec.bplay.R;
import com.wiatec.bplay.animator.Zoom;
import com.wiatec.bplay.databinding.ActivityMovieBinding;
import com.wiatec.bplay.presenter.BasePresenter;
import com.wiatec.bplay.utils.AppUtils;

/**
 * Created by patrick on 23/05/2017.
 * create time : 1:46 PM
 */

public class MovieActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    private ActivityMovieBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        binding = DataBindingUtil.setContentView(this ,R.layout.activity_movie);
        binding.setOnEvent(new OnEventListener());
        binding.ibtTvHouse.setOnFocusChangeListener(this);
        binding.ibtShowBox.setOnFocusChangeListener(this);
    }

    public class OnEventListener{
        public void onClick(View view){
            switch (view.getId()){
                case R.id.ibt_tv_house:
                    if(AppUtils.isInstalled(MovieActivity.this , F.package_name.tv_house)) {
                        AppUtils.launchApp(MovieActivity.this, F.package_name.tv_house);
                    }else{
                        Toast.makeText(Application.getContext() , getString(R.string.notice1)+" TV House" , Toast.LENGTH_LONG).show();
                        AppUtils.launchApp(MovieActivity.this, F.package_name.market);
                    }
                    finish();
                    break;
                case R.id.ibt_show_box:
                    if(AppUtils.isInstalled(MovieActivity.this , F.package_name.show_box)) {
                        AppUtils.launchApp(MovieActivity.this, F.package_name.show_box);
                    }else{
                        Toast.makeText(Application.getContext() , getString(R.string.notice1)+" Show Box" , Toast.LENGTH_LONG).show();
                        AppUtils.launchApp(MovieActivity.this, F.package_name.market);
                    }
                    finish();
                    break;
            }
        }
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
