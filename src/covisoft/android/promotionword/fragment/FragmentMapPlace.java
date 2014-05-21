package covisoft.android.promotionword.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import covisoft.android.promotionword.R;
import covisoft.android.promotionword.TabBarWithCustomStack;
import covisoft.android.promotionword.adapter.ListviewAdapter;
import covisoft.android.promotionword.model.Place;
import covisoft.android.promotionword.service.ServicePlace;
import covisoft.android.promotionword.utils.API;
import covisoft.android.promotionword.utils.Constants;
import covisoft.android.promotionword.utils.Util;

public class FragmentMapPlace extends SherlockFragment implements OnClickListener, OnItemClickListener{

	private TabBarWithCustomStack mActivity;
	private List<Place> mListPlace = new ArrayList<Place>();
	private Place placeItem;
	private GoogleMap mMap;
	private Double mLatPlace;
	private Double mLngPlace;
	private LatLng mLatLngPlace;
	private LinearLayout llFindNearby;
	private LinearLayout llNearPlace;
	private TextView tvFindNearby;
	private ListView lvNearPlace;
	private ProgressBar pbMapPlace;
	private FragmentTransaction transaction;
	private ListviewAdapter mAdapter;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		if(activity instanceof TabBarWithCustomStack){
			mActivity = (TabBarWithCustomStack) activity;
		}
		placeItem = (Place)getArguments().getSerializable(Constants.PLACE_ITEM);
	}

	public FragmentMapPlace() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_map_place, container, false);
		
		setUpMapIfNeeded();
		setUpView(v);
		return v;
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		SupportMapFragment f = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.fragMapPlace);
		if(f != null){
			mActivity.getSupportFragmentManager().beginTransaction().remove(f).commit();
		}
	}
	
	

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private void setUpView(View v){
		pbMapPlace = (ProgressBar)v.findViewById(R.id.pbMapPlace);
		
		llFindNearby = (LinearLayout)v.findViewById(R.id.llFindNearby);
		llFindNearby.setOnClickListener(this);
		
		llNearPlace = (LinearLayout)v.findViewById(R.id.llNearPlace);
		tvFindNearby = (TextView)v.findViewById(R.id.tvFindNearby);
		
		lvNearPlace = (ListView)v.findViewById(R.id.lvNearPlace);
		lvNearPlace.setOnItemClickListener(this);
		
		tvFindNearby.setText(getResources().getString(setTextTextView(llNearPlace)));
		
		mAdapter = new ListviewAdapter(mActivity);
		lvNearPlace.setAdapter(mAdapter);
	}
	
	private void setUpMapIfNeeded(){
		if(mMap == null){
			FragmentManager fragManager = mActivity.getSupportFragmentManager();
			mMap = ((SupportMapFragment)fragManager.findFragmentById(R.id.fragMapPlace)).getMap();
			
			if(mMap != null){
				setUpMap();
			}
		}
	}
	
	private void setUpMap(){
		mLatPlace = Double.parseDouble(placeItem.getLat());
		mLngPlace = Double.parseDouble(placeItem.getLng());
		mLatLngPlace = new LatLng(mLatPlace, mLngPlace);
		mMap.addMarker(new MarkerOptions().position(mLatLngPlace)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_my_position))
				.title(placeItem.getAddress()
				));
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mLatLngPlace, 17);
		mMap.animateCamera(cameraUpdate);	
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.llFindNearby:
			
//			setViewVisibility(llNearPlace, llNearPlace.getVisibility() == View.VISIBLE);
			if(llNearPlace.getVisibility() == View.VISIBLE){
				llNearPlace.setVisibility(View.GONE);
				tvFindNearby.setText(getResources().getString(setTextTextView(llNearPlace)));
			}else{
				makeRequestFindNearby();
			}
			break;

		default:
			break;
		}
	}
	
//	private void setViewVisibility(View v, boolean b){
//		v.setVisibility(b ? View.GONE : View.VISIBLE);
//	}
	
	private int setTextTextView(View v){
		return (v.getVisibility() == View.GONE ? R.string.find_nearby : R.string.hide_find_nearby);
	}
	
	private void makeRequestFindNearby(){
		if(Util.isNetworkConnected(mActivity)){
			AsyncFindNearby async = new AsyncFindNearby();
			async.execute();
		}else{
			Toast.makeText(mActivity, getResources().getString(R.string.not_connect), Toast.LENGTH_SHORT).show();
		}
	}
	
	private class AsyncFindNearby extends AsyncTask<Void, Void, Void>{

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		
		
		
		@Override
		protected Void doInBackground(Void... params) {
			String url = API.urlRequestPlaceNearby;
			Uri.Builder builder = Uri.parse(url).buildUpon();
			builder.appendQueryParameter("currentLat", Double.toString(mLatPlace));
			builder.appendQueryParameter("currentLong", Double.toString(mLngPlace));
			builder.appendQueryParameter("distantLimit", "3");
			ServicePlace servicePlace = new ServicePlace(mActivity, builder.toString());
			try {
				mListPlace = servicePlace.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pbMapPlace.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			pbMapPlace.setVisibility(View.GONE);
			llNearPlace.setVisibility(View.VISIBLE);
			
			tvFindNearby.setText(getResources().getString(setTextTextView(llNearPlace)));
			
			mAdapter.setListPlace(mListPlace);
		}
		
	}

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if(parent == lvNearPlace){
			
			Place itemAdapter;
			itemAdapter = (Place) parent.getItemAtPosition(position);
			FragmentPlaceDetail fragDetail = new FragmentPlaceDetail();
			Bundle bundle = new Bundle();
			bundle.putSerializable(Constants.PLACE_ITEM, itemAdapter);
			fragDetail.setArguments(bundle);
			
			transaction = mActivity.getSupportFragmentManager().beginTransaction();
			transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
			transaction.replace(R.id.realtabcontent, fragDetail);
			//add to custom back stack
			TabBarWithCustomStack.customBackStack.get(TabBarWithCustomStack.mTabHost.getCurrentTabTag()).add(fragDetail);
			transaction.commit();
		}
	}
}
