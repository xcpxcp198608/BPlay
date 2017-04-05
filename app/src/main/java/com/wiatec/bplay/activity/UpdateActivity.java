package com.wiatec.bplay.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import com.wiatec.bplay.R;
import com.wiatec.bplay.beans.UpdateInfo;
import com.wiatec.bplay.databinding.ActivityUpdateBinding;
import com.wiatec.bplay.utils.AppUtils;
import com.wiatec.bplay.utils.OkHttp.Bean.DownloadInfo;
import com.wiatec.bplay.utils.OkHttp.Listener.DownloadListener;
import com.wiatec.bplay.utils.OkHttp.OkMaster;
import com.wiatec.bplay.utils.OkHttp.Request.RequestMaster;

/**
 * Created by patrick on 2017/2/17.
 */

public class UpdateActivity extends AppCompatActivity {

    private ActivityUpdateBinding binding;
    private UpdateInfo updateInfo;
    private String path;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this ,R.layout.activity_update);
        updateInfo = getIntent().getParcelableExtra("updateInfo");
        path = getExternalFilesDir("update").getAbsolutePath();
    }

    @Override
    protected void onStart() {
        super.onStart();
        OkMaster.download(UpdateActivity.this)
                .url(updateInfo.getUrl())
                .path(path)
                .tag("update")
                .startDownload(new DownloadListener() {
                    @Override
                    public void onPending(DownloadInfo downloadInfo) {

                    }

                    @Override
                    public void onStart(DownloadInfo downloadInfo) {
                        binding.tvProgress.setText("0%");
                        binding.progressBar.setProgress(0);
                    }

                    @Override
                    public void onPause(DownloadInfo downloadInfo) {

                    }

                    @Override
                    public void onProgress(DownloadInfo downloadInfo) {
                        binding.tvProgress.setText(downloadInfo.getProgress()+"%");
                        binding.progressBar.setProgress(downloadInfo.getProgress());
                    }

                    @Override
                    public void onFinished(DownloadInfo downloadInfo) {
                        binding.tvProgress.setText("100%");
                        binding.progressBar.setProgress(100);
                        AppUtils.installApk(UpdateActivity.this,path,downloadInfo.getName());
                    }

                    @Override
                    public void onCancel(DownloadInfo downloadInfo) {

                    }

                    @Override
                    public void onError(DownloadInfo downloadInfo) {

                    }
                });
    }
}
