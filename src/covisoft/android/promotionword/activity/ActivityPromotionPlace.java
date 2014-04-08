package covisoft.android.promotionword.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.meetme.android.horizontallistview.HorizontalListView;

import covisoft.android.promotionword.R;
import covisoft.android.promotionword.adapter.HorizontalListPlaceAdapter;
import covisoft.android.promotionword.fragment.FragmentPlaceDetail;
import covisoft.android.promotionword.model.Place;
import covisoft.android.promotionword.service.ServicePlace;
import covisoft.android.promotionword.utils.CheckTimeAsyncTask;
import covisoft.android.promotionword.utils.Util;

public class ActivityPromotionPlace extends SherlockFragmentActivity {

	private FragmentManager fm = getSupportFragmentManager();
	public ActivityPromotionPlace() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
//		setContentView(R.layout.activity_promotion_place);
//		if(arg0 == null){
//			MainFragment main = new MainFragment();
//			fm.beginTransaction().add(R.id.frameContent, main).commit();
//		}
		if(fm.findFragmentById(android.R.id.content) == null){
			MainFragment main = new MainFragment();
			fm.beginTransaction().add(android.R.id.content, main).commit();
		}
	}

	public static class MainFragment extends SherlockFragment{

		private final String 		TAG = "MainFragment";
		private List<Place> 		staticListPlace = new ArrayList<Place>();
		private List<Place> 		listFood;
		private List<Place> 		listTravel;
		private List<Place> 		listEntertainment;
		private List<Place> 		listHealth;
		private List<Place> 		listCoffeeScream;
		private List<Place> 		listShopping;
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
		
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			View view = inflater.inflate(R.layout.fragment_main, container, false);
			
			mProgressBar = (ProgressBar)view.findViewById(R.id.mProgressBar);
			
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
			
			// check internet connection
			if(Util.isNetworkConnected(getSherlockActivity())){
				if(staticListPlace.size() != 0){
					Log.d(TAG, "staticListPlace not NULL");
					initHorizontalListView();
				}else{
					Log.d(TAG, "staticListPlace NULL");
					AsyncPlace asyncPlace = new AsyncPlace();
					asyncPlace.execute();
					mTimer = new Timer();
					mTimer.schedule(new CheckTimeAsyncTask(getSherlockActivity(), asyncPlace), Util.DELAYTIME);
				}
			}else{
				Toast.makeText(getSherlockActivity(), getResources().getString(R.string.not_connect), Toast.LENGTH_SHORT).show();
			}
			return view;
		}
		
		private class AsyncPlace extends AsyncTask<Void, Void, Void>{

			
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				mProgressBar.setVisibility(View.GONE);
				mTimer.cancel();
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
				ServicePlace svPlace = new ServicePlace(getSherlockActivity());
				try {
					staticListPlace = svPlace.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
		}
		
		private void exeItemOnHorizontalListView(AdapterView<?> parent, int position){
			Place itemAdapter;
			itemAdapter = (Place)parent.getItemAtPosition(position);
			Bundle bundle = new Bundle();
			bundle.putSerializable("place_item", itemAdapter);
			FragmentPlaceDetail fragPlaceDetail = new FragmentPlaceDetail();
			fragPlaceDetail.setArguments(bundle);
			getFragmentManager().beginTransaction().replace(android.R.id.content, fragPlaceDetail)
							.addToBackStack(null)
							.commit();
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

}
