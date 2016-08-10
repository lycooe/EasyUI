package com.lz.easyui.activity;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lz.easyui.R;
import com.lz.easyui.adapter.LibraryBaseRecyclerViewAdapter;
import com.lz.easyui.util.CheckTool;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public abstract class LibraryBaseRecyclerViewFragment<DataSource> extends LibraryBaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView baseRecyclerView;

    SwipeRefreshLayout refreshWidget;

    private RecyclerView.LayoutManager layoutManager;

    private LibraryBaseRecyclerViewAdapter mAdapter;

    private boolean isLoading;
    private boolean isLast;

    protected int getContentViewId() {
        return R.layout.library_base_fgm_recyclerview;
    }

    @Override
    public final void initWidget(View view) {
        baseRecyclerView = (RecyclerView) view.findViewById(R.id.base_fgm_recyclerview);
        refreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.base_swipe_refresh_widget);

        if (baseRecyclerView == null || refreshWidget == null) {
            throw new RuntimeException("[Library] xml must have library_base_fgm_recyclerview and base_swipe_refresh_widget");
        }


        // 设置布局管理器
        if (layoutManager == null) {
            layoutManager = getLayoutManager();
        }

        baseRecyclerView.setLayoutManager(layoutManager);
        baseRecyclerView.setHasFixedSize(true);

        mAdapter = getAdapter();

        if (isHaveLastPage()) {
            refreshWidget.setEnabled(true);
            refreshWidget.setColorSchemeResources(R.color.refresh_color1, R.color.refresh_color2, R.color.refresh_color3, R.color.refresh_color4);
            refreshWidget.setOnRefreshListener(this);
        } else {
            refreshWidget.setEnabled(false);
        }

        if (isHaveNextPage()) {
            mAdapter.setFooterVisibility(false);
            mAdapter.addFooter(View.inflate(getTopActivity().getApplicationContext(), R.layout.library_pull_vw_footer, null));

            baseRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (layoutManager instanceof LinearLayoutManager) {
                        LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                        if (!isLoading && !isLast && mAdapter.getItemCount() <= (manager.findLastVisibleItemPosition() + 4) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                            mAdapter.setFooterVisibility(true);
                            getData(nowPage + 1, GETDATA_STATE_MORE);
                        }
                    } else if (layoutManager instanceof GridLayoutManager) {
                        GridLayoutManager manager = (GridLayoutManager) layoutManager;
                        if (!isLoading && !isLast && mAdapter.getItemCount() <= (manager.findLastVisibleItemPosition() + 4) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                            mAdapter.setFooterVisibility(true);
                            getData(nowPage + 1, GETDATA_STATE_MORE);
                        }
                    } else {
                        if (!isLoading && !isLast && newState == RecyclerView.SCROLL_STATE_IDLE) {
                            mAdapter.setFooterVisibility(true);
                            getData(nowPage + 1, GETDATA_STATE_MORE);
                        }
                    }
                }
            });
        }
        baseRecyclerView.setAdapter(mAdapter);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        // 创建一个线性布局管理器
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(getOrientation());
        return layoutManager;
    }

    @Override
    public void onRefresh() {
        getData(1, GETDATA_STATE_PULL);
    }

    @Override
    public final void initData() {
        getData(nowPage, GETDATA_STATE_INIT);
    }

    public LibraryBaseListRequest callBack = new LibraryBaseListRequest<DataSource>() {
        @Override
        public void requestCallBack(List<DataSource> list, int page) {
            setData(page, list);
        }
    };

    private void getData(int page, int state) {
        isLoading = true;
        getData(page, state, callBack);
    }

    private void setData(int page, List list) {
        mAdapter.setFooterVisibility(false);
        isLoading = false;
        refreshWidget.setRefreshing(false);
        this.nowPage = page;
        if (page == 1) {
            isLast = false;
            if (CheckTool.isEmpty(list) || list.size() < getPageCount()) {
                isLast = true;
            }
            mAdapter.resetData(list);
        } else {
            if (CheckTool.isEmpty(list) || list.size() < getPageCount()) {
                isLast = true;
            }
            mAdapter.addData(list);
        }
    }

    protected abstract LibraryBaseRecyclerViewAdapter getAdapter();

    protected abstract void getData(int page, int state, LibraryBaseListRequest<DataSource> callBack);

    public RecyclerView getBaseRecyclerView() {
        return baseRecyclerView;
    }

    protected final void setRefreshColorSchemeResources(int... colors) {
        refreshWidget.setColorSchemeResources(colors);
    }

    public String showLoginErrorText() {
        return null;
    }

    public boolean isHaveLastPage() {
        return true;
    }

    public boolean isHaveNextPage() {
        return true;
    }

    public boolean showDivider() {
        return true;
    }

    protected int getPageCount() {
        return 10;
    }

    protected int getOrientation() {
        return LinearLayoutManager.VERTICAL;
    }

    public final LibraryBaseRecyclerViewAdapter getParentAdapter() {
        return mAdapter;
    }

    public final void autoRefresh() {
        if (refreshWidget != null) {
            refreshWidget.setRefreshing(true);
        }
    }

    public final List<DataSource> getDataSource() {
        if (mAdapter == null) {
            return new ArrayList<>();
        }
        return mAdapter.getDataSource();
    }

    public final int getCount() {
        if (mAdapter == null) {
            return 0;
        }
        return mAdapter.getItemCount();
    }

    public final DataSource getItem(int position) {
        if (mAdapter == null) {
            return null;
        }
        return (DataSource) mAdapter.getItem(position);
    }

    public void resetData(List<DataSource> tList) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.resetData(tList);
    }

    public void addData(List<DataSource> tList) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.addData(tList);
    }

    public void insertData(List<DataSource> tList) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.insertData(tList);
    }

    public void removeData(List<DataSource> tList) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.removeData(tList);
    }

    public void removeData(DataSource t) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.removeData(t);
    }

    public void addHeader(@NonNull View view) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.addHeader(view);
    }

    public void addFooter(@NonNull View view) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.addFooter(view);
    }

    public void setHeaderVisibility(boolean shouldShow) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.setHeaderVisibility(shouldShow);
    }

    public void setFooterVisibility(boolean shouldShow) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.setFooterVisibility(shouldShow);
    }

    public int getHeaderCount() {
        if (mAdapter == null) {
            return 0;
        }
        return mAdapter.getHeaderCount();
    }

    public int getFooterCount() {
        if (mAdapter == null) {
            return 0;
        }
        return mAdapter.getFooterCount();
    }

    public View getHeader(int i) {
        if (mAdapter == null) {
            return null;
        }
        return mAdapter.getHeader(i);
    }

    public View getFooter(int i) {
        if (mAdapter == null) {
            return null;
        }
        return mAdapter.getFooter(i);
    }

}
