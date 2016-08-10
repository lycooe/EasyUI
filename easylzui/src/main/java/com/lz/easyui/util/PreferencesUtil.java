package com.lz.easyui.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.lz.easyui.EasyUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class PreferencesUtil {

    private final static String TAG = "PreferencesUtil";

    public static <T> void putPreferences(String key, T value) {
        SharedPreferences.Editor editor = EasyUI.preferences.edit();
        if (value instanceof String) {
            editor.putString(key, value.toString());
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, ((Boolean) value).booleanValue());
        } else if (value instanceof Integer) {
            editor.putInt(key, ((Integer) value).intValue());
        } else if (value instanceof Float) {
            editor.putFloat(key, ((Float) value).floatValue());
        } else if (value instanceof Long) {
            editor.putLong(key, ((Long) value).longValue());
        }
        editor.commit();
    }

    public static <T> T getPreferences(String key, T value) {
        Object o = null;
        if (value instanceof String) {
            o = EasyUI.preferences.getString(key, value.toString());
        } else if (value instanceof Boolean) {
            o = EasyUI.preferences.getBoolean(key, ((Boolean) value).booleanValue());
        } else if (value instanceof Integer) {
            o = EasyUI.preferences.getInt(key, ((Integer) value).intValue());
        } else if (value instanceof Float) {
            o = EasyUI.preferences.getFloat(key, ((Float) value).floatValue());
        } else if (value instanceof Long) {
            o = EasyUI.preferences.getLong(key, ((Long) value).longValue());
        }
        T t = (T) o;
        return t;
    }

    public static void putFilePreferences(Context context, String fileName, String value) {
        try {
            FileOutputStream fout = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            byte[] bytes = value.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getFilePreferences(Context context, String fileName) {
        String value = null;
        try {
            File f = context.getFileStreamPath(fileName);
            if (f == null || !f.exists()) {
                EasyLog.w(TAG, String.format("old sdk doesn't have preference file [%s]", fileName));
                return null;
            }

            try {
                FileInputStream fis = context.openFileInput(fileName);
                byte[] b = new byte[fis.available()];//新建一个字节数组
                fis.read(b);//将文件中的内容读取到字节数组中
                fis.close();
                value = new String(b);//再将字节数组中的内容转化成字符串形式输出
            } catch (Exception e) {
                EasyLog.e(TAG, "read file err", e);
            }
        } catch (Throwable e) {
            EasyLog.e(TAG, "permission err", e);
        }
        return value;
    }

    public static void putSDCardFile(String fileName, String msg) {
        try {
            File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/." + EasyUI.pkgName + "/");
            if (!path.exists()) {
                path.mkdirs();
            }
            FileOutputStream fout = new FileOutputStream(path.getPath() + "/" + fileName);
            byte[] bytes = msg.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (Throwable e) {
            throw new RuntimeException("err in write sd file", e);
        }
    }

    public static String getSDCardFile(String fileName) {
        String msg = null;
        try {
            FileInputStream fis = new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/." + EasyUI.pkgName + "/" + fileName);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            fis.close();
            msg = new String(b);
        } catch (Throwable e) {
            EasyLog.e(TAG, "get SD card file err", e);
        }
        return msg;
    }

}
