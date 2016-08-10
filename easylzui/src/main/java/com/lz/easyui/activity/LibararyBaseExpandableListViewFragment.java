package com.lz.easyui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.lz.easyui.R;
import com.lz.easyui.adapter.LibraryBaseExpandableListViewAdapter;
import com.lz.easyui.util.CheckTool;
import com.lz.easyui.widget.swiperefresh.XFooterView;

import java.util.List;

/**
 *
 */
public abstract class LibararyBaseExpandableListViewFragment extends LibraryBaseFragment implements AbsListView.OnScrollListener, SwipeRefreshLayout.OnRefreshListener {

    private ExpandableListView base_fgm_expandable;
    private SwipeRefreshLayout refreshWidget;

    private LinearLayout mFooterLayout;
    private XFooterView mFooterView;

    private LibraryBaseExpandableListViewAdapter mAdapter;

    private boolean isLoading;
    private boolean isLast;

    @Override
    protected int getContentViewId() {
        return R.layout.library_base_fgm_expandable;
    }

    @Override
    public final void initWidget(View view) {
        base_fgm_expandable = (ExpandableListView) view.findViewById(R.id.base_fgm_expandable);
        refreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.base_swipe_refresh_widget);

        initHeader();
        base_fgm_expandable.setGroupIndicator(null);
        mAdapter = getAdapter();
        base_fgm_expandable.setAdapter(mAdapter);

        base_fgm_expandable.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        if (isHaveLastPage()) {
            refreshWidget.setEnabled(true);
            refreshWidget.setColorSchemeResources(R.color.refresh_color1, R.color.refresh_color2, R.color.refresh_color3, R.color.refresh_color4);
            refreshWidget.setOnRefreshListener(this);
        } else {
            refreshWidget.setEnabled(false);
        }


        if (isHaveNextPage()) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER;
            mFooterView = new XFooterView(getTopActivity().getBaseContext());
            mFooterLayout = new LinearLayout(getTopActivity().getApplicationContext());
            mFooterLayout.addView(mFooterView, params);
            base_fgm_expandable.addFooterView(mFooterLayout, null, false);
            showOrHideFooter(false);
            base_fgm_expandable.setOnScrollListener(this);
        }
    }

    protected abstract void initHeader();

    protected abstract LibraryBaseExpandableListViewAdapter getAdapter();

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && isHaveNextPage()) {
            if (view.getLastVisiblePosition() == view.getCount() - 1) {
                //加载更多功能的代码
                if (!isLoading && !isLast) {
                    showOrHideFooter(true);
                    getData(nowPage + 1, GETDATA_STATE_MORE);
                }
            }
        }
    }

    @Override
    public void onRefresh() {
        getData(1, GETDATA_STATE_PULL);
    }

    @Override
    public final void initData() {
        getData(nowPage, GETDATA_STATE_INIT);
    }

    private void getData(int page, int state) {
        isLoading = true;
        getData(page, state, callBack);
    }

    protected abstract void getData(int page, int state, LibraryBaseListRequest callBack);

    public LibraryBaseListRequest callBack = new LibraryBaseListRequest() {
        @Override
        public void requestCallBack(List list, int page) {
            setData(page, list);
        }
    };

    private void setData(int page, List list) {
        isLoading = false;
        refreshWidget.setRefreshing(false);
        this.nowPage = page;
        if (CheckTool.isEmpty(list) || list.size() < getPageCount()) {
            isLast = true;
        } else {
            isLast = false;
        }
        if (page == 1) {
            mAdapter.resetData(list);
        } else {
            mAdapter.addData(list);
        }

        if (showAllChild()) {
            for (int i = 0; i < list.size(); i++) {
                base_fgm_expandable.expandGroup(i);
            }
        }
    }

    public final void showOrHideFooter(boolean isShow) {
        try {
            RelativeLayout pull_footer = (RelativeLayout) mFooterView.findViewById(R.id.library_pull_footer);
            pull_footer.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected ExpandableListView getExpandableListView(){
        return base_fgm_expandable;
    }

    protected LibraryBaseExpandableListViewAdapter getParentAdapter(){
        return mAdapter;
    }

    public boolean isHaveLastPage() {
        return true;
    }

    public boolean isHaveNextPage() {
        return true;
    }

    protected int getPageCount() {
        return 20;
    }

    protected boolean showAllChild(){
        return true;
    }

    @Override
    public final void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

}
