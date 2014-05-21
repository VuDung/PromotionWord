package covisoft.android.promotionword.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.meetme.android.horizontallistview.HorizontalListView;

import covisoft.android.promotionword.R;
import covisoft.android.promotionword.TabBarWithCustomStack;
import covisoft.android.promotionword.adapter.HorizontalListPlaceAdapter;
import covisoft.android.promotionword.banner.Banner;
import covisoft.android.promotionword.banner.PagerBannerAdapter;
import covisoft.android.promotionword.model.Place;
import covisoft.android.promotionword.service.ServiceBanner;
import covisoft.android.promotionword.service.ServicePlace;
import covisoft.android.promotionword.utils.API;
import covisoft.android.promotionword.utils.Constants;
import covisoft.android.promotionword.utils.Util;

public class FragmentMain extends SherlockFragment implements OnItemClickListener, OnClickListener{

	
	private TabBarWithCustomStack mActivity;
	private FragmentTransaction transaction;
	//private final String 		TAG = getClass().getSimpleName();
	private static boolean 		isBannerRequested = false;
	private static boolean 		isPlaceRequested = false;
	private List<Place> 		listFood;
	private List<Place> 		listTravel;
	private List<Place> 		listEntertainment;
	private List<Place> 		listHealth;
	private List<Place> 		listCoffeeScream;
	private List<Place> 		listShopping;
	private static List<Place> 	mListPlace = new ArrayList<Place>();
	private static List<Banner>	mListBanner = new ArrayList<Banner>();
	private ProgressBar 		mProgressBar;
	private HorizontalListView 	hlvFood;
	private HorizontalListView 	hlvTravel;
	private HorizontalListView 	hlvEntertainment;
	private HorizontalListView 	hlvHealth;
	private HorizontalListView 	hlvCoffeeScream;
	private HorizontalListView 	hlvShopping;
	private RelativeLayout 		rlFood;
	private RelativeLayout 		rlTravel;
	private RelativeLayout 		rlEntertainment;
	private RelativeLayout 		rlHealth;
	private RelativeLayout 		rlCoffeeScream;
	private RelativeLayout 		rlShopping;
	private RelativeLayout 		rlLayoutFragMain;
	private ViewPager 			mViewPager;
	private PagerBannerAdapter 	mPagerBannerAdapter;
	private HorizontalListPlaceAdapter mHorizontalAdapter;
	private TextView			mTvCityActionbar;
	private String[]			mCityID;
	private static String 		mCityValue;
	private static String		mCityIdSelected;
	private String[] 			mCityArray;
	private ListView 			lvCityDialog;
	private AlertDialog 		dialogCity;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		if(activity instanceof TabBarWithCustomStack){
			mActivity = (TabBarWithCustomStack) activity;
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		
		setUpActionBar();
		initUI(view);
		makeRequest();
		
		return view;
	}
	
	private void setUpActionBar(){
		
		ActionBar mActionBar = mActivity.getSupportActionBar();
		mActionBar.setDisplayShowTitleEnabled(false);
		
		//LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,Gravity.CENTER_VERTICAL);
		View customActionbar = LayoutInflater.from(mActivity).inflate(R.layout.custom_actionbar, null);
		mActionBar.setCustomView(customActionbar);
		mActionBar.setDisplayShowCustomEnabled(true);
		
		if(mCityValue == null){
			mCityValue = Util.getCityValue(mActivity);
			if(mCityValue == null){
				mCityValue = "TP HCM";
			}
		}
		if(mCityIdSelected == null){
			mCityIdSelected = Util.getCityIdValue(mActivity);
			if(mCityIdSelected == null){
				mCityIdSelected = "30";
			}
		}
		
		mTvCityActionbar = (TextView)customActionbar.findViewById(R.id.tvCityActionbar);
		mTvCityActionbar.setText(mCityValue);
		LinearLayout llCity = (LinearLayout) customActionbar.findViewById(R.id.llCity);
		final ViewGroup mContainerCity = (ViewGroup)llCity;
		Util.setAppFont(mActivity, mContainerCity);
		llCity.setOnClickListener(this);
		
	}
	
	private void initUI(View view){
		mProgressBar = (ProgressBar)view.findViewById(R.id.mProgressBar);
		
		mViewPager = (ViewPager)view.findViewById(R.id.pagerBanner);
		
		// init view
		hlvFood = (HorizontalListView)view.findViewById(R.id.hlvFood);
		hlvFood.setOnItemClickListener(this);
		
		hlvCoffeeScream = (HorizontalListView)view.findViewById(R.id.hlvCoffeScream);
		hlvCoffeeScream.setOnItemClickListener(this);
		
		hlvEntertainment = (HorizontalListView)view.findViewById(R.id.hlvEntertainment);
		hlvEntertainment.setOnItemClickListener(this);
		
		hlvHealth = (HorizontalListView)view.findViewById(R.id.hlvHealth);
		hlvHealth.setOnItemClickListener(this);
		
		hlvShopping = (HorizontalListView)view.findViewById(R.id.hlvShopping);
		hlvShopping.setOnItemClickListener(this);
		
		hlvTravel = (HorizontalListView)view.findViewById(R.id.hlvTravel);
		hlvTravel.setOnItemClickListener(this);
		
		// init view category
		rlFood = (RelativeLayout)view.findViewById(R.id.rlFood);
		rlCoffeeScream = (RelativeLayout)view.findViewById(R.id.rlCoffeeScream);
		rlEntertainment = (RelativeLayout)view.findViewById(R.id.rlEntertainment);
		rlHealth = (RelativeLayout)view.findViewById(R.id.rlHealth);
		rlShopping = (RelativeLayout)view.findViewById(R.id.rlShopping);
		rlTravel = (RelativeLayout)view.findViewById(R.id.rlTravel);
		
		mHorizontalAdapter = new HorizontalListPlaceAdapter(mActivity);
		
		hlvFood.setAdapter(mHorizontalAdapter);
		hlvCoffeeScream.setAdapter(mHorizontalAdapter);
		hlvEntertainment.setAdapter(mHorizontalAdapter);
		hlvHealth.setAdapter(mHorizontalAdapter);
		hlvShopping.setAdapter(mHorizontalAdapter);
		hlvTravel.setAdapter(mHorizontalAdapter);
		
		rlLayoutFragMain = (RelativeLayout)view.findViewById(R.id.rlLayoutFragMain);
		
		final ViewGroup mContainer = (ViewGroup)rlLayoutFragMain;
		Util.setAppFont(mActivity, mContainer);
	}
	
	private void makeRequest(){
		// check internet connection
		if(Util.isNetworkConnected(mActivity)){
			if(isBannerRequested == false){
				AsyncBanner asyncBanner = new AsyncBanner();
				asyncBanner.execute();
				
			}else{
				initPagerBanner();
				
			}
			if(isPlaceRequested == false){
				AsyncPlace asyncPlace = new AsyncPlace();
				asyncPlace.execute();
			}else{
				initHorizontalListView();
			}
		}else{
			Toast.makeText(mActivity, getResources().getString(R.string.not_connect), Toast.LENGTH_SHORT).show();
		}
	}
	
	/*
	 * AsyncTask class
	 */
	private class AsyncBanner extends AsyncTask<Void, Void, Void>{

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			mTimer.cancel();
			isBannerRequested = true;
			initPagerBanner();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ServiceBanner svBanner = new ServiceBanner(mActivity);
			mListBanner = svBanner.start();
			return null;
			
		}
		
	}
	
	/*
	 * AsyncTask class
	 */
	private class AsyncPlace extends AsyncTask<Void, Void, Void>{

		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mProgressBar.setVisibility(View.GONE);
			isPlaceRequested = true;
//			mTimer.cancel();
			initHorizontalListView();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(mProgressBar.getVisibility() == View.GONE){
				mProgressBar.setVisibility(View.VISIBLE);
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String url = API.urlRequestPlaceByCity;
			Uri.Builder builder = Uri.parse(url).buildUpon();
			builder.appendQueryParameter("city", mCityIdSelected);
			ServicePlace svPlace = new ServicePlace(mActivity, builder.toString());
			try {
				mListPlace = svPlace.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
	private void initPagerBanner(){
		mPagerBannerAdapter = new PagerBannerAdapter(getFragmentManager(), mListBanner);
		mViewPager.setAdapter(mPagerBannerAdapter);
	}
	
	private void exeItemOnHorizontalListView(AdapterView<?> parent, int position){
		
		Place itemAdapter;
		itemAdapter = (Place)parent.getItemAtPosition(position);
		Bundle bundle = new Bundle();
		bundle.putSerializable(Constants.PLACE_ITEM, itemAdapter);
		FragmentPlaceDetail fragPlaceDetail = new FragmentPlaceDetail();
		fragPlaceDetail.setArguments(bundle);
//		((LinearLayout)findViewById(R.id.realtabcontent)).removeAllViews();
		transaction = mActivity.getSupportFragmentManager().beginTransaction();
		
		transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
		transaction.replace(R.id.realtabcontent, fragPlaceDetail);
		
		//add to custom backstack
		TabBarWithCustomStack.customBackStack.get(TabBarWithCustomStack.mTabHost.getCurrentTabTag()).add(fragPlaceDetail);
		
		transaction.commit();
	}
	
	private void initHorizontalListView(){
		listFood = new ArrayList<Place>();
		listTravel = new ArrayList<Place>();
		listShopping = new ArrayList<Place>();
		listHealth = new ArrayList<Place>();
		listEntertainment = new ArrayList<Place>();
		listCoffeeScream = new ArrayList<Place>();
		for(int i = 0; i< mListPlace.size(); i++){
			Place item = mListPlace.get(i);
			if(item.getCategory().equalsIgnoreCase("1")){
				listFood.add(item);
			}else if (item.getCategory().equalsIgnoreCase("2")) {
				listTravel.add(item);
			}else if (item.getCategory().equalsIgnoreCase("3")) {
				listShopping.add(item);
			}else if (item.getCategory().equalsIgnoreCase("4")) {
				listHealth.add(item);
			}else if (item.getCategory().equalsIgnoreCase("5")) {
				listEntertainment.add(item);
			}else if (item.getCategory().equalsIgnoreCase("6")) {
				listCoffeeScream.add(item);
			}
			if(listFood != null && !listFood.isEmpty()){
				mHorizontalAdapter.setmListPlace(listFood);
				rlFood.setVisibility(View.VISIBLE);
			}else{
				rlFood.setVisibility(View.GONE);
			}
			if(listTravel != null && !listTravel.isEmpty()){
				mHorizontalAdapter.setmListPlace(listTravel);
				rlTravel.setVisibility(View.VISIBLE);
			}else{
				rlTravel.setVisibility(View.GONE);
			}
			if(listShopping != null && !listShopping.isEmpty()){
				mHorizontalAdapter.setmListPlace(listShopping);
				rlShopping.setVisibility(View.VISIBLE);
			}else{
				rlShopping.setVisibility(View.GONE);
			}
			if(listHealth != null && !listHealth.isEmpty()){
				mHorizontalAdapter.setmListPlace(listHealth);
				rlHealth.setVisibility(View.VISIBLE);
			}else{
				rlHealth.setVisibility(View.GONE);
			}
			if(listEntertainment != null && !listEntertainment.isEmpty()){
				mHorizontalAdapter.setmListPlace(listEntertainment);
				rlEntertainment.setVisibility(View.VISIBLE);
			}else{
				rlEntertainment.setVisibility(View.GONE);
			}
			if(listCoffeeScream != null && !listCoffeeScream.isEmpty()){
				mHorizontalAdapter.setmListPlace(listCoffeeScream);
				rlCoffeeScream.setVisibility(View.VISIBLE);
			}else{
				rlCoffeeScream.setVisibility(View.GONE);
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if(parent == lvCityDialog){
			
			
			mCityValue = mCityArray[position];
			mCityIdSelected = mCityID[position];
			
			Util.setCityPrefs(mActivity, mCityValue, mCityIdSelected);
			dialogCity.dismiss();
			
			mTvCityActionbar.setText(mCityValue);
			AsyncPlace asyncPlace = new AsyncPlace();
			asyncPlace.execute();
			
		}else{
			exeItemOnHorizontalListView(parent, position);
		}
		
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.llCity:
			View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.dialog_listview, null);
			LinearLayout llCityDialog = (LinearLayout)dialogView.findViewById(R.id.llCityDialog);
			final ViewGroup mContainerDialog = (ViewGroup)llCityDialog;
			Util.setAppFont(mActivity, mContainerDialog);
			String titleDialog = "City";
			AlertDialog.Builder buildercity = new Builder(mActivity);
			buildercity.setTitle(titleDialog)
						.setNegativeButton("Close", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						})
						.setView(dialogView);
			dialogCity = buildercity.create();
			dialogCity.show();			
			//Util.createDialogCustomView(mActivity, dialogView, titleDialog);
			
			mCityArray = mActivity.getResources().getStringArray(R.array.cityName);
			mCityID = mActivity.getResources().getStringArray(R.array.cityValues);
			
			ArrayAdapter<String> mLvAdapter = new ArrayAdapter<String>(mActivity, R.layout.sherlock_spinner_dropdown_item, mCityArray);
			lvCityDialog = (ListView)dialogView.findViewById(R.id.lvCityDialog);
			lvCityDialog.setAdapter(mLvAdapter);
			lvCityDialog.setOnItemClickListener(this);
			break;

		default:
			break;
		}
	}

	

}
