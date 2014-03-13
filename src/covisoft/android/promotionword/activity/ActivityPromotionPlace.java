package covisoft.android.promotionword.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class ActivityPromotionPlace extends SherlockFragmentActivity {

	private FragmentManager fm = getSupportFragmentManager();
	public ActivityPromotionPlace() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		if(fm.findFragmentById(android.R.id.content) == null){
			MainFragment main = new MainFragment();
			fm.beginTransaction().add(android.R.id.content, main).commit();
		}
	}

	public static class MainFragment extends SherlockFragment{
		
	}

}
