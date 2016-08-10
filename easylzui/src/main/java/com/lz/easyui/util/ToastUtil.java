package com.lz.easyui.util;

import android.widget.Toast;

import com.lz.easyui.EasyUI;

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
        Toast.makeText(EasyUI.ctx, msg, time).show();
    }
}
