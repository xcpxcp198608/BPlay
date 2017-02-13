package com.wiatec.bplay.utils.OkHttp.Download;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by patrick on 2016/12/23.
 */

public interface DownloadListener  {
     void onPending(DownloadInfo downloadInfo);
     void onStart(DownloadInfo downloadInfo);
     void onPause(DownloadInfo downloadInfo);
     void onProgress(DownloadInfo downloadInfo);
     void onFinished(DownloadInfo downloadInfo);
     void onCancel(DownloadInfo downloadInfo);
     void onError(DownloadInfo downloadInfo);

}
