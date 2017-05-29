package com.wiatec.bplay.utils;

import android.media.MediaPlayer;

/**
 * Created by patrick on 26/05/2017.
 * create time : 10:20 AM
 */

public class MediaPlayerErrorUtil {

    public static String getError (int what ){
        if(what > 0 ) {
            switch (what) {
                case MediaPlayer.MEDIA_ERROR_IO:
                    return "Channel error code 001, please check back later";
                case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                    return "Channel error code 002, please check back later";
                case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                    return "Channel time out, please check back later";
                case MediaPlayer.MEDIA_ERROR_MALFORMED:
                    return "Channel error code 003, please check back later";
                case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                    return "Channel error code 004, please check back later";
                case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                    return "Channel error code 005, please check back later";
                default:
                    return "Channel error code 006, please check back later";
            }
        }else{
            return "";
        }
    }
}
