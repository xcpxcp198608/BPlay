package com.wiatec.bplay.presenter;

import java.lang.ref.WeakReference;

/**
 * Created by patrick on 2017/1/13.
 */

public abstract class BasePresenter<V> {
    private WeakReference<V> weakReference;

    public void attachView(V v){
        weakReference = new WeakReference<V>(v);
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
