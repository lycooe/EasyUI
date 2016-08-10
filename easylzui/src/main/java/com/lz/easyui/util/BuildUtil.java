package com.lz.easyui.util;

import android.content.Context;

import java.util.Locale;

public class BuildUtil {

    public static String getCPU(){
        return android.os.Build.CPU_ABI;
    }

    public static String getTags(){
        return android.os.Build.TAGS;
    }

    public static int getBase(){
        return android.os.Build.VERSION_CODES.BASE;
    }

    public static String getModel(){
        return android.os.Build.MODEL;
    }

    public static int getSDK(){
        return android.os.Build.VERSION.SDK_INT;
    }

    public static String getRELEASE(){
        return android.os.Build.VERSION.RELEASE;
    }

    public static String getDEVICE(){
        return android.os.Build.DEVICE;
    }

    public static String getFINGERPRINT(){
        return android.os.Build.FINGERPRINT;
    }

    public static String getDISPLAY(){
        return android.os.Build.DISPLAY;
    }

    public static String getMANUFACTURER(){
        return android.os.Build.MANUFACTURER;
    }

    public static String getBRAND(){
        return android.os.Build.BRAND;
    }

    public static String getLanguage(Context context){
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getDisplayLanguage();
        return language;
    }

    public static String getCountry(Context context){
        Locale locale = context.getResources().getConfiguration().locale;
        String country = locale.getDisplayCountry();
        return country;
    }

    public static String getZone(Context context){
        //todo 未获取到地区
        Locale locale = context.getResources().getConfiguration().locale;
        String country = locale.getDisplayCountry();
        return country;
    }
}
