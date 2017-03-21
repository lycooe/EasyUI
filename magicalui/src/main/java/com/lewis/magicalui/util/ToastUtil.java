package com.lewis.magicalui.util;

import android.widget.Toast;

import com.lewis.magicalui.MagicalUI;

public class ToastUtil {

    private static boolean ISSHOW = false;

    public static void setDebugMode(boolean isShow) {
        ISSHOW = isShow;
    }

    public static void debug(final String msg) {
        if (ISSHOW)
            toast("调试：" + msg);
    }

    public static void toast(final String msg) {
        toast(msg, Toast.LENGTH_SHORT);
    }

    public static void toast(String msg, int time){
        Toast.makeText(MagicalUI.ctx, msg, time).show();
    }
}
