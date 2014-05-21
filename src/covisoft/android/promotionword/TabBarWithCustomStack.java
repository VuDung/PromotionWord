package covisoft.android.promotionword;

import java.util.HashMap;
import java.util.Stack;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import covisoft.android.promotionword.activity.ActivityNearby;
import covisoft.android.promotionword.activity.ActivitySearch;
import covisoft.android.promotionword.fragment.FragmentBonusPoint;
import covisoft.android.promotionword.fragment.FragmentMain;
import covisoft.android.promotionword.fragment.FragmentSendOrder;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.Toast;

public class TabBarWithCustomStack extends SherlockFragmentActivity {

	public static HashMap<String, Stack<Fragment>> customBackStack;
	
	private static Stack<Fragment> promotionPlaceStack;
	private static Stack<Fragment> sendOrderStack;
	private static Stack<Fragment> bonusPointStack;
	
	
	//Stack final names
	private static final String STACK_PLACE = "place";
	private static final String STACK_ORDER = "order";
	private static final String STACK_BONUS = "bonus";
	
	
	//Tab string for onSaveInstanceState method
	private static final String INSTANCE_STATE_TAB = "tab";
	
	public static TabHost mTabHost;
	public static TabManagerWithCustomStack mTabManager;
	public static ViewPager mViewPager;
	
	public static final int HOME_TAB_INDEX = 5;
	
	private FragmentTransaction transaction;
	
	private Context mContext;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_tabhost);
		
		mContext = this;
		
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup();
		
		customBackStack = new HashMap<String, Stack<Fragment>>();
		
		promotionPlaceStack = new Stack<Fragment>();
		sendOrderStack = new Stack<Fragment>();
		bonusPointStack = new Stack<Fragment>();
		
		
		
		// put stack to custom back stack
		customBackStack.put(STACK_PLACE, promotionPlaceStack);
		customBackStack.put(STACK_ORDER, sendOrderStack);
		customBackStack.put(STACK_BONUS, bonusPointStack);
		
		
		
		customBackStack.get(STACK_PLACE).push(new FragmentMain());
		customBackStack.get(STACK_ORDER).push(new FragmentSendOrder());
		customBackStack.get(STACK_BONUS).push(new FragmentBonusPoint());
		
		mTabManager = new TabManagerWithCustomStack(this, mTabHost, R.id.realtabcontent);
		
		mTabManager.addTab(mTabHost.newTabSpec(STACK_PLACE)
										.setIndicator(
												mContext.getResources().getString(R.string.promotion_place), 
												mContext.getResources().getDrawable(R.drawable.like48)), 
							FragmentMain.class, 
							null);
		mTabManager.addTab(mTabHost.newTabSpec(STACK_ORDER).setIndicator(
						mContext.getResources().getString(R.string.send_order), 
						mContext.getResources().getDrawable(R.drawable.camera48)), 
							FragmentSendOrder.class, 
							null);
		mTabManager.addTab(mTabHost.newTabSpec(STACK_BONUS).setIndicator(
						mContext.getResources().getString(R.string.mark_charge), 
						mContext.getResources().getDrawable(R.drawable.star48)), 
							FragmentBonusPoint.class, 
							null);
		
		// check previously saved instance state
		if(arg0 != null){
			mTabHost.setCurrentTabByTag(arg0.getString(INSTANCE_STATE_TAB));
		}else{
			mTabHost.setCurrentTabByTag(STACK_PLACE);
		}
	}
	
	

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString(INSTANCE_STATE_TAB, mTabHost.getCurrentTabTag());
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		case R.id.action_search:
			Intent iSearch = new Intent(this, ActivitySearch.class);
			startActivity(iSearch);
			break;
		case R.id.action_nearby:
			Intent iNearby = new Intent(this, ActivityNearby.class);
			startActivity(iNearby);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}



	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Stack<Fragment> stack = customBackStack.get(mTabHost.getCurrentTabTag());
		if(stack.isEmpty()){
			super.onBackPressed();
		}else{
			Fragment fragment = stack.pop();
			if(fragment.isVisible()){
				if(stack.isEmpty()){
					super.onBackPressed();
				}else{
					Fragment frag = stack.pop();
					customBackStack.get(mTabHost.getCurrentTabTag()).push(frag);
					
					transaction = getSupportFragmentManager().beginTransaction();
					transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, 
							R.anim.slide_in_right, R.anim.slide_out_left);
					Toast.makeText(this, "Backstack pull the fragment back", Toast.LENGTH_SHORT).show();
					
					transaction.replace(R.id.realtabcontent, frag).commit();
				}
			}else{
				getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, fragment).commit();
			}
		}
		
	}



	/*
	 * TabManager
	 */
	public static class TabManagerWithCustomStack implements OnTabChangeListener {

		private final FragmentActivity mActivity;
		private final TabHost mTabHost;
		private final int mContainerId;
		
		private final HashMap<String, TabInfo> mTabs = new HashMap<String, TabManagerWithCustomStack.TabInfo>();
		
		private TabInfo mLastTab;
		
		static final class TabInfo{
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;
			private Fragment fragment;
			public TabInfo(String tag, Class<?> clss, Bundle args) {
				super();
				this.tag = tag;
				this.clss = clss;
				this.args = args;
			}
		}
		
		static class DummyTabFactory implements TabContentFactory{
			
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

		public TabManagerWithCustomStack(FragmentActivity mActivity,
				TabHost mTabHost, int mContainerId) {
			this.mActivity = mActivity;
			this.mTabHost = mTabHost;
			this.mContainerId = mContainerId;
			mTabHost.setOnTabChangedListener(this);
		}
		
		public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args){
			tabSpec.setContent(new DummyTabFactory(mActivity));
			String tag = tabSpec.getTag();
			
			TabInfo info = new TabInfo(tag, clss, args);
			
			info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
			if(info.fragment != null && !info.fragment.isDetached()){
				FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
				ft.detach(info.fragment);
				ft.commit();
			}
			
			mTabs.put(tag, info);
			mTabHost.addTab(tabSpec);
		}
		
		public void addInvisibleTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args, int childId){
			tabSpec.setContent(new DummyTabFactory(mActivity));
			String tag = tabSpec.getTag();
			TabWidget tabWidget = mTabHost.getTabWidget();
			TabInfo info = new TabInfo(tag, clss, args);
			
			info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
			if(info.fragment != null && !info.fragment.isDetached()){
				FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
				ft.detach(info.fragment);
				ft.commit();
			}
			
			mTabs.put(tag, info);
			mTabHost.addTab(tabSpec);
			
			//Makes tab invisible
			tabWidget.getChildAt(childId).setVisibility(View.GONE);
		}


		@Override
		public void onTabChanged(String tabId) {
			// TODO Auto-generated method stub
			TabInfo newTab = mTabs.get(tabId);
			if(mLastTab != newTab){
				FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
				if(mLastTab != null){
					if(mLastTab.fragment != null){
						ft.detach(mLastTab.fragment);
					}
				}
				if(newTab != null){
					if(newTab.fragment == null){
						if(!customBackStack.get(tabId).isEmpty()){
							Fragment fragment = customBackStack.get(tabId).pop();
							customBackStack.get(tabId).push(fragment);
							ft.replace(mContainerId, fragment);
						}else{
							if(!customBackStack.get(tabId).isEmpty()){
								Fragment fragment = customBackStack.get(tabId).pop();
								customBackStack.get(tabId).push(fragment);
								ft.replace(mContainerId, fragment);
							}
						}
					}
				}
				
				mLastTab = newTab;
				ft.commit();
				mActivity.getSupportFragmentManager().executePendingTransactions();
			}
		}

	}

}
