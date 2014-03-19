package covisoft.android.promotionword.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Util {

	public static final int DELAYTIME = 15000;
	public static final int LISTVIEW_NUMBER_INIT = 40;
	public static final int LISTVIEW_NUMBER_MORE = 10;
	public static boolean isLoading;
	public static ProgressDialog mProgress;
	public static void initProgressDialog(Context context){
		if(!isLoading){
			isLoading = true;
			mProgress = ProgressDialog.show(context, "", "Loading ...", true, true);
			mProgress.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss(DialogInterface dialog) {
					if(isLoading){
						mProgress.show();
					}
				}
			});
		}
	}
	public static void cancelProgressDialog(){
		isLoading = false;
		mProgress.dismiss();
	}
	public static boolean isNetworkConnected(Activity activity) {
		ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null && ni.isConnected()) {
			return true;
		} else {
			return false;
		}
	}
	public static final void setAppFont(ViewGroup mContainer, Typeface mFont)
	{
	    if (mContainer == null || mFont == null) return;

	    final int mCount = mContainer.getChildCount();

	    // Loop through all of the children.
	    for (int i = 0; i < mCount; ++i)
	    {
	        final View mChild = mContainer.getChildAt(i);
	        if (mChild instanceof TextView)
	        {
	            // Set the font if it is a TextView.
	            ((TextView) mChild).setTypeface(mFont);
	        }
	        else if (mChild instanceof ViewGroup)
	        {
	            // Recursively attempt another ViewGroup.
	            setAppFont((ViewGroup) mChild, mFont);
	        }
	    }
	}
}
