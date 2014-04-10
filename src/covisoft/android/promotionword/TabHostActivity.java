package covisoft.android.promotionword;

import android.content.Context;
import android.os.Bundle;
import android.widget.TabHost;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import covisoft.android.promotionword.activity.ActivityBonusPoint;
import covisoft.android.promotionword.activity.ActivityPromotionPlace;
import covisoft.android.promotionword.activity.ActivitySendOder;
import covisoft.android.promotionword.tabhost.TabManager;

public class TabHostActivity extends SherlockFragmentActivity{

	private TabHost mTabHost;
	private TabManager mTabManager;
	private Context mContext;
	public TabHostActivity() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_tabhost);
		
		setUpActionBar();
		
		mContext = this;
		
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup();
		
		mTabManager = new TabManager(this, mTabHost, R.id.realtabcontent);
		
//		View tabPlace = LayoutInflater.from(mContext).inflate(R.layout.tab_indicator,mTabHost, false);
//		((TextView)tabPlace.findViewById(R.id.tvTitleTab)).setText(mContext.getResources().getString(R.string.promotion_place));
//		((ImageView)tabPlace.findViewById(R.id.imgTab)).setImageResource(R.drawable.like48);
		mTabManager.addTab(
				mTabHost.newTabSpec("place").setIndicator(mContext.getResources().getString(R.string.promotion_place),mContext.getResources().getDrawable(R.drawable.like48)), 
				ActivityPromotionPlace.MainFragment.class, 
				null);
		mTabManager.addTab(mTabHost.newTabSpec("order").setIndicator(mContext.getResources().getString(R.string.send_order),mContext.getResources().getDrawable(R.drawable.camera48)), 
					ActivitySendOder.TakeOrderFragment.class, 
					null);
		mTabManager.addTab(mTabHost.newTabSpec("point").setIndicator(mContext.getResources().getString(R.string.mark_charge),mContext.getResources().getDrawable(R.drawable.star48)), 
					ActivityBonusPoint.BonusPointFragment.class, 
					null);
		
		if(arg0 != null){
			mTabHost.setCurrentTabByTag(arg0.getString("tab"));
		}
	}
	
	protected void setUpActionBar(){
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
			
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
		
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("tab", mTabHost.getCurrentTabTag());
		
	}
	

	
}
