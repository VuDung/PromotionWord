package covisoft.android.promotionword.tabhost;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;

public class TabAdapter  extends FragmentPagerAdapter
						implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener{

	private final Context mContext;
	private final TabHost mTabHost;
	private final ViewPager mViewPager;
	private final ArrayList<TabInfo> mTabs = new ArrayList<TabAdapter.TabInfo>();
	
	static final class TabInfo{
		private final String _TAG;
		private final Class<?> _CLASS;
		private final Bundle _ARGS;
		public TabInfo(String _TAG, Class<?> _CLASS, Bundle _ARGS) {
			super();
			this._TAG = _TAG;
			this._CLASS = _CLASS;
			this._ARGS = _ARGS;
		}
		
	}
	
	static class DummyTabFactory implements TabHost.TabContentFactory {
		private final Context mContext;

		
		public DummyTabFactory(Context mContext) {
			super();
			this.mContext = mContext;
		}


		@Override
		public View createTabContent(String tag) {
			// TODO Auto-generated method stub
			View v = new View(mContext);
			v.setMinimumHeight(0);
			v.setMinimumWidth(0);
			return v;
		}
		
		
	}
	public TabAdapter(FragmentActivity activity, TabHost tabHost, ViewPager viewPager) {
		// TODO Auto-generated constructor stub
		super(activity.getSupportFragmentManager());
		this.mContext = activity;
		this.mTabHost = tabHost;
		this.mViewPager = viewPager;
		mTabHost.setOnTabChangedListener(this);
		mViewPager.setAdapter(this);
		mViewPager.setOnPageChangeListener(this);
	}
	
	public void addTab(TabHost.TabSpec spec, Class<?> _class, Bundle arg){
		spec.setContent(new DummyTabFactory(mContext));
		String tag = spec.getTag();
		
		TabInfo info = new TabInfo(tag, _class, arg);
		mTabs.add(info);
		mTabHost.addTab(spec);
		notifyDataSetChanged();
		
	}
	
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		TabInfo info = mTabs.get(arg0);
		return Fragment.instantiate(mContext, info._CLASS.getName(), info._ARGS);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTabs.size();
	}

	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		int position = mTabHost.getCurrentTab();
		mViewPager.setCurrentItem(position);
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		TabWidget widget = mTabHost.getTabWidget();
        int oldFocusability = widget.getDescendantFocusability();
        widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        mTabHost.setCurrentTab(arg0);
        widget.setDescendantFocusability(oldFocusability);
	}

}
