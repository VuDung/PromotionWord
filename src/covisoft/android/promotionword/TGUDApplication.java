package covisoft.android.promotionword;

import android.app.Application;

public class TGUDApplication extends Application {
    private static final String TAG = "ObjectPreference";
    private ComplexPreferences complexPrefenreces = null;
    private static TGUDApplication instance;

    public static TGUDApplication getInstance(){
    	return instance;
    }
	@Override
	public void onCreate() {
	    super.onCreate();
	    instance = this;
	    complexPrefenreces = ComplexPreferences.getComplexPreferences(getBaseContext(), "TGUDPreferenceKey", MODE_PRIVATE);
	    android.util.Log.i(TAG, "Preference Created.");
	}
	
	public ComplexPreferences getComplexPreference() {
	    if(complexPrefenreces != null) {
	        return complexPrefenreces;
	    }
	    return null;
	} 
}
