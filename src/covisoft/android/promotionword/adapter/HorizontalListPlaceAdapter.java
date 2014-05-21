package covisoft.android.promotionword.adapter;

import java.util.List;

import com.squareup.picasso.Picasso;

import covisoft.android.promotionword.R;
import covisoft.android.promotionword.model.Place;
import covisoft.android.promotionword.utils.Util;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HorizontalListPlaceAdapter extends BaseAdapter {
	private Context mContext;
	private List<Place> mListPlace;
	private LayoutInflater mInflater;
	public HorizontalListPlaceAdapter(Context context) {
		mContext = context;
//		mListPlace = null;
		mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setmListPlace(List<Place> listPlace){
		if((listPlace != null) && (!listPlace.isEmpty())){
			mListPlace = listPlace;
		}else{
			mListPlace = null;
		}
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mListPlace != null){
			int count = mListPlace.size();
			return count;
		}
		return 0;
	}

	@Override
	public Place getItem(int position) {
		// TODO Auto-generated method stub
		return mListPlace.get(position);
	}

	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.horizontal_listplace_item, null);
			holder = new ViewHolder();
			holder.llLayoutHorizontallv = (LinearLayout)convertView.findViewById(R.id.llLayoutHorizontallv);
			holder.tvNamePlace = (TextView)convertView.findViewById(R.id.tvNamePlace);
			holder.imgPlace = (ImageView)convertView.findViewById(R.id.imgPlace);
			holder.tvAddressPlace = (TextView)convertView.findViewById(R.id.tvAddressPlace);
			holder.tvPromotionPercent = (TextView)convertView.findViewById(R.id.tvPromotionPercent);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		final ViewGroup mContainer = (ViewGroup)holder.llLayoutHorizontallv;
		Util.setAppFont(mContext, mContainer);
		
		final Place item = mListPlace.get(position);
		Picasso.with(mContext)
				.load(item.getImage())
				.resize(150, 140)
				.placeholder(R.drawable.empty_photo)
				.error(R.drawable.empty_photo)
				.into(holder.imgPlace);
		if(item.getPromotionPercentage() != null){
			holder.tvPromotionPercent.setText(item.getPromotionPercentage()+ "%");
		}else{
			holder.tvPromotionPercent.setText("0%");
		}
//		holder.tvPromotionPercent.setText(item.getPromotionPercentage()+ "%");
		holder.tvNamePlace.setText(item.getName());
		holder.tvAddressPlace.setText(item.getAddress());
		return convertView;
	}
	@Override
	public int getItemViewType(int position) {
		
		return super.getItemViewType(position);
	}
	private static class ViewHolder{
		private LinearLayout llLayoutHorizontallv;
		private ImageView imgPlace;
		private TextView tvPromotionPercent; 
		private TextView tvNamePlace; 
		private TextView tvAddressPlace;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	

}
