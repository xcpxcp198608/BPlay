package com.wiatec.bplay.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.Display;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by patrick on 2016/12/29.
 */

public class SysUtils {

    /**
     * 判断系统是否已Root
     * @return 是否已root
     */
    public static boolean isRoot () {
        Process process = null;
        DataOutputStream dataOutputStream = null;
        try {
            process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataOutputStream.writeBytes("check"+"\n");
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("----px----","system no root");
            return false;
        }finally {
            try {
                if(dataOutputStream != null){
                    dataOutputStream.close();
                }
                if(process != null) {
                    process.destroy();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Log.d("----px----","system have root");
        return true;
    }

    /**
     * 获得当前屏幕宽度
     * @param activity 当前activity
     * @return 当前屏幕宽度
     */
    public static int getScreenWidth(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        //display.getRealSize(point);
        int width = point.x;
        return width;
    }

    /**
     * 获得当前屏幕高度
     * @param activity 当前activity
     * @return 当前屏幕高度
     */
    public static int getScreenHeight(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        //display.getRealSize(point);
        int width = point.y;
        return width;
    }

    /**
     * 获得当前设备的wifi mac地址
     * 需要 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     * @param context 上下文
     * @return 当前设备wifi的mac地址
     */
    public static String getWifiMac1(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getMacAddress();
    }

    /**
     * 获得当前设备的wifi mac地址
     * @return 当前设备wifi的mac地址
     */
    public static String getWifiMac() {
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            String line;
            while ((line = input.readLine()) != null) {
                macSerial += line.trim();
            }

            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return macSerial;
    }

    /**
     * 获得当前系统语言
     * @param context 上下文
     * @return 当前系统设置的语言+国家地区类型
     */
    public static String getLanguage (Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        String country = locale.getCountry();
        //Log.d("----px----" ,language+"_"+country);
        return language+"_"+country;
    }

    /**
     * 获取当前系统日期
     * @return 年-月-日格式返回当前系统日期
     */
    public static String getDate () {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当前系统时间
     * @return 小时：分钟 格式返回当前系统时间
     */
    public static String getTime () {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(date);
    }
}
