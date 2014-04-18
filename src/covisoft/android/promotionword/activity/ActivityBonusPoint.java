package covisoft.android.promotionword.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import covisoft.android.promotionword.R;

public class ActivityBonusPoint extends SherlockFragmentActivity{

	private FragmentManager fm = getSupportFragmentManager();
	public ActivityBonusPoint() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		if(fm.findFragmentById(android.R.id.content) == null){
			BonusPointFragment point = new BonusPointFragment();
//			fm.beginTransaction().add(android.R.id.content, point).commit();
			fm.beginTransaction().replace(R.id.realtabcontent, point).commit();
		}
	}

	public static class BonusPointFragment extends SherlockFragment{
		
	}
}
