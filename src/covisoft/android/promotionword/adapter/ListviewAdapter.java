/**
 * 
 */
package covisoft.android.promotionword.adapter;

import java.util.List;

import com.squareup.picasso.Picasso;

import covisoft.android.promotionword.R;
import covisoft.android.promotionword.model.Place;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author VuDung (vutiendunguit@gmail.com)
 *
 */
public class ListviewAdapter extends BaseAdapter {

	/**
	 * 
	 */
	
	private Context mContext;
	private List<Place> mListPlace;
	private LayoutInflater mInflater;
	
	public ListviewAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void setListPlace(List<Place> listPlace){
		if(listPlace != null && !listPlace.isEmpty()){
			mListPlace = listPlace;
		}else{
			mListPlace = null;
		}
		notifyDataSetChanged();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return (mListPlace != null ? mListPlace.size() : 0);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mListPlace.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.layout_listview_place, null);
			holder = new ViewHolder();
			holder.imgPlace = (ImageView) convertView.findViewById(R.id.imgPlaceVertical);
			holder.tvNamePlace = (TextView) convertView.findViewById(R.id.tvNamePlaceVertical);
			holder.tvAddressPlace = (TextView) convertView.findViewById(R.id.tvAddressPlaceVertical);
			holder.tvTelPlace = (TextView) convertView.findViewById(R.id.tvTelPlaceVertical);
			holder.tvPromotionPlace = (TextView) convertView.findViewById(R.id.tvPromotionPlaceVertical);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		final Place item = mListPlace.get(position);
		Picasso.with(mContext)
				.load(item.getImage())
				.resize(80, 80)
				.placeholder(R.drawable.empty_photo)
				.error(R.drawable.empty_photo)
				.into(holder.imgPlace);
		holder.tvNamePlace.setText(item.getName());
		holder.tvAddressPlace.setText(item.getAddress());
		holder.tvTelPlace.setText(item.getPhoneNumber());
		if(item.getPromotionPercentage() != null){
			holder.tvPromotionPlace.setText(item.getPromotionPercentage() + "%");
		}else{
			holder.tvPromotionPlace.setText("0%");
		}
		
		return convertView;
	}
	
	private static class ViewHolder{
		private ImageView imgPlace;
		private TextView tvNamePlace;
		private TextView tvAddressPlace;
		private TextView tvTelPlace;
		private TextView tvPromotionPlace;
	}

}
