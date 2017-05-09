package com.lewis.easyui.widget;

import android.content.Context;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class DefaultWebView extends WebView {
    public DefaultWebView(Context context) {
        super(context);
    }

    private Handler handler = new Handler();
    private Runnable runnable;

    private void computeHeight() {
        loadUrl("javascript:Resize.fetchHeight(document.body.getBoundingClientRect().height)");
    }

    @JavascriptInterface
    public void fetchHeight(final float height) {
        final int newHeight = (int) (height * getResources().getDisplayMetrics().density);
        runnable = new Runnable() {
            @Override
            public void run() {
                if (getLayoutParams() instanceof LinearLayout.LayoutParams) {
                    LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) getLayoutParams();
                    linearParams.height = newHeight;
                    setLayoutParams(linearParams);
                }
            }
        };
        if (null != handler) {
            handler.postDelayed(runnable, 50);
        }
    }
}
