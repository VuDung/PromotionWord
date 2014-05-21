package covisoft.android.promotionword.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.squareup.picasso.Picasso;

import covisoft.android.promotionword.R;
import covisoft.android.promotionword.TabBarWithCustomStack;
import covisoft.android.promotionword.model.Place;
import covisoft.android.promotionword.utils.Constants;
import covisoft.android.promotionword.utils.Util;

public class FragmentPlaceDetail extends SherlockFragment implements OnClickListener{

	private ImageView 				imgPlaceDetail;
	private TextView 				tvNamePlaceDetail;
	private TextView 				tvAddressPlaceDetail;
	private TextView 				tvTelPlaceDetail;
	private TextView 				tvPromotionPercentPlaceDetail;
	private LinearLayout 			llAddressPlaceDetail;
	private LinearLayout 			llCallPlaceDetail;
	private LinearLayout 			llClickEntry;
	private LinearLayout 			llUseCondition;
	private LinearLayout 			llViewFromWP;
	private ScrollView				svLayoutFragPlaceDetail;
	private Place 					placeItem;
	private TabBarWithCustomStack 	mActivity;
	private FragmentTransaction 	transaction;
	
	public FragmentPlaceDetail() {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		if(activity instanceof TabBarWithCustomStack){
			mActivity = (TabBarWithCustomStack) activity;
		}
		placeItem = (Place) getArguments().getSerializable(Constants.PLACE_ITEM);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_place_detail, container, false);
		
		initView(v);
		
		llAddressPlaceDetail.setOnClickListener(this);
		llCallPlaceDetail.setOnClickListener(this);
		llClickEntry.setOnClickListener(this);
		llUseCondition.setOnClickListener(this);
		llViewFromWP.setOnClickListener(this);
		return v;
	}
	
	private void initView(View v){
		svLayoutFragPlaceDetail = (ScrollView)v.findViewById(R.id.svLayoutFragPlaceDetail);
		
		imgPlaceDetail = (ImageView)v.findViewById(R.id.imgPlaceDetail);
		tvNamePlaceDetail = (TextView)v.findViewById(R.id.tvNamePlaceDetail);
		tvAddressPlaceDetail = (TextView)v.findViewById(R.id.tvAddressPlaceDetail);
		tvTelPlaceDetail = (TextView)v.findViewById(R.id.tvTelPlaceDetail);
		tvPromotionPercentPlaceDetail = (TextView)v.findViewById(R.id.tvPromotionPercentPlaceDetail);
		
		llAddressPlaceDetail = (LinearLayout)v.findViewById(R.id.llAddressPlaceDetail);
		llCallPlaceDetail = (LinearLayout)v.findViewById(R.id.llCallPlaceDetail);
		llClickEntry = (LinearLayout)v.findViewById(R.id.llClickEntry);
		llUseCondition = (LinearLayout)v.findViewById(R.id.llUseCondition);
		llViewFromWP = (LinearLayout)v.findViewById(R.id.llViewFromWP);
		
		final ViewGroup mContainer = (ViewGroup)svLayoutFragPlaceDetail;
		Util.setAppFont(mActivity, mContainer);
		
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
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.llAddressPlaceDetail:
			FragmentMapPlace fragMapPlace = new FragmentMapPlace();
			Bundle bundle = new Bundle();
			bundle.putSerializable(Constants.PLACE_ITEM, placeItem);
			fragMapPlace.setArguments(bundle);
			//transaction
			transaction = mActivity.getSupportFragmentManager().beginTransaction();
			transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
			transaction.replace(R.id.realtabcontent, fragMapPlace);
			//add to custom back stack
			TabBarWithCustomStack.customBackStack.get(TabBarWithCustomStack.mTabHost.getCurrentTabTag()).add(fragMapPlace);
			transaction.commit();
			break;
		case R.id.llCallPlaceDetail:
			AlertDialog.Builder builderCall = new Builder(mActivity);
			builderCall.setTitle("Call");
			builderCall.setMessage(R.string.do_u_want_call_them);
			builderCall.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String tel = "tel:" + tvTelPlaceDetail.getText().toString();
					Intent iCall = new Intent(Intent.ACTION_CALL);
					iCall.setData(Uri.parse(tel));
					startActivity(iCall);
				}
			});
			builderCall.setPositiveButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			
			AlertDialog dialogCall = builderCall.create();
			dialogCall.show();
			break;
		case R.id.llClickEntry:
			final Dialog dialogEntry = new Dialog(getActivity());
			dialogEntry.setTitle(placeItem.getName());
			dialogEntry.setCancelable(true);
			dialogEntry.setContentView(R.layout.dialog_webview);
			dialogEntry.show();
			WebView wv = (WebView)dialogEntry.findViewById(R.id.wvEntryItem);
//			wv.getSettings().setJavaScriptEnabled(true);
			wv.loadData(placeItem.getDescription(), "text/html; charset=UTF-8", null);
			Button btnCloseIn = (Button)dialogEntry.findViewById(R.id.btnCloseIn);
			btnCloseIn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialogEntry.dismiss();
				}
			});
			break;
		case R.id.llUseCondition:
			String contentUseCondition = Html.fromHtml(placeItem.getConditions()).toString();
			createDialog(getResources().getString(R.string.use_condition), contentUseCondition);
			break;
		case R.id.llViewFromWP:
			String contentViewFromWP = Html.fromHtml(placeItem.getFeatures()).toString();
			createDialog(getResources().getString(R.string.view_from_wordpromotion), contentViewFromWP);
			break;
		default:
			break;
		}
	}
	
	private void createDialog(String title, String content){
		View dialogView = mActivity.getLayoutInflater().inflate(R.layout.dialog_entry_place, null);
		AlertDialog.Builder builderEntry = new Builder(mActivity);
		builderEntry.setTitle(title);
		builderEntry.setNegativeButton("Close", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builderEntry.setView(dialogView);
		AlertDialog dialog = builderEntry.create();
		dialog.show();
		TextView tvContent = (TextView)dialogView.findViewById(R.id.tvEntryContent);
		tvContent.setText(content);
	}

}
