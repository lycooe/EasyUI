package com.lz.easyui.widget;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import com.lz.easyui.util.EasyLog;
import com.lz.easyui.util.RelayoutViewTool;


/**
 * title bar
 */
public abstract class LibraryActionBar {

    private Activity act;
    private View view;
    private OnNavigationClickListener onNavigationListener;

    public LibraryActionBar(Activity act, int layoutID) {
        this(act, layoutID, false);
    }

    public LibraryActionBar(Activity act, int layoutID, boolean relayout) {
        if (act == null || layoutID <= 0) {
            throw new RuntimeException("[Library] act and layoutID mast have value");
        }
        if (act.getActionBar() == null) {
            EasyLog.d("[Library] can't get ActionBar");
            return;
        }
        this.act = act;

        act.getActionBar().setDisplayShowHomeEnabled(false);
        act.getActionBar().setTitle("");
        act.getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        view = View.inflate(act.getApplicationContext(), layoutID, null);

        if (relayout) {
            RelayoutViewTool.relayoutViewWithScale(view, act.getApplicationContext().getResources().getDisplayMetrics().widthPixels);
        }

        initWidget(view);

        act.getActionBar().setCustomView(view);
    }

    public Context getContext() {
        if (act == null) {
            return null;
        }
        return act.getApplicationContext();
    }

    public interface OnNavigationClickListener {
        public void onNavigationItemClick(int itemPosition, int itemId);
    }

    public void setOnNavigationListener(OnNavigationClickListener onNavigationListener) {
        this.onNavigationListener = onNavigationListener;
    }

    protected void addClickListener(View view, final int itemPosition) {
        if (view == null) {
            throw new RuntimeException("view is null");
        }
        if (onNavigationListener == null) {
            throw new RuntimeException("must first call `setOnNavigationListener`");
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendListener(itemPosition, v.getId());
            }
        });
    }

    protected void sendListener(int itemPosition, int itemId) {
        if (onNavigationListener != null) {
            onNavigationListener.onNavigationItemClick(itemPosition, itemId);
        }
    }

    public void setBackgroundColor(int color) {
        if (view != null) {
            view.setBackgroundColor(color);
        }
    }

    public void setBackgroundResource(int resid) {
        if (view != null) {
            view.setBackgroundResource(resid);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setBackground(Drawable background) {
        if (view != null) {
            view.setBackground(background);
        }
    }

    protected abstract void initWidget(View view);
}
