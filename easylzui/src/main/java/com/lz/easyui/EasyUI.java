package com.lz.easyui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

/**
 *
 */
public class EasyUI {

    public static Context ctx;

    public static SharedPreferences preferences;

    public static DisplayMetrics displayMetrics;

    public static int screenWidthScale;

    public static int screenHeightScale;

    public static Resources resource;

    public static String pkgName;

    public static String app_identity;

    public static void init(Context context) {
        ctx = context;

        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        resource = context.getResources();

        pkgName = context.getPackageName();

        displayMetrics = context.getResources().getDisplayMetrics();
        screenWidthScale = displayMetrics.widthPixels;
        screenHeightScale = displayMetrics.heightPixels;
    }
}
