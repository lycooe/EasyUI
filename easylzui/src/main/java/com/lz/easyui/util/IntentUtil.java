package com.lz.easyui.util;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.net.URLEncoder;

public class IntentUtil {

    public static Intent getSmsIntent(String number) {
        return new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + number));
    }

    public static Intent getSmsIntent(String number, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + number));
        intent.putExtra("sms_body", body);
        return intent;
    }

    public static Intent getCallIntent(String number) {
        Intent intent = new Intent();
        //系统默认的action，用来打开默认的电话界面
        intent.setAction(Intent.ACTION_CALL);
        //需要拨打的号码
        intent.setData(Uri.parse("tel:" + number));
        return intent;
    }

    public static Intent getOtherApkStartIntent(String packagePath, String startActivity) {
        Intent mIntent = new Intent();
        ComponentName comp = new ComponentName(packagePath, startActivity);
        mIntent.setComponent(comp);
        mIntent.setAction("android.intent.action.VIEW");
        return mIntent;
    }

    public static Intent makeTakePicturesIntent(File file){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        return intent;
    }

    public static Intent makeSystemAlbumIntent(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        return intent;
    }

    public static Intent getSelectPhoto() {
        Intent intent = new Intent();
        /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
        // intent.setType("audio/*"); //选择音频
        // intent.setType("video/*"); //选择视频
        // intent.setType("video/*;image/*");//同时选择视频和图片
        /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
        /* 取得相片后返回本画面 */
        Intent wrapperIntent = Intent.createChooser(intent, null);
        return wrapperIntent;
    }

    public static Intent startPhotoZoomIntent(Uri uri, Uri toUri, int size){
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
//        intent.setType("image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, toUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        return intent;
    }

    private Intent startCameraZoomIntent(Uri uri, int size){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        return intent;
    }

    public static Intent startPhotoZoomIntent(Bitmap bitmap){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
//        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        intent.putExtra("data", bitmap);
        return intent;
    }

    public static Intent startSinaWeiboSendIntent(String content){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("sinaweibo://sendweibo?content=" + URLEncoder.encode(content)));
        return intent;
    }


}
