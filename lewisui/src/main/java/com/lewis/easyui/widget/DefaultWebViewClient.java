package com.lewis.easyui.widget;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.lang.ref.WeakReference;

public class DefaultWebViewClient extends WebViewClient {

    private WeakReference<Activity> mActivityWeakReference;

    public DefaultWebViewClient(Activity activity) {
        this.mActivityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if (url.startsWith("farm:")) {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            mActivityWeakReference.get().startActivity(intent);

            return true;
        }
        view.loadUrl(url);
        return true;
    }
}
