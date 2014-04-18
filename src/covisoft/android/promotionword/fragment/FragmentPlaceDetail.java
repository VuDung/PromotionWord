package covisoft.android.promotionword.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.squareup.picasso.Picasso;

import covisoft.android.promotionword.R;
import covisoft.android.promotionword.model.Place;

public class FragmentPlaceDetail extends SherlockFragment implements OnClickListener{

	private ImageView imgPlaceDetail;
	private TextView tvNamePlaceDetail;
	private TextView tvAddressPlaceDetail;
	private TextView tvTelPlaceDetail;
	private TextView tvPromotionPercentPlaceDetail;
	private LinearLayout llAddressPlaceDetail;
	private LinearLayout llCallPlaceDetail;
	private LinearLayout llClickEntry;
	private LinearLayout llUseCondition;
	private LinearLayout llViewFromWP;
	private Place placeItem;
	public FragmentPlaceDetail() {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		placeItem = (Place) getArguments().getSerializable("place_item");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_place_detail, container, false);
		
		Log.d("name place fragment detail place", placeItem.getName());
		imgPlaceDetail = (ImageView)v.findViewById(R.id.imgPlaceDetail);
		tvNamePlaceDetail = (TextView)v.findViewById(R.id.tvNamePlaceDetail);
		tvAddressPlaceDetail = (TextView)v.findViewById(R.id.tvAddressPlaceDetail);
		tvTelPlaceDetail = (TextView)v.findViewById(R.id.tvTelPlaceDetail);
		tvPromotionPercentPlaceDetail = (TextView)v.findViewById(R.id.tvPromotionPercentPlaceDetail);
		llAddressPlaceDetail = (LinearLayout)v.findViewById(R.id.llAddressPlaceDetail);
		llAddressPlaceDetail.setOnClickListener(this);
		llCallPlaceDetail = (LinearLayout)v.findViewById(R.id.llCallPlaceDetail);
		llCallPlaceDetail.setOnClickListener(this);
		llClickEntry = (LinearLayout)v.findViewById(R.id.llClickEntry);
		llClickEntry.setOnClickListener(this);
		llUseCondition = (LinearLayout)v.findViewById(R.id.llUseCondition);
		llUseCondition.setOnClickListener(this);
		llViewFromWP = (LinearLayout)v.findViewById(R.id.llViewFromWP);
		llViewFromWP.setOnClickListener(this);
		Picasso.with(getSherlockActivity())
				.load(placeItem.getImage())
				.fit()
				.placeholder(R.drawable.empty_photo)
				.error(R.drawable.empty_photo)
				.into(imgPlaceDetail);
		tvNamePlaceDetail.setText(placeItem.getName());
		tvAddressPlaceDetail.setText(placeItem.getAddress());
		tvTelPlaceDetail.setText(placeItem.getPhoneNumber());
		tvPromotionPercentPlaceDetail.setText("Giảm " + placeItem.getPromotionPercentage() + " %");
		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
