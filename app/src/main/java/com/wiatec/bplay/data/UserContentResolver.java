package com.wiatec.bplay.data;

import android.content.ContentResolver;
import android.net.Uri;

import com.wiatec.bplay.Application;
import com.wiatec.bplay.utils.Logger;

/**
 * Created by patrick on 27/05/2017.
 * create time : 10:43 AM
 */

public class UserContentResolver {

    private static final ContentResolver contentResolver = Application.getContext().getContentResolver();
    private static final String AUTH = "content://com.wiatec.btv_launcher.provide.UserContentProvider/user/";

    public static String get(String key){
        String u = AUTH + key;
        Logger.d(u);
        Uri uri = Uri.parse(u);
        return contentResolver.getType(uri);
    }
}
