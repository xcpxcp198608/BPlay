package com.wiatec.bplay.utils.OkHttp.Download;

import android.content.Context;

/**
 * Created by patrick on 2016/12/23.
 */

public class DownloadMaster {

    public static DownloadRequest with (Context context){
        return new DownloadRequest(context);
    }


}
