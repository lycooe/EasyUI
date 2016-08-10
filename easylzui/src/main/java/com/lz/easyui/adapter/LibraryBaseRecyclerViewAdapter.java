package com.lz.easyui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lz.easyui.util.CheckTool;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public abstract class LibraryBaseRecyclerViewAdapter<DataSource, Holder extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final int HEADER_VIEW_TYPE = -1000;
    public static final int FOOTER_VIEW_TYPE = -2000;

    private List<DataSource> dataList = new ArrayList<DataSource>();
    private final List<View> mHeaders = new ArrayList<View>();
    private final List<View> mFooters = new ArrayList<View>();

    private Context ctx;

    public LibraryBaseRecyclerViewAdapter(Context context) {
        this.ctx = context;
    }

    public void resetData(List<DataSource> tList) {
        dataList.clear();
        if (!CheckTool.isEmpty(tList)) {
            dataList.addAll(tList);
        }
        notifyDataSetChanged();
    }

    public void addData(List<DataSource> tList) {
        if (!CheckTool.isEmpty(tList)) {
            dataList.addAll(tList);
        }
        notifyDataSetChanged();
    }

    public void insertData(List<DataSource> tList) {
        if (!CheckTool.isEmpty(tList)) {
            dataList.addAll(0, tList);
        }
        notifyDataSetChanged();
    }

    public void removeData(List<DataSource> tList) {
        if (!CheckTool.isEmpty(tList)) {
            dataList.removeAll(tList);
        }
        notifyDataSetChanged();
    }

    public void removeData(DataSource t) {
        if (!CheckTool.isEmpty(t)) {
            dataList.remove(t);
        }
        notifyDataSetChanged();
    }

    protected abstract int getLayoutId();

    protected abstract Holder onCreateViewHolder(View view);

    protected abstract void onBindViewHolder(Holder viewHolder, DataSource data, int position);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (isHeader(viewType)) {
            int whichHeader = Math.abs(viewType - HEADER_VIEW_TYPE);
            View headerView = getHeader(whichHeader);
            return new RecyclerView.ViewHolder(headerView) {
            };
        } else if (isFooter(viewType)) {
            int whichFooter = Math.abs(viewType - FOOTER_VIEW_TYPE);
            View footerView = getFooter(whichFooter);
            footerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            return new RecyclerView.ViewHolder(footerView) {
            };

        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(getLayoutId(), viewGroup, false);
            return onCreateViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (position < getHeaderCount()) {
            // Headers don't need anything special

        } else if (position < getItemCount() - getFooterCount()) {
            // This is a real position, not a header or footer. Bind it.
            onBindViewHolder((Holder) viewHolder, getItem(position - getHeaderCount()), position - getHeaderCount());
        } else {
            // Footers don't need anything special
        }
    }

    @Override
    public final int getItemCount() {
        return getHeaderCount() + getDataSourceCount() + getFooterCount();
    }

    /**
     * Adds a header view.
     */
    public void addHeader(@NonNull View view) {
        if (view == null) {
            throw new IllegalArgumentException("You can't have a null header!");
        }
        mHeaders.add(view);
        notifyDataSetChanged();
    }

    public void removeHeader() {
        mHeaders.clear();
        notifyDataSetChanged();
    }

    /**
     * Adds a footer view.
     */
    public void addFooter(@NonNull View view) {
        if (view == null) {
            throw new IllegalArgumentException("You can't have a null footer!");
        }
        mFooters.add(view);
        notifyDataSetChanged();
    }

    /**
     * Toggles the visibility of the header views.
     */
    public void setHeaderVisibility(boolean shouldShow) {
        for (View header : mHeaders) {
            header.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
        }
        notifyDataSetChanged();
    }

    /**
     * Toggles the visibility of the footer views.
     */
    public void setFooterVisibility(boolean shouldShow) {
        for (View footer : mFooters) {
            footer.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
        }
        notifyDataSetChanged();
    }

    /**
     * @return the number of headers.
     */
    public final int getHeaderCount() {
        return mHeaders.size();
    }

    /**
     * @return the number of footers.
     */
    public final int getFooterCount() {
        return mFooters.size();
    }

    /**
     * Gets the indicated header, or null if it doesn't exist.
     */
    public View getHeader(int i) {
        return i < getHeaderCount() ? mHeaders.get(i) : null;
    }

    /**
     * Gets the indicated footer, or null if it doesn't exist.
     */
    public View getFooter(int i) {
        return i < getFooterCount() ? mFooters.get(i) : null;
    }

    private boolean isHeader(int viewType) {
        return viewType >= HEADER_VIEW_TYPE && viewType < (HEADER_VIEW_TYPE + getHeaderCount());
    }

    private boolean isFooter(int viewType) {
        return viewType >= FOOTER_VIEW_TYPE && viewType < (FOOTER_VIEW_TYPE + getFooterCount());
    }


    @Override
    public int getItemViewType(int position) {
        if (position < getHeaderCount()) {
            return HEADER_VIEW_TYPE + position;
        } else if (position < (getItemCount() - getFooterCount())) {
            return super.getItemViewType(position - getHeaderCount());
        } else {
            return FOOTER_VIEW_TYPE + position - (getItemCount() - getFooterCount());
        }
    }

    public DataSource getItem(int position) {
        return dataList.get(position);
    }

    public List<DataSource> getDataSource() {
        return dataList;
    }

    public int getDataSourceCount() {
        return dataList.size();
    }

    public Context getAdapterContext() {
        return ctx;
    }

    public void startActivity(Intent it) {
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getAdapterContext().startActivity(it);
    }
}
