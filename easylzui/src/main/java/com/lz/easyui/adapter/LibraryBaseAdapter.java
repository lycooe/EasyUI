package com.lz.easyui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lz.easyui.util.CheckTool;
import com.lz.easyui.util.RelayoutViewTool;

import java.util.ArrayList;
import java.util.List;

public abstract class LibraryBaseAdapter<T> extends BaseAdapter {

    protected Context ctx;
    protected List<T> libraryAdapterList = new ArrayList<T>();
    private ReleaseListener releaseListener;

    public LibraryBaseAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public LibraryBaseAdapter(Context ctx, List<T> list) {
        this.ctx = ctx;
        resetData(list);
    }

    public List<T> getLibraryAdapterList() {
        return libraryAdapterList;
    }

    @Override
    public int getCount() {
        return libraryAdapterList.size();
    }

    @Override
    public T getItem(int position) {
        return libraryAdapterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getItemView();
            if (isRelayout()) {
                RelayoutViewTool.relayoutViewWithScale(convertView, ctx.getResources().getDisplayMetrics().widthPixels);
            }
        }
        initItemView(position, convertView, parent);
        return convertView;
    }

    public abstract View getItemView();

    public abstract void initItemView(int position, View convertView, ViewGroup parent);

    public void resetData(List<T> tList) {
        if (CheckTool.isEmpty(tList)) {
            tList = new ArrayList<T>();
        }
        if (releaseListener != null) {
            releaseListener.listStopRefresh();
            releaseListener.listShowOrHideFooter(tList.size() > pageCount());
        }
        this.libraryAdapterList.clear();
        this.libraryAdapterList.addAll(tList);
        notifyDataSetChanged();
    }

    public void addData(List<T> tList) {
        if (CheckTool.isEmpty(tList)) {
            tList = new ArrayList<T>();
        }
        if (releaseListener != null) {
            releaseListener.listStopRefresh();
            releaseListener.listShowOrHideFooter(tList.size() > pageCount());
        }
        this.libraryAdapterList.addAll(tList);
        notifyDataSetChanged();
    }

    public void startActivity(Intent it) {
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(it);
    }

    public void setReleaseListener(ReleaseListener releaseListener) {
        this.releaseListener = releaseListener;
    }

    public interface ReleaseListener {
        public void listStopRefresh();

        public void listShowOrHideFooter(boolean show);
    }

    protected int pageCount() {
        return 10;
    }

    protected boolean isRelayout() {
        return false;
    }
}
