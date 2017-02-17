package com.wiatec.bplay.presenter;

import com.wiatec.bplay.data.ChannelData;
import com.wiatec.bplay.data.IChannelData;

import java.lang.ref.WeakReference;

/**
 * Created by patrick on 2017/1/13.
 */

public abstract class BasePresenter<V> {
    private WeakReference<V> weakReference;
    protected IChannelData iChannelData;

    public void attachView(V v){
        weakReference = new WeakReference<V>(v);
        if(iChannelData == null) {
            iChannelData = new ChannelData();
        }
    }

    public void detachView(){
        if(weakReference != null){
            weakReference.clear();
            weakReference = null;
        }
    }

    public V getView(){
        return weakReference.get();
    }
}
