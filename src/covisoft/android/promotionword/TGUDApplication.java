package covisoft.android.promotionword;

import covisoft.android.promotionword.utils.GPSTracker;
import android.app.Application;

public class TGUDApplication extends Application {
	
    private 		ComplexPreferences 	complexPrefenreces = null;
    private 		GPSTracker 			mGPSTracker;
    private static 	TGUDApplication 	instance;
    

    public static TGUDApplication getInstance(){
    	return instance;
    }
    
	@Override
	public void onCreate() {
	    super.onCreate();
	    instance = this;
	}
	
	public ComplexPreferences getComplexPreference() {
	    if(complexPrefenreces != null) {
	        return complexPrefenreces;
	    }else{
	    	return complexPrefenreces = ComplexPreferences.getComplexPreferences(getBaseContext(), "TGUDPreferenceKey", MODE_PRIVATE);
	    	
	    }
	} 
	
	public GPSTracker getGPS(){
		if(mGPSTracker != null){
			return mGPSTracker;
		}else{
			return new GPSTracker(this);
		}
	}
	
	
}
