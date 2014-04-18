package covisoft.android.promotionword.banner;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class PagerBannerAdapter extends FragmentStatePagerAdapter{
	private List<Banner> listBanner;
	
	@Override
	public Fragment getItem(int arg0) {
		
		Fragment fragBanner = new FragmentBanner();
		final Banner banner = listBanner.get(arg0);
		Bundle args = new Bundle();
		args.putString("bannerImageUrl", banner.getBannerImageUrl());
		fragBanner.setArguments(args);
		return fragBanner;
	}

//	@Override
//	public void destroyItem(ViewGroup container, int position, Object object) {
//		// TODO Auto-generated method stub
////		super.destroyItem(container, position, object);
////		container.removeView((View)object);
//		container.removeViewAt(position);
//	}

	@Override
	public int getCount() {
		return listBanner.size();
	}

	public PagerBannerAdapter(FragmentManager fm, List<Banner> listBanner) {
		super(fm);
		this.listBanner = listBanner;
	}

}
