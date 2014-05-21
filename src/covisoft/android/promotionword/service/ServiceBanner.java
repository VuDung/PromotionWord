package covisoft.android.promotionword.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import covisoft.android.promotionword.banner.Banner;
import covisoft.android.promotionword.utils.API;

import android.app.Activity;
import android.util.Log;

public class ServiceBanner {

	private Activity mActivity;
	private List<Banner> listBanner;
	private String Url;
	private InputStream is;
	private String json;
	private String TAG = ServiceBanner.this.getClass().getSimpleName();
	
	public ServiceBanner(Activity activity) {
		// TODO Auto-generated constructor stub
		this.mActivity = activity;
	}
	
	public List<Banner> start(){
		listBanner = new ArrayList<Banner>();
		Url = API.requestGetBanner;
		Log.i(TAG, Url);
		try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpUriRequest httpGet = new HttpGet(Url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();  
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        try {
			JSONArray jArray = new JSONArray(json);
            for (int i= 0; i< jArray.length(); i++){
            	JSONObject jObj = jArray.getJSONObject(i);
				String bannerId = jObj.getString("bannerId");
				String bannerName = jObj.getString("name");
				
				String bannerImageUrl = jObj.getString("image");
				String bannerImage = bannerImageUrl.replaceAll(" ", "%20");
				String bannerUrl = jObj.getString("url");
				String bannerOrder = jObj.getString("order");
				listBanner.add(new Banner(bannerId, bannerName, bannerImage, bannerUrl, bannerOrder));
            }

        } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listBanner;
	}

}
