package covisoft.android.promotionword.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import covisoft.android.promotionword.R;

public class ActivitySendOder extends SherlockFragmentActivity {

	FragmentManager fm = getSupportFragmentManager();
	public ActivitySendOder() {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		if(fm.findFragmentById(android.R.id.content) == null){
			TakeOrderFragment orderFragment = new TakeOrderFragment();
//			fm.beginTransaction().add(android.R.id.content, orderFragment).commit();
			fm.beginTransaction().replace(R.id.realtabcontent, orderFragment).commit();
		}
	}


	public static class TakeOrderFragment extends SherlockFragment{
		
	}
}
