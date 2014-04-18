package covisoft.android.promotionword.banner;

import com.actionbarsherlock.app.SherlockFragment;
import com.squareup.picasso.Picasso;

import covisoft.android.promotionword.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class FragmentBanner extends SherlockFragment {

	private String bannerImageUrl;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		bannerImageUrl = args.getString("bannerImageUrl");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ImageView imageBanner = new ImageView(getActivity());
		LinearLayout.LayoutParams vp = 
			    new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 
			                    LayoutParams.MATCH_PARENT);
		imageBanner.setLayoutParams(vp);
		imageBanner.setScaleType(ImageView.ScaleType.FIT_XY);
		Picasso.with(getActivity())
				.load(bannerImageUrl)
				.fit()
				.placeholder(R.drawable.empty_photo)
				.into(imageBanner);
		return imageBanner;
	}
	
}
