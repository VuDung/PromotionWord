package covisoft.android.promotionword.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

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
import covisoft.android.promotionword.utils.CheckTimeAsyncTask;
import covisoft.android.promotionword.utils.Util;

public class FragmentMain extends SherlockFragment{

	private TabBarWithCustomStack mActivity;
	private FragmentTransaction transaction;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		if(activity instanceof TabBarWithCustomStack){
			mActivity = (TabBarWithCustomStack) activity;
		}
	}


	public FragmentMain() {
		// TODO Auto-generated constructor stub
	}
	
	private final String 		TAG = getClass().getSimpleName();
	private static List<Place> 		staticListPlace = new ArrayList<Place>();
	private List<Place> 		listFood;
	private List<Place> 		listTravel;
	private List<Place> 		listEntertainment;
	private List<Place> 		listHealth;
	private List<Place> 		listCoffeeScream;
	private List<Place> 		listShopping;
	private static List<Banner>		mListBanner = new ArrayList<Banner>();
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
	private Timer 				mTimer;
	private ViewPager 			mViewPager;
	private PagerBannerAdapter 	mPagerBannerAdapter;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		
		initUI(view);
		makeRequest();
		
		return view;
	}
	
	private void initUI(View view){
		mProgressBar = (ProgressBar)view.findViewById(R.id.mProgressBar);
		
		mViewPager = (ViewPager)view.findViewById(R.id.pagerBanner);
		
		// init view
		hlvFood = (HorizontalListView)view.findViewById(R.id.hlvFood);
		hlvCoffeeScream = (HorizontalListView)view.findViewById(R.id.hlvCoffeScream);
		hlvEntertainment = (HorizontalListView)view.findViewById(R.id.hlvEntertainment);
		hlvHealth = (HorizontalListView)view.findViewById(R.id.hlvHealth);
		hlvShopping = (HorizontalListView)view.findViewById(R.id.hlvShopping);
		hlvTravel = (HorizontalListView)view.findViewById(R.id.hlvTravel);
		
		// init view category
		rlFood = (RelativeLayout)view.findViewById(R.id.rlFood);
		rlCoffeeScream = (RelativeLayout)view.findViewById(R.id.rlCoffeeScream);
		rlEntertainment = (RelativeLayout)view.findViewById(R.id.rlEntertainment);
		rlHealth = (RelativeLayout)view.findViewById(R.id.rlHealth);
		rlShopping = (RelativeLayout)view.findViewById(R.id.rlShopping);
		rlTravel = (RelativeLayout)view.findViewById(R.id.rlTravel);
	}
	
	private void makeRequest(){
		// check internet connection
		if(Util.isNetworkConnected(getSherlockActivity())){
			
			if(mListBanner != null && !mListBanner.isEmpty()){
				Log.d(TAG, "List Banner not NULL");
				initPagerBanner();
			}else{
				Log.d(TAG, "List Banner NULL");
				AsyncBanner asyncBanner = new AsyncBanner();
				asyncBanner.execute();
			}
			
			if(staticListPlace != null && !staticListPlace.isEmpty()){
				Log.d(TAG, "staticListPlace not NULL");
				initHorizontalListView();
			}else{
				Log.d(TAG, "staticListPlace NULL");
				AsyncPlace asyncPlace = new AsyncPlace();
				asyncPlace.execute();
//				mTimer = new Timer();
//				mTimer.schedule(new CheckTimeAsyncTask(getSherlockActivity(), asyncPlace), Util.DELAYTIME);
			}
		}else{
			Toast.makeText(getSherlockActivity(), getResources().getString(R.string.not_connect), Toast.LENGTH_SHORT).show();
		}
	}
	
	private class AsyncBanner extends AsyncTask<Void, Void, Void>{

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			mTimer.cancel();
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
			if(isCancelled()){
				return null;
			}
			ServiceBanner svBanner = new ServiceBanner(mActivity);
			mListBanner = svBanner.start();
			return null;
			
		}
		
	}
	
	private class AsyncPlace extends AsyncTask<Void, Void, Void>{

		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mProgressBar.setVisibility(View.GONE);
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
			if(isCancelled()){
				return null;
			}
			ServicePlace svPlace = new ServicePlace(mActivity);
			try {
				staticListPlace = svPlace.start();
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
		bundle.putSerializable("place_item", itemAdapter);
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
		for(int i = 0; i< staticListPlace.size(); i++){
			Place item = staticListPlace.get(i);
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
			if(listFood.size() != 0){
				rlFood.setVisibility(View.VISIBLE);
				hlvFood.setAdapter(new HorizontalListPlaceAdapter(getSherlockActivity(), listFood));
				hlvFood.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						exeItemOnHorizontalListView(parent, position);
					}
					
				});
			}else{
				rlFood.setVisibility(View.GONE);
			}
			if(listTravel.size() != 0){
				rlTravel.setVisibility(View.VISIBLE);
				hlvTravel.setAdapter(new HorizontalListPlaceAdapter(getSherlockActivity(), listTravel));
				hlvTravel.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						exeItemOnHorizontalListView(parent, position);
					}
					
				});
			}else{
				rlTravel.setVisibility(View.GONE);
			}
			if(listShopping.size() != 0){
				rlShopping.setVisibility(View.VISIBLE);
				hlvShopping.setAdapter(new HorizontalListPlaceAdapter(getSherlockActivity(), listShopping));
				hlvShopping.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						exeItemOnHorizontalListView(parent, position);
					}
					
				});
			}else{
				rlShopping.setVisibility(View.GONE);
			}
			if(listHealth.size() != 0){
				rlHealth.setVisibility(View.VISIBLE);
				hlvHealth.setAdapter(new HorizontalListPlaceAdapter(getSherlockActivity(), listHealth));
				hlvHealth.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						exeItemOnHorizontalListView(parent, position);
					}
					
				});
			}else{
				rlHealth.setVisibility(View.GONE);
			}
			if(listEntertainment.size() != 0){
				rlEntertainment.setVisibility(View.VISIBLE);
				hlvEntertainment.setAdapter(new HorizontalListPlaceAdapter(getSherlockActivity(), listEntertainment));
				hlvEntertainment.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						exeItemOnHorizontalListView(parent, position);
					}
					
				});
			}else{
				rlEntertainment.setVisibility(View.GONE);
			}
			if(listCoffeeScream.size() != 0){
				rlCoffeeScream.setVisibility(View.VISIBLE);
				hlvCoffeeScream.setAdapter(new HorizontalListPlaceAdapter(getSherlockActivity(), listCoffeeScream));
				hlvCoffeeScream.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						exeItemOnHorizontalListView(parent, position);
					}
					
				});
			}else{
				rlCoffeeScream.setVisibility(View.GONE);
			}
		}
	}

}