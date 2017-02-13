package com.wiatec.bplay.utils.OkHttp.Request;


import com.wiatec.bplay.utils.OkHttp.Download.DownloadCallback;
import com.wiatec.bplay.utils.OkHttp.Download.DownloadInfo;
import com.wiatec.bplay.utils.OkHttp.Download.DownloadListener;
import com.wiatec.bplay.utils.OkHttp.Listener.UploadListener;
import com.wiatec.bplay.utils.OkHttp.OkMaster;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;

/**
 * Created by patrick on 2016/12/20.
 */

public abstract class RequestMaster {
    private Header header;
    private Parameters parameters;
    private Object mTag;
    private Map<Object ,Call> callMap = new ConcurrentHashMap<>();
    protected DownloadInfo mDownloadInfo;

    public RequestMaster() {
        parameters = new Parameters();
        header = new Header();
    }

    public RequestMaster tag(Object tag){
        this.mTag = tag;
        return this;
    }

    public RequestMaster parames(String key , String  value){
        if(key != null && value != null) {
            parameters.put(key, value);
        }
        return this;
    }

    public RequestMaster parames(String key , File value){
        if(key != null && value != null) {
            parameters.put(key, value);
        }
        return this;
    }

    public RequestMaster parames(Parameters parameters){
        this.parameters = parameters;
        return this;
    }

    public RequestMaster headers(String key ,String value){
        if(key != null && value != null) {
            header.put(key, value);
        }
        return this;
    }

    public RequestMaster headers(Header header){
        this.header = header;
        return this;
    }

    protected abstract Request createRequest(Header header, Parameters parameters ,Object tag);
    //异步执行请求
    public void enqueue (Callback callback){
        Request request = createRequest(header,parameters ,mTag);
        Call call = OkMaster.okHttpClient.newCall(request);
        call.enqueue(callback);
        if(mTag!=null) {
            callMap.put(mTag, call);
        }
    }
    //异步执行下载
    public void download (DownloadListener downloadListener){
        Request request = createRequest(header , parameters ,mTag);
        Call call = OkMaster.okHttpClient.newCall(request);
        call.enqueue(new DownloadCallback(mDownloadInfo ,downloadListener));
        if(mTag!=null) {
            callMap.put(mTag, call);
        }
    }
    public void upload(UploadListener uploadListener){
        Request request = createRequest(header , parameters ,mTag);
        Call call = OkMaster.okHttpClient.newCall(request);
        call.enqueue(uploadListener);
    }
    //通过标签取消请求
    public void cancel (Object tag){
        Call call = callMap.get(tag);
        if(call != null) {
            call.cancel();
        }
    }
    //取消所有请求
    public void cancelAll(){
        OkMaster.okHttpClient.dispatcher().cancelAll();
    }
}
