package com.wiatec.bplay.utils.OkHttp;

import android.content.Context;


import com.wiatec.bplay.utils.OkHttp.Request.DownloadRequest;
import com.wiatec.bplay.utils.OkHttp.Request.GetRequest;
import com.wiatec.bplay.utils.OkHttp.Request.PostRequest;
import com.wiatec.bplay.utils.OkHttp.Request.UploadRequest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by patrick on 2016/12/20.
 */

public class OkMaster {

    public static OkHttpClient okHttpClient;

    /**
     * okhttp client init
     */
    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30,TimeUnit.SECONDS);
        builder.readTimeout(30,TimeUnit.SECONDS);
        okHttpClient = builder.build();
    }

    /**
     * get request
     * @param url request url
     * @return get request
     */
    public static GetRequest get(String url){
        return new GetRequest(url);
    }

    /**
     * post request
     * @param url request url
     * @return post request
     */
    public static PostRequest post(String url){
        return new PostRequest(url);
    }

    /**
     * upload request
     * @param url request url
     * @return upload request
     */
    public static UploadRequest upload(String url){
        return new UploadRequest(url);
    }

    /**
     * download request
     * @param context context
     * @return download request
     */
    public static DownloadRequest download (Context context) {
        return new DownloadRequest(context);
    }
}
