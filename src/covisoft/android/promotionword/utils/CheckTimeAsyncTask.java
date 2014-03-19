package covisoft.android.promotionword.utils;

import java.util.TimerTask;

import android.app.Activity;
import android.os.AsyncTask;

public class CheckTimeAsyncTask extends TimerTask{

	private Activity mActivity;
	@SuppressWarnings("rawtypes")
	private AsyncTask async;
	@SuppressWarnings("rawtypes")
	public CheckTimeAsyncTask(Activity activity, AsyncTask async) {
		super();
		this.mActivity = activity;
		this.async = async;
}

	@Override
	public void run() {
		mActivity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				async.cancel(true);
			}
		});
	}

	
}
