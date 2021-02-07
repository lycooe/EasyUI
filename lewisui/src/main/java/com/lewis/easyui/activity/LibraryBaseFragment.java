package com.lewis.easyui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.lewis.easyui.event.BaseEvent;
import com.lewis.easyui.util.RelayoutViewTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 *
 */
public abstract class LibraryBaseFragment extends Fragment {

    public final static int GETDATA_STATE_PULL = 1;
    public final static int GETDATA_STATE_MORE = 2;
    public final static int GETDATA_STATE_INIT = 3;
    protected int nowPage = 1;

    private View baseFgmView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseEvent event) {
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        baseFgmView = inflater.inflate(getContentViewId(), container, false);
//        ButterKnife.bind(this, baseFgmView);
        if (isRelayout()) {
            RelayoutViewTool.relayoutViewWithScale(baseFgmView, getTopActivity().getApplicationContext().getResources().getDisplayMetrics().widthPixels);
        }
        initWidget(baseFgmView);
        setWidgetState();
        if (callInit()) {
            initData();
        }
        return baseFgmView;
    }

    @Deprecated
    public View onLibraryCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    protected View getBaseFgmView() {
        return baseFgmView;
    }

    protected void setBackgroundColor(int color) {
        baseFgmView.setBackgroundColor(color);
    }

    protected void setBackgroundResource(int resid) {
        baseFgmView.setBackgroundResource(resid);
    }

    protected boolean isRelayout() {
        return false;
    }

    protected abstract int getContentViewId();

    public abstract void initWidget(View view);

    public abstract void setWidgetState();

    public abstract void initData();

    public boolean callInit() {
        return true;
    }

    public void tabInitData(int index, Fragment fragment) {
    }

    protected FragmentActivity getTopActivity() {
        return getActivity();
    }

    public void startActivity(Intent it) {
        super.startActivity(it);
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }


}
