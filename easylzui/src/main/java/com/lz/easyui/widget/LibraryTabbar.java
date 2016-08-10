package com.lz.easyui.widget;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lz.easyui.R;
import com.lz.easyui.activity.LibraryBaseFragment;
import com.lz.easyui.util.CheckTool;
import com.lz.easyui.util.EasyLog;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class LibraryTabbar extends FrameLayout {

    private final static String TAG = LibraryTabbar.class.getSimpleName();


    private XCViewPager viewPager;
    private FrameLayout frameLayout;
    private LinearLayout tabWidget;

    private List<TabInfo> tabInfos = new ArrayList<TabInfo>();
    private FragmentManager fm;

    private OnTabPageChangeListener tabPageChangeListener;

    private int mCurrentIndex = -1;
    private View mCurrentView = null;

    private boolean click = false;


    public LibraryTabbar(Context context) {
        super(context);
    }

    public LibraryTabbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LibraryTabbar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public static abstract class OnTabPageChangeListener {
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public abstract boolean onPageSelected(int position, Fragment fragment);

        public void onPageScrollStateChanged(int state) {
        }
    }

    public static class TabInfo {
        public View tabWidgetView;
        public Fragment frament;

        public TabInfo(View tabWidgetView, Fragment frament) {
            this.tabWidgetView = tabWidgetView;
            this.frament = frament;
        }
    }

    public void addTabs(List<TabInfo> tabInfos, FragmentManager fm) {
        addTabs(tabInfos, fm, 0);
    }

    public void addTabs(List<TabInfo> tabInfos, FragmentManager fm, int defaultIndex) {
        if (CheckTool.isEmpty(tabInfos) || fm == null) {
            throw new RuntimeException("Mast have tabinfos and FragmentManager");
        }
        tabWidget = (LinearLayout) findViewById(R.id.tabbar_tabwidget);
        if (tabWidget == null) {
            throw new RuntimeException("Your Tabbar must have a LinearLayout whose id attribute is 'R.id.tabbar_tabwidget'");
        }
        viewPager = (XCViewPager) findViewById(R.id.tabbar_viewpager);

        frameLayout = (FrameLayout) findViewById(R.id.tabbar_frament);

        if ((viewPager != null && frameLayout != null) || (viewPager == null && frameLayout == null)) {
            throw new RuntimeException("Your Tabbar must have but only have one view by viewPager and frameLayout");
        }

        this.fm = fm;
        this.tabInfos.clear();
        this.tabInfos.addAll(tabInfos);

        setViewPager();

        setTabWidget();

        setCurrentItem(defaultIndex);
    }

    public void setCurrentItem(int index) {
        if (index < 0 || index >= tabInfos.size()) {
            return;
        }

        changeTabWidget(index);
        changeViewPager(index);
    }

    public void setScrollable(boolean scrollable) {
        if (viewPager == null) {
            return;
        }
        viewPager.setScrollable(scrollable);
    }

    public void setOnTabPageChangeListener(OnTabPageChangeListener tabPageChangeListener) {
        this.tabPageChangeListener = tabPageChangeListener;
    }

    private void setViewPager() {
        if (viewPager == null) {
            return;
        }
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(fm);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (tabPageChangeListener != null && !click) {
                    tabPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                changeTabWidget(position);
                if (!click) {
                    pageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (tabPageChangeListener != null && !click) {
                    tabPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
    }

    private void setTabWidget() {
        if (tabWidget == null) {
            EasyLog.e(TAG, "set tab widget error");
            return;
        }
        tabWidget.removeAllViews();

        for (int i = 0; i < tabInfos.size(); i++) {
            TabInfo info = tabInfos.get(i);
            View tabWidgetView = info.tabWidgetView;
            tabWidget.addView(tabWidgetView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));

            tabWidgetView.setSelected(false);

            tabWidgetView.setTag(i);
            tabWidgetView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = Integer.parseInt(v.getTag().toString());
                    click = true;

                    if (mCurrentIndex == index) {
                        return;
                    }
//                    mCurrentIndex = index;

                    if (!pageSelected(index)){
                        setCurrentItem(index);
                    }
                }
            });
        }
    }

    private boolean pageSelected(int index) {
        if (tabPageChangeListener != null) {
            Fragment fragment = tabInfos.get(index).frament;
            if (fragment != null && fragment instanceof LibraryBaseFragment) {
                ((LibraryBaseFragment) fragment).tabInitData(index, fragment);
            }

           return tabPageChangeListener.onPageSelected(index, fragment);
        }
        return true;
    }

    private synchronized void changeTabWidget(int index) {
        if (mCurrentView != null) {
            mCurrentView.setSelected(false);
        }

        mCurrentIndex = index;

        TabInfo info = tabInfos.get(index);

        mCurrentView = info.tabWidgetView;
        mCurrentView.setSelected(true);
    }

    private synchronized void changeViewPager(int index) {
        if (viewPager != null) {
            viewPager.setCurrentItem(index, false);
        } else {
            changeView(index);
        }
        click = false;
    }

    private void changeView(int page) {
        FragmentTransaction ft = fm.beginTransaction();

        hideFragments(ft);

        if (tabInfos.get(page).frament.isAdded()) {
            ft.show(tabInfos.get(page).frament);
        } else {
            ft.add(R.id.tabbar_frament, tabInfos.get(page).frament);
        }
        try {
            ft.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
            EasyLog.e(TAG, "Tabbar change fragment err", e);
        }
    }

    private void hideFragments(FragmentTransaction transaction) {
        for (TabInfo tabInfo : tabInfos) {
            Fragment fragment = tabInfo.frament;
            if (fragment.isAdded()) {
                transaction.hide(fragment);
            }
        }
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return tabInfos.get(i).frament;
        }

        @Override
        public int getCount() {
            return tabInfos.size();
        }
    }

}
