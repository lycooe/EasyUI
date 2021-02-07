package com.lewis.easyui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lewis.easyui.R;
import com.lewis.easyui.util.CheckTool;
import com.lewis.easyui.util.EasyLog;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class LibraryTabHolder extends FrameLayout {
    public LibraryTabHolder(Context context) {
        super(context);
    }

    public LibraryTabHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LibraryTabHolder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private final static String TAG = LibraryTabHolder.class.getSimpleName();


    private XCViewPager viewPager;
    private FrameLayout frameLayout;
    private TabLayout tabLayout;

    private List<TabInfo> tabInfos = new ArrayList<TabInfo>();
    private FragmentManager fm;

    private OnTabPageChangeListener tabPageChangeListener;

    private int mCurrentIndex = -1;
    private View mCurrentView = null;

    private boolean click = false;

    private static boolean tabInfoShowView;

    public static abstract class OnTabPageChangeListener {
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public abstract boolean onPageSelected(int position, Fragment fragment);

        public void onPageScrollStateChanged(int state) {
        }
    }

    public static class TabInfo {
        public View tabWidgetView;
        public String tabName;
        public Fragment frament;

        public TabInfo(View tabWidgetView, Fragment frament) {
            this.tabWidgetView = tabWidgetView;
            this.frament = frament;
            tabInfoShowView = true;
        }

        public TabInfo(String tabName, Fragment frament) {
            this.tabName = tabName;
            this.frament = frament;
            tabInfoShowView = false;
        }
    }

    public void addTabs(List<TabInfo> tabInfos, FragmentManager fm) {
        addTabs(tabInfos, fm, 0);
    }

    public void addTabs(List<TabInfo> tabInfos, FragmentManager fm, int defaultIndex) {
        if (CheckTool.isEmpty(tabInfos) || fm == null) {
            throw new RuntimeException("Mast have tabinfos and FragmentManager");
        }
        tabLayout = findViewById(R.id.tabbar_tablayout);
        if (tabLayout == null) {
            throw new RuntimeException("Your Tabbar must have a TabLayout whose id attribute is 'R.id.tabbar_tablayout'");
        }
        View tabbarView = findViewById(R.id.tabbar_view);
        if (tabbarView == null) {
            throw new RuntimeException("Your Tabbar must have but only have one view by viewPager and frameLayout");
        }
        if (tabbarView instanceof XCViewPager) {
            viewPager = (XCViewPager) tabbarView;
            viewPager.removeAllViews();
        } else if (tabbarView instanceof FrameLayout) {
            frameLayout = (FrameLayout) tabbarView;
            frameLayout.removeAllViews();
        } else {
            throw new RuntimeException("Your Tabbar must have but only have one view by viewPager and frameLayout");
        }

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

    public int getCurrentIndex() {
        return mCurrentIndex;
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
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int mScrollState;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (tabPageChangeListener != null && !click) {
                    tabPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
                if (tabLayout != null && tabInfoShowView) {
                    tabLayout.setScrollPosition(position, positionOffset, true);
                }
            }

            @Override
            public void onPageSelected(int position) {
                changeTabWidget(position);
                if (!click) {
                    pageSelected(position);
                }
                if (tabLayout != null && tabInfoShowView) {
                    tabLayout.getTabAt(position).select();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                this.mScrollState = state;
                if (tabPageChangeListener != null && !click) {
                    tabPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
    }

    private void setTabWidget() {
        if (!tabInfoShowView) {
            return;
        }
        if (tabLayout == null) {
            EasyLog.e(TAG, "set tab widget error");
            return;
        }
        tabLayout.removeAllTabs();
        for (int i = 0; i < tabInfos.size(); i++) {
            TabInfo info = tabInfos.get(i);
            View tabWidgetView = info.tabWidgetView;
            tabWidgetView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            tabLayout.addTab(tabLayout.newTab().setCustomView(tabWidgetView), i, i == 0);
        }

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            MarginLayoutParams p = (MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 0, 0);
            tab.requestLayout();
        }
    }

    private boolean pageSelected(int index) {
        if (tabPageChangeListener != null) {
            return tabPageChangeListener.onPageSelected(index, tabInfos.get(index).frament);
        }
        return false;
    }

    private synchronized void changeTabWidget(int index) {
        if (!tabInfoShowView) {
            return;
        }
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
            ft.add(R.id.tabbar_view, tabInfos.get(page).frament);
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

        @Override
        public CharSequence getPageTitle(int position) {
            TabInfo tabInfo = tabInfos.get(position);
            return tabInfo.tabName;
        }
    }
}
