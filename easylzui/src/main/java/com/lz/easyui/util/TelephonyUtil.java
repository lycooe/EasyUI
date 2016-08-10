package com.lz.easyui.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.List;
import java.util.regex.Pattern;

public class TelephonyUtil {

    private static final String TAG = TelephonyUtil.class.getSimpleName();

    public static Intent dial(String paramString) {
        Uri localUri = Uri.parse("tel:" + paramString);
        return new Intent(Intent.ACTION_DIAL, localUri);
    }

    public static Intent call(String paramString) {
        Uri localUri = Uri.parse("tel:" + paramString);
        return new Intent(Intent.ACTION_CALL, localUri);
    }

    public static Intent sendSms(String phoneNumber, String content) {
        Uri localUri = Uri.parse("smsto:" + phoneNumber);
        Intent localIntent = new Intent(Intent.ACTION_SENDTO, localUri);
        localIntent.putExtra("sms_body", content);
        return localIntent;
    }

    public static void sendSms(Context context, String content) {
        Intent localIntent = new Intent("android.intent.action.VIEW");
        localIntent.setType("vnd.android-dir/mms-sms");
        localIntent.putExtra("sms_body", content);

        if (!(context instanceof Activity)) {
            localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(localIntent);
    }


    public static boolean verifyPhoneNumber(String phoneNumber) {
        return Pattern.matches("^1[358][0-9]{9}$", phoneNumber);
    }

    public static void openSourceURL(Activity activity, String title, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        activity.startActivity(Intent.createChooser(intent, title));
    }

    public static void mail(Activity activity, String email, String subject, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);

        if (content != null) {
            intent.putExtra(Intent.EXTRA_TEXT, content);
        }

        final PackageManager manager = activity.getPackageManager();
        final List<ResolveInfo> matches = manager.queryIntentActivities(intent, 0);


        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail")) {
                intent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
            } else if (info.activityInfo.packageName.endsWith(".email") || info.activityInfo.name.toLowerCase().contains("email")) {
                intent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
            }
        }

        activity.startActivity(intent);
    }

}
