package covisoft.android.promotionword;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import covisoft.android.promotionword.activity.ActivityBonusPoint;
import covisoft.android.promotionword.activity.ActivityPromotionPlace;
import covisoft.android.promotionword.activity.ActivitySendOder;
import covisoft.android.promotionword.tabhost.TabAdapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TabHost;

public class MainActivity extends SherlockFragmentActivity {

	private TabHost mTabHost;
	private ViewPager mPager;
	private TabAdapter mTabAdapter;
	private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
        
        mPager = (ViewPager)findViewById(R.id.mPager);
        
        mTabAdapter = new TabAdapter(this, mTabHost, mPager);
        
        mTabAdapter.addTab(mTabHost.newTabSpec("place").setIndicator(mContext.getResources().getString(R.string.promotion_place)), 
        					ActivityPromotionPlace.MainFragment.class, 
        					null);
        mTabAdapter.addTab(mTabHost.newTabSpec("order").setIndicator(mContext.getResources().getString(R.string.send_order)), 
        					ActivitySendOder.TakeOrderFragment.class, 
        					null);
        mTabAdapter.addTab(mTabHost.newTabSpec("point").setIndicator(mContext.getResources().getString(R.string.mark_charge)), 
        					ActivityBonusPoint.BonusPointFragment.class, 
        					null);
        
        if(savedInstanceState != null){
        	mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
    }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("tab", mTabHost.getCurrentTabTag());
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}


}
