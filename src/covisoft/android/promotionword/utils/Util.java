package covisoft.android.promotionword.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Util {
	
	public static final String 			PREFS_CITY = "cities_data";
    public static final String			PREFS_CITY_VALUE = "city_value";
    public static final String 			PREFS_CITY_ID_VALUE = "city_id_value";
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
	public static final void setAppFont(Context mContext, ViewGroup mContainer)
	{
		final Typeface mFont = Typeface.createFromAsset(mContext.getAssets(), "SFUHelveticaLight.ttf"); 
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
	            setAppFont(mContext, (ViewGroup) mChild);
	        }
	    }
	}
	
	public static void createDialogCustomView(Context mContext, View viewDialog, String title){
		
		AlertDialog.Builder builderDialog = new Builder(mContext);
		builderDialog.setTitle(title)
						.setNegativeButton("Close", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						})
						.setView(viewDialog);
		AlertDialog dialog = builderDialog.create();
		dialog.show();
	}
	
	public static void setCityPrefs (Context context, String cityValue, String cityIdValue){
		SharedPreferences cityData = context.getSharedPreferences(PREFS_CITY, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = cityData.edit();
		if(cityValue != null && cityIdValue != null){
			editor.putString(PREFS_CITY_VALUE, cityValue);
			editor.putString(PREFS_CITY_ID_VALUE, cityIdValue);
		}
		editor.commit();
	}
	
	
	
	public static String getCityValue(Context context){
		String cityValue = null;
		SharedPreferences cityData = context.getSharedPreferences(PREFS_CITY, Context.MODE_PRIVATE);
		if(cityData != null){
			cityValue = cityData.getString(PREFS_CITY_VALUE, null);
		}
		return cityValue;
	}
	public static String getCityIdValue(Context context){
		String cityIdValue = null;
		SharedPreferences cityData = context.getSharedPreferences(PREFS_CITY, Context.MODE_PRIVATE);
		if(cityData != null){
			cityIdValue = cityData.getString(PREFS_CITY_ID_VALUE, null);
		}
		return cityIdValue;
	}
	
}
