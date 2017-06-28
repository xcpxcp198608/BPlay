package com.wiatec.bplay.play;

import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.data.UserContentResolver;
import com.wiatec.bplay.utils.AESUtil;
import com.wiatec.bplay.utils.Logger;
import com.wiatec.bplay.utils.OkHttp.Listener.StringListener;
import com.wiatec.bplay.utils.OkHttp.OkMaster;

import java.io.IOException;
import java.util.List;

/**
 * Created by patrick on 28/06/2017.
 * create time : 9:10 AM
 */

public class PlayManager {

    private List<ChannelInfo> channelInfoList;
    private int currentPosition;
    private ChannelInfo channelInfo;
    private boolean isLock;
    private PlayListener mPlayListener;
    private int level;

    public PlayManager(List<ChannelInfo> channelInfoList, int currentPosition, boolean isLock) {
        this.channelInfoList = channelInfoList;
        this.currentPosition = currentPosition;
        this.isLock = isLock;
        channelInfo = channelInfoList.get(currentPosition);
        String levelStr = UserContentResolver.get("userLevel");
        try {
            level = Integer.parseInt(levelStr);
        }catch (Exception e){
            level = 1;
        }
    }

    public interface PlayListener{
        void play(String url);
        void playAd();
        void launchApp(String packageName);
    }
    public void setPlayListener(PlayListener playListener){
        mPlayListener = playListener;
    }

    public ChannelInfo getChannelInfo(){
        return channelInfo;
    }

    public void dispatchChannel(){
        String type = channelInfo.getType();
        String url = AESUtil.decrypt(channelInfo.getUrl(), AESUtil.key);
        if("live".equals(type)){
            if(isLock){
                if(level > 1){
                    if(mPlayListener != null){
                        mPlayListener.play(url);
                    }
                }else{
                    String experience = UserContentResolver.get("experience");
                    if("true".equals(experience)){
                        if(mPlayListener != null){
                            mPlayListener.play(url);
                        }
                    }else{
                        if(mPlayListener != null){
                            mPlayListener.playAd();
                        }
                    }
                }
            }else{
                if(mPlayListener != null){
                    mPlayListener.play(url);
                }
            }
        }else if("relay".equals(type)){
            if(isLock){
                if(level > 1){
                    relayUrl(url);
                }else{
                    String experience = UserContentResolver.get("experience");
                    if("true".equals(experience)){
                        relayUrl(url);
                    }else{
                        if(mPlayListener != null){
                            mPlayListener.playAd();
                        }
                    }
                }
            }else{
                relayUrl(url);
            }
        }else if("app".equals(type)){
            if(mPlayListener != null){
                mPlayListener.launchApp(AESUtil.decrypt(channelInfo.getUrl(), AESUtil.key));
            }
        }else{
            Logger.d("type error");
        }
    }

    public void relayUrl(String url){
        OkMaster.get(url)
                .enqueue(new StringListener() {
                    @Override
                    public void onSuccess(String s) throws IOException {
                        if(s != null){
                            if(mPlayListener != null){
                                mPlayListener.play(s);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String e) {
                        Logger.d(e);
                    }
                });
    }

    public void previousChannel(){
        currentPosition -- ;
        if(currentPosition < 0){
            currentPosition = channelInfoList.size()-1;
        }
        Logger.d(currentPosition);
        channelInfo = channelInfoList.get(currentPosition);
        dispatchChannel();
    }

    public void nextChannel(){
        currentPosition ++ ;
        if(currentPosition >= channelInfoList.size()){
            currentPosition = 0;
        }
        Logger.d(currentPosition);
        channelInfo = channelInfoList.get(currentPosition);
        dispatchChannel();
    }
}
