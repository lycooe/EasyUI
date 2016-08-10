package com.lz.easyui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.lz.easyui.util.RelayoutViewTool;

public abstract class LibraryBaseExpandableListAdapter extends BaseExpandableListAdapter {


    protected Context ctx;

    public LibraryBaseExpandableListAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public abstract View getGroupView();

    public abstract View getChildView();

    public abstract void initChildData(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent);

    public abstract void initGroupData(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent);

    @Override
    public final View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getGroupView();
            if (isRelayout()) {
                RelayoutViewTool.relayoutViewWithScale(convertView, ctx.getResources().getDisplayMetrics().widthPixels);
            }
        }
        initGroupData(groupPosition, isExpanded, convertView, parent);
        return convertView;
    }

    @Override
    public final View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getChildView();
            if (isRelayout()) {
                RelayoutViewTool.relayoutViewWithScale(convertView, ctx.getResources().getDisplayMetrics().widthPixels);
            }
        }
        initChildData(groupPosition, childPosition, isLastChild, convertView, parent);
        return convertView;
    }


    public void startActivity(Intent it) {
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(it);
    }

    protected boolean isRelayout() {
        return false;
    }
}
