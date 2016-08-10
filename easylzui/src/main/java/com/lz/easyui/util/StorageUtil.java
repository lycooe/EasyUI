package com.lz.easyui.util;

import android.content.res.AssetManager;
import android.os.Environment;


import com.lz.easyui.EasyUI;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class StorageUtil {

    public static final String ROOT_ASSET = "/android_asset/";

    public static boolean isSdcardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String getCache() {
        return EasyUI.ctx.getCacheDir() + "/" + EasyUI.app_identity + "-" + System.currentTimeMillis();
    }

    public static String getTemp() {
        return EasyUI.ctx.getCacheDir() + "/temp/" + EasyUI.app_identity + "-" + System.currentTimeMillis();
    }

    public static String getStorageDirectory() {
        return Environment.getExternalStorageDirectory() + "/." + EasyUI.app_identity;
    }

    public static String getStorage(String content, long id, boolean isDirectory) {
        String external = Environment.getExternalStorageDirectory().getPath();

        external += "/." + EasyUI.app_identity + "/Content/" + content + id;

        if (isDirectory) {
            external += "/";
        }

        return external;
    }

    public static void cleanAll() {
        try {
            if (new File(EasyUI.ctx.getCacheDir() + "/").exists())
                FileUtils.cleanDirectory(new File(EasyUI.ctx.getCacheDir() + "/"));
            String external = Environment.getExternalStorageDirectory().getPath();
            if (new File(external + "/." + EasyUI.app_identity + "/Content/").exists())
                FileUtils.cleanDirectory(new File(external + "/." + EasyUI.app_identity + "/Content/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] listFiles(String path, String suffix) {
        if (path.startsWith(ROOT_ASSET)) {
            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }

            AssetManager assetManager = EasyUI.ctx.getAssets();

            String[] files = new String[0];

            try {
                files = assetManager.list(path.substring(ROOT_ASSET.length()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<String> result = new ArrayList<String>();

            for (int i = 0; i < files.length; i++) {
                if (files[i].endsWith(suffix)) {
                    result.add(path + "/" + files[i]);
                }
            }

            return result.toArray(new String[result.size()]);
        } else {

            ArrayList<String> result = new ArrayList<String>();
            Collection<File> files = FileUtils.listFiles(new File(path), suffix);
            for (File file : files) {
                if (!file.getName().startsWith(".")) {
                    result.add(file.getAbsolutePath());
                }
            }
            //对图片地址按照文件名称(数字)进行排序（升序）
            String[] picPathArray = result.toArray(new String[result.size()]);
            Arrays.sort(picPathArray, new Comparator<Object>() {
                @Override
                public int compare(Object o1, Object o2) {
                    String path1 = (String) o1;
                    String path2 = (String) o2;
                    long pic1NameNum = getPicNameNm(path1);
                    long pic2NameNum = getPicNameNm(path1);
                    if (pic1NameNum > pic2NameNum) {
                        return -1;
                    } else if (pic1NameNum < pic2NameNum) {
                        return 1;
                    } else {
                        return path1.compareTo(path2);
                    }
                }

                private Long getPicNameNm(String path1) {
                    try {
                        int nameStartIndex = path1.lastIndexOf("/");
                        int nameEndIndex = path1.lastIndexOf(".");
                        String fileName = path1.substring(nameStartIndex + 1, nameEndIndex);
                        return Long.parseLong(fileName);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    return -1l;
                }
            });

            return picPathArray;
        }
    }

    public static InputStream openInputStream(String file) throws IOException {
        if (file.startsWith(ROOT_ASSET)) {
            AssetManager assetManager = EasyUI.ctx.getAssets();
            return assetManager.open(file.substring(ROOT_ASSET.length()));
        } else {
            return FileUtils.openInputStream(new File(file));
        }
    }

    public static boolean deleteFile(String file) {
        if (!file.startsWith(ROOT_ASSET)) {
            return FileUtils.deleteQuietly(new File(file));
        }

        return true;
    }

    public static boolean deleteDirectory(String file) throws IOException {
        if (!file.startsWith(ROOT_ASSET)) {
            FileUtils.forceDelete(new File(file));
        }

        return true;
    }

    public static void cleanCache() throws IOException {
        FileUtils.cleanDirectory(EasyUI.ctx.getCacheDir());
    }

}
