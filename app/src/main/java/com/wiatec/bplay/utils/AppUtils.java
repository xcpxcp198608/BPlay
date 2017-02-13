package com.wiatec.bplay.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

/**
 * Created by patrick on 2016/12/28.
 */

public class AppUtils {

    /**
     * 通过包名判断APK是否已安装
     * @param context 上下文
     * @param packageName apk的完整包名
     * @return 是否已安装
     */
    public static boolean isInstalled (Context context , String packageName){
        if(TextUtils.isEmpty(packageName)){
            return false;
        }
        try {
            context.getPackageManager().getPackageInfo(packageName , PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("----px----" , e.getMessage());
            return false;
        }
    }

    /**
     * 通过包名获得已经安装APK的icon
     * @param context 上下文
     * @param packageName apk的完整包名
     * @return icon的drawable
     */
    public static Drawable getIcon (Context context , String packageName){
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(packageName ,PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(applicationInfo != null){
            return applicationInfo.loadIcon(packageManager);
        }else{
            return null;
        }
    }

    /**
     * 通过包名获得已安装APK的label name
     * @param context 上下文
     * @param packageName apk的完整包名
     * @return apk的LABEL (显示的名称)
     */
    public static String getLabelName (Context context , String packageName) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(packageName ,PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(applicationInfo != null){
            return applicationInfo.loadLabel(packageManager).toString();
        }else{
            return null;
        }
    }

    /**
     * 通过包名获得已安装APK的version name
     * @param context 上下文
     * @param packageName apk的完整包名
     * @return 版本号
     */
    public static String getVersionName (Context context , String packageName) {
        if(TextUtils.isEmpty(packageName)){
            return "apkPackageName error";
        }
        String apkVersionName = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName , PackageManager.GET_ACTIVITIES);
            if(packageInfo != null) {
                apkVersionName = packageInfo.versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
//            Log.d("----px----" , e.getMessage());
        }
//        Log.d("----px----" , apkVersionName);
        return apkVersionName;
    }

    /**
     * 通过包名获得已安装APK的version code
     * @param context 上下文
     * @param packageName apk的完整包名
     * @return 版本代号
     */
    public static int getVersionCode (Context context , String packageName) {
        if(TextUtils.isEmpty(packageName)){
            return 0;
        }
        int apkVersionCode = 0;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName , PackageManager.GET_ACTIVITIES);
            if(packageInfo != null) {
                apkVersionCode = packageInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            //Log.d("----px----" , e.getMessage());
        }
        //Log.d("----px----" , apkVersionCode+"");
        return apkVersionCode;
    }

    /**
     * 通过包名和版本编号判断apk是否是最新版本
     * @param context 上下文
     * @param packageName apk的完整包名
     * @param versionCode 参照的正确版本代号
     * @return 是否是最新版本
     */
    public static boolean isLastVersion (Context context ,String packageName ,int versionCode) {
        if(AppUtils.isInstalled(context , packageName)) {
            int localVersionCode = AppUtils.getVersionCode(context,packageName);
            if(versionCode > localVersionCode){
                return true;
            }else {
                return false;
            }
        }else {
            return true;
        }
    }

    /**
     * 获得已存在的apk文件的包名
     * @param context 上下文
     * @param filePath 文件绝对路径
     * @param fileName 文件名称
     * @return apk文件的包名
     */
    public static String getApkPackageName (Context context , String filePath ,String fileName){
        ApplicationInfo applicationInfo = null;
        PackageInfo packageInfo = null;
        PackageManager packageManager = context.getPackageManager();
        String apkPackageName = null;
        packageInfo = packageManager.getPackageArchiveInfo(filePath+fileName ,PackageManager.GET_ACTIVITIES);
        if(packageInfo != null){
            applicationInfo = packageInfo.applicationInfo;
            apkPackageName = applicationInfo.packageName;
        }else{
            return null;
        }
        //Log.d("----px----" ,apkPackageName);
        return apkPackageName;
    }

    /**
     * 获得已经存在的apk文件的版本号
     * @param context 上下文
     * @param filePath 文件绝对路径
     * @param fileName 文件名称
     * @return 版本号
     */
    public static String getApkVersionName (Context context , String filePath ,String fileName){
        ApplicationInfo applicationInfo = null;
        PackageInfo packageInfo = null;
        PackageManager packageManager = context.getPackageManager();
        String apkVersionName = null;
        packageInfo = packageManager.getPackageArchiveInfo(filePath+fileName ,PackageManager.GET_ACTIVITIES);
        if(packageInfo != null){
            apkVersionName = packageInfo.versionName;
        }else{
            return null;
        }
        Log.d("----px----" ,apkVersionName);
        return apkVersionName;
    }

    /**
     * 判断apk文件是否可安装
     * @param context 上下文
     * @param filePath 文件绝对路径
     * @param fileName 文件名称
     * @return 是否可以安装
     */
    public static boolean isApkCanInstall (Context context , String filePath ,String fileName){
        PackageInfo packageInfo = null;
        PackageManager packageManager = context.getPackageManager();
        packageInfo = packageManager.getPackageArchiveInfo(filePath+"/"+fileName ,PackageManager.GET_ACTIVITIES);
        if(packageInfo != null){
            return true;
        }else{
            return false;
        }
    }


    /**
     * 获得APK文件的versionCode
     * @param context 上下文
     * @param filePath 文件绝对路径
     * @param fileName 文件名称
     * @return apk文件的版本代号
     */
    public static int getApkVersionCode (Context context , String filePath ,String fileName){
        ApplicationInfo applicationInfo = null;
        PackageInfo packageInfo = null;
        PackageManager packageManager = context.getPackageManager();
        int apkVersionCode = 0;
        packageInfo = packageManager.getPackageArchiveInfo(filePath+fileName ,PackageManager.GET_ACTIVITIES);
        if(packageInfo != null){
            apkVersionCode = packageInfo.versionCode;
        }else{
            return 0;
        }
        Log.d("----px----" ,apkVersionCode+"");
        return apkVersionCode;
    }

    /**
     * 安装apk文件
     * @param context 上下文
     * @param filePath 文件绝对路径
     * @param fileName 文件名称
     */
    public static void installApk (Context context , String filePath , String fileName){
        File file = new File(filePath, fileName);
        if(!file.exists()) {
            Toast.makeText(context , "Apk file is not exists" ,Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isApkCanInstall(context , filePath ,fileName)){
            Toast.makeText(context , "Apk file can not install" ,Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 通过包名启动已经安装的app
     * @param context 上下文
     * @param packageName apk的包名
     */
    public static void launchApp (Context context ,String packageName){
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if(intent!= null){
            context.startActivity(intent);
        }
    }
}
