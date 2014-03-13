package covisoft.android.promotionword.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import covisoft.android.promotionword.model.CardInfomation;


import android.app.Activity;

public class ServiceLogin {

	private String numberCardOrEmail;
	private String dateOfBirth;
	private Activity mActivity;
	private InputStream inputStream;
	private String stringJson;
	private CardInfomation cardInfo = null;
	public ServiceLogin(Activity activity, String numberCardOrEmail, String dateOfBirth) {
		// TODO Auto-generated constructor stub
		this.mActivity = activity;
		this.numberCardOrEmail = numberCardOrEmail;
		this.dateOfBirth = dateOfBirth;
	}
	
	public CardInfomation start(){
		String URL = "http://thegioiuudai.com.vn/apps/server.php/member/auth/login?cardNumberOrEmail=" + numberCardOrEmail + "&dateOfBirth=" + dateOfBirth;
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpUriRequest httpGet = new HttpGet(URL);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			inputStream = httpEntity.getContent();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null){
				stringBuilder.append(line + "\n");
			}
			inputStream.close();
			stringJson = stringBuilder.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
            JSONObject jObj = new JSONObject(stringJson);
            String responseCode = jObj.getString("responseCode");
            String spentAmount = jObj.getString("spentAmount");
            String savedAmount = jObj.getString("savedAmount");
            String redeemedPoints = jObj.getString("redeemedPoints");
            String remainingPoints = jObj.getString("remainingPoints");
            cardInfo = new CardInfomation(numberCardOrEmail, dateOfBirth, responseCode, spentAmount, savedAmount, redeemedPoints, remainingPoints);
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return cardInfo;
	}
	

}
