package covisoft.android.promotionword.service;

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

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import covisoft.android.promotionword.model.Place;
import covisoft.android.promotionword.utils.API;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class ServicePlace {

	private List<Place> listPlace;
	private Gson gson = new Gson();
	private Context mContext;
	private InputStream is = null;
	private String json = "";
	private String mUrl;
	
	public ServicePlace(Context context, String Url) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mUrl = Url;
	}
	
	public List<Place> start() throws IOException{
		try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            // post
//            HttpPost httpPost = new HttpPost(URL);
            // get
            Log.d("GetListPlace ===> URL", mUrl);
            HttpUriRequest httpGet = new HttpGet(mUrl);
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
		listPlace = readJsonStream(is);
		return listPlace;
		
	}
	private List<Place> readJsonStream(InputStream in) throws IOException{
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		listPlace = new ArrayList<Place>();
		reader.beginArray();
		while(reader.hasNext()){
			Place place = gson.fromJson(reader, Place.class);
			listPlace.add(place);
		}
		reader.endArray();
		reader.close();
		return listPlace;
	}

}
