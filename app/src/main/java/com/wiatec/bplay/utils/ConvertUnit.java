package com.wiatec.bplay.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by patrick on 2017/1/12.
 */

public class ConvertUnit {

    /**
     * 像素值转化为dp值
     * @param context 上下文
     * @param px 像素
     * @return dp
     */
    public static int px2dp (Context context , int px){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP , px , context.getResources().getDisplayMetrics());
    }

    /**
     * 像素转化为sp
     * @param context 上下文
     * @param px 像素
     * @return sp
     */
    public static int px2sp (Context context , int px){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP , px ,context.getResources().getDisplayMetrics());
    }

    /**
     * 开氏温度转为摄氏温度
     * @param kelvin 开氏温度
     * @return 摄氏温度
     */
    public static float kelvinToCelsius (float kelvin){
        float t = kelvin - 273.15f;
        return (float)(Math.round(t*10))/10;
    }

    /**
     * 摄氏温度转为华氏温度
     * @param celsius 摄氏温度
     * @return 华氏温度
     */
    private int celsiusToFahrenheit (float celsius){
        return (int) ((1.8f+celsius)+32);
    }
}
