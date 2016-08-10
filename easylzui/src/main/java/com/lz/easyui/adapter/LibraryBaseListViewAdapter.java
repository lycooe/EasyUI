package com.lz.easyui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lz.easyui.util.CheckTool;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public abstract class LibraryBaseListViewAdapter<DataSource, ViewHolder> extends BaseAdapter {

    protected List<DataSource> cheeseAdapterList = new ArrayList<DataSource>();
    private ViewHolder holder;
    private Context ctx;
    private ReleaseListener releaseListener;

    public LibraryBaseListViewAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return cheeseAdapterList.size();
    }

    @Override
    public DataSource getItem(int position) {
        return cheeseAdapterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), null);
            holder = onCreateViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        onBindViewHolder(holder, getItem(position), position);

        return convertView;
    }

    protected abstract int getLayoutId();

    protected abstract ViewHolder onCreateViewHolder(View view);

    protected abstract void onBindViewHolder(ViewHolder viewHolder, DataSource data, int position);

    public void resetData(List<DataSource> tList) {
        this.cheeseAdapterList.clear();
        if (!CheckTool.isEmpty(tList)) {
            this.cheeseAdapterList.addAll(tList);
        }

        if (releaseListener != null) {
            releaseListener.listStopRefresh();
            releaseListener.listShowOrHideFooter(tList.size() >= total());
        }

        notifyDataSetChanged();
    }

    public void addData(List<DataSource> tList) {
        if (!CheckTool.isEmpty(tList)) {
            this.cheeseAdapterList.addAll(tList);
        }
        if (releaseListener != null) {
            releaseListener.listStopRefresh();
            releaseListener.listShowOrHideFooter(tList.size() >= total());
        }
        notifyDataSetChanged();
    }

    public void insertData(List<DataSource> tList) {
        if (!CheckTool.isEmpty(tList)) {
            this.cheeseAdapterList.addAll(0, tList);
        }
        notifyDataSetChanged();
    }

    public void removeData(List<DataSource> tList) {
        if (!CheckTool.isEmpty(tList)) {
            this.cheeseAdapterList.removeAll(tList);
        }
        notifyDataSetChanged();
    }

    public void removeData(DataSource t) {
        if (!CheckTool.isEmpty(t)) {
            this.cheeseAdapterList.remove(t);
        }
        notifyDataSetChanged();
    }

    public void removeData(int index) {
        this.cheeseAdapterList.remove(index);
        notifyDataSetChanged();
    }

    public List<DataSource> getDataSource() {
        return cheeseAdapterList;
    }

    public Context getAdapterContext() {
        return ctx;
    }

    public void startActivity(Intent it) {
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getAdapterContext().startActivity(it);
    }

    public int total(){
        return 20;
    }

    public void setReleaseListener(ReleaseListener releaseListener) {
        this.releaseListener = releaseListener;
    }

    public interface ReleaseListener {
        void listStopRefresh();

        void listShowOrHideFooter(boolean show);
    }
}
