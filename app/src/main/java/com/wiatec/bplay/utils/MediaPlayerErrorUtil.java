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
                    return "network related operation errors";
                case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                    return "Media server died";
                case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                    return "Some operation takes too long to complete";
                case MediaPlayer.MEDIA_ERROR_MALFORMED:
                    return "Bitstream is not conforming to the related coding standard";
                case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                    return "Unspecified media player error";
                case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                    return "does not support the feature";
                default:
                    return "Unspecified media player error";
            }
        }else{
            return "";
        }
    }
}
