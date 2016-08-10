package com.lz.easyui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.lz.easyui.adapter.LibraryBaseListViewAdapter;
import com.lz.easyui.util.CheckTool;
import com.lz.easyui.widget.swiperefresh.XListView;

import java.util.ArrayList;
import java.util.List;

import com.lz.easyui.R;

/**
 *
 */
public abstract class LibraryBaseListViewFragment<DataSource> extends LibraryBaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    XListView fgm_listview;

    SwipeRefreshLayout refreshWidget;

    private LibraryBaseListViewAdapter<DataSource, Object> adapter;

    protected int getContentViewId() {
        return R.layout.library_base_fgm_listview;
    }

    @Override
    public final void initWidget(View view) {
        fgm_listview = (XListView) view.findViewById(R.id.base_listview);
        refreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.base_swipe_refresh_widget);

        if (fgm_listview == null || refreshWidget == null) {
            throw new RuntimeException("[Library] xml must have fgm_listview and refreshWidget");
        }

        if (!showDivider()) {
            fgm_listview.setDividerHeight(0);
        }
        adapter = getAdapter();
        fgm_listview.setAdapter(adapter);

        if (isHaveLastPage()) {
            refreshWidget.setEnabled(true);
            refreshWidget.setColorSchemeResources(R.color.refresh_color1, R.color.refresh_color2, R.color.refresh_color3, R.color.refresh_color4);
            refreshWidget.setOnRefreshListener(this);
        } else {
            refreshWidget.setEnabled(false);
        }


        if (isHaveNextPage()) {
            fgm_listview.setOnLoadMoreListener(true, new XListView.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    getData(nowPage + 1, GETDATA_STATE_MORE);
                }
            });
        }
        fgm_listview.showOrHideFooter(false);
    }

    @Override
    public final void initData() {
        initData(GETDATA_STATE_INIT);
    }

    @Override
    public void onRefresh() {
        getData(1, GETDATA_STATE_PULL);
    }

    public final void initData(int state) {
        getData(1, state);
    }


    private void getData(int page, int state) {
        getData(page, state, callBack);
    }

    protected abstract void getData(int page, int state, LibraryBaseListRequest<DataSource> callBack);

    LibraryBaseListRequest<DataSource> callBack = new LibraryBaseListRequest<DataSource>() {
        @Override
        public void requestCallBack(List<DataSource> list, int page) {
            setData(page, list);
        }
    };

    private void setData(int page, List<DataSource> list) {
        nowPage = page;
        refreshWidget.setRefreshing(false);
        if (page == 1) {
            if (CheckTool.isEmpty(list)) {
                emptyData(true);
            }
            adapter.resetData(list);
            getListView().setSelection(0);
        } else {
            adapter.addData(list);
        }
    }

    public final XListView getListView() {
        return fgm_listview;
    }

    public final void autoRefresh() {
        if (fgm_listview != null) {
            fgm_listview.autoRefresh();
        }
    }

    public final LibraryBaseListViewAdapter getParentAdapter() {
        return adapter;
    }

    public final List<DataSource> getDataSource() {
        if (adapter == null) {
            return new ArrayList<>();
        }
        return adapter.getDataSource();
    }

    public final int getCount() {
        if (adapter == null) {
            return 0;
        }
        return adapter.getCount();
    }

    public final DataSource getItem(int position) {
        if (adapter == null) {
            return null;
        }
        return adapter.getItem(position);
    }

    public void resetData(List<DataSource> tList) {
        if (adapter == null) {
            return;
        }
        adapter.resetData(tList);
    }

    public void addData(List<DataSource> tList) {
        if (adapter == null) {
            return;
        }
        adapter.addData(tList);
    }

    public void insertData(List<DataSource> tList) {
        if (adapter == null) {
            return;
        }
        adapter.insertData(tList);
    }

    public void removeData(List<DataSource> tList) {
        if (adapter == null) {
            return;
        }
        adapter.removeData(tList);
    }

    public void removeData(DataSource t) {
        if (adapter == null) {
            return;
        }
        adapter.removeData(t);
    }


    protected abstract LibraryBaseListViewAdapter getAdapter();

    public boolean isHaveLastPage() {
        return true;
    }

    public boolean isHaveNextPage() {
        return true;
    }

    public boolean showDivider() {
        return true;
    }

    public void emptyData(boolean empty) {

    }
}
