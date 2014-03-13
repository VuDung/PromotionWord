package covisoft.android.promotionword;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.actionbarsherlock.app.SherlockActivity;

import covisoft.android.promotionword.model.CardInfomation;
import covisoft.android.promotionword.service.ServiceLogin;
import covisoft.android.promotionword.utils.Util;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoginHomeActivity extends SherlockActivity implements OnClickListener {
	private TGUDApplication tgudApplication;
	private final String TAG = "LoginHomeActivity";
	private EditText txtNumberCardEmail;
	private EditText txtDateOfBirth;
	private Button btnLogin;
	private Button btnCallHotline;
	private LinearLayout llLogo;
	private LinearLayout llLoginForm;
	private ProgressBar pbLogin;
	private TextView tvBreakLogin;
	private Activity mActivity;
	private String cardNumberOrEmailValue;
	private String dateOfBirthValue;
	private CardInfomation cardInfo;
	SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_home);
        mActivity = this;
        tgudApplication = (TGUDApplication)mActivity.getApplication();
        // set font type for this activity
        final Typeface mFont = Typeface.createFromAsset(mActivity.getAssets(), "SFUHelveticaLight.ttf"); 
		final ViewGroup mContainer = (ViewGroup)findViewById(android.R.id.content).getRootView();
		Util.setAppFont(mContainer, mFont);
		//setup view
		initView();
        //get shared preferend card info login
        ComplexPreferences pref = tgudApplication.getComplexPreference();
        if(pref != null){
        	CardInfomation cardInfo = pref.getObject(CardInfomation.CARDINFORMATION, CardInfomation.class);
        	if(cardInfo != null && cardInfo.getResponseCode().equalsIgnoreCase("0")){
        		String numberCardOrEmailValue = cardInfo.getCardNumberOrEmail();
        		Log.d("LoginHomeActivity", numberCardOrEmailValue);
            	String dateOfBirthValue = cardInfo.getDateOfBirth();
            	Log.d("LoginHomeActivity", dateOfBirthValue);
            	txtNumberCardEmail.setText(numberCardOrEmailValue);
            	txtDateOfBirth.setText(dateOfBirthValue);
        	}
        }
        // set onclicked view
        txtDateOfBirth.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnCallHotline.setOnClickListener(this);
        tvBreakLogin.setOnClickListener(this);
        
    }
    
    private void initView(){
    	
    	llLogo = (LinearLayout)findViewById(R.id.llLogo);
		llLoginForm = (LinearLayout)findViewById(R.id.llLoginForm);
		initAnimation();
		pbLogin = (ProgressBar)findViewById(R.id.pbLogin);
        txtNumberCardEmail = (EditText)findViewById(R.id.txtNumberCardEmail);
        txtDateOfBirth = (EditText)findViewById(R.id.txtDateOfBirth);
        txtDateOfBirth.setInputType(InputType.TYPE_NULL);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnCallHotline = (Button)findViewById(R.id.btnCallHotline);
        tvBreakLogin = (TextView)findViewById(R.id.tvBreakLogin);
    }
    
    private void initAnimation(){
    	Handler handlerAnim = new Handler();
    	handlerAnim.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mActivity.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							if(llLoginForm.getVisibility() == View.GONE){
								llLoginForm.setVisibility(View.VISIBLE);
								Animation animUp = AnimationUtils.loadAnimation(mActivity, R.anim.show_up);
								Animation animLeft = AnimationUtils.loadAnimation(mActivity, R.anim.show_left);
								llLogo.startAnimation(animUp);
								llLoginForm.startAnimation(animLeft);
							}
							
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				});
			}
		}, 2000);
    }
    Calendar mCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateListener = new OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mCalendar.set(Calendar.YEAR, year);
			mCalendar.set(Calendar.MONTH, monthOfYear);
			mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			updateTxtDateOfBirth();
		}
	};
	
	private void updateTxtDateOfBirth(){
		String mFormat = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(mFormat, Locale.US);
		txtDateOfBirth.setText(sdf.format(mCalendar.getTime()));
	}


	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txtDateOfBirth:
			new DatePickerDialog(
					LoginHomeActivity.this, 
					dateListener, 
					mCalendar.get(Calendar.YEAR), 
					mCalendar.get(Calendar.MONTH), 
					mCalendar.get(Calendar.DAY_OF_MONTH)).show();
			break;
		
		case R.id.btnLogin:
			cardNumberOrEmailValue = txtNumberCardEmail.getText().toString();
			dateOfBirthValue = txtDateOfBirth.getText().toString();
			ConnectivityManager connectMgr = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = connectMgr.getActiveNetworkInfo();
			if(netInfo != null && netInfo.isConnected()){
				new LoginTask().execute();
				
			}else{
				Toast.makeText(LoginHomeActivity.this, "Your device is not connect network", Toast.LENGTH_LONG).show();
			}
			
			break;
		case R.id.tvBreakLogin:
			Intent iMainActivity = new Intent(LoginHomeActivity.this, MainActivity.class);
			startActivity(iMainActivity);
			break;
		case R.id.btnCallHotline:
			AlertDialog.Builder builder = new Builder(LoginHomeActivity.this);
			builder.setTitle("Call");
			builder.setMessage(R.string.do_u_want_call);
			builder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent iCall = new Intent(Intent.ACTION_CALL);
					iCall.setData(Uri.parse("tel:0995393933"));
					startActivity(iCall);
				}
			});
			builder.setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			AlertDialog dialogCall = builder.create();
			dialogCall.show();
			break;
		default:
			break;
		}
	}
	
	private class LoginTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pbLogin.setVisibility(View.GONE);
			if(cardInfo == null){
//				new Process
			}else{
				//login success
				if(cardInfo.getResponseCode().equalsIgnoreCase("0")){
					Toast.makeText(LoginHomeActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();
					// save info login into share preference
					ComplexPreferences pref = tgudApplication.getComplexPreference();
					if(pref != null){
						pref.putObject(CardInfomation.CARDINFORMATION, cardInfo);
						pref.commit();
					}else{
						Log.e(TAG, "Preference is null");
					}
					Intent iMainActivity = new Intent(LoginHomeActivity.this, TabHostActivity.class);
					startActivity(iMainActivity);
				}else{
					// login fail
					if(cardInfo.getResponseCode().equalsIgnoreCase("1")){
						Toast.makeText(LoginHomeActivity.this, R.string.login_wrong_info, Toast.LENGTH_SHORT).show();
					}else if (cardInfo.getResponseCode().equalsIgnoreCase("2")) {
						Toast.makeText(LoginHomeActivity.this, R.string.info_not_availeble, Toast.LENGTH_SHORT).show();
					}else if (cardInfo.getResponseCode().equalsIgnoreCase("3")) {
						Toast.makeText(LoginHomeActivity.this, R.string.card_not_access, Toast.LENGTH_SHORT).show();
					}
				}
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pbLogin.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ServiceLogin login = new ServiceLogin(mActivity, cardNumberOrEmailValue, dateOfBirthValue);
			cardInfo = login.start();
			return null;
		}
		
	}
    
}
