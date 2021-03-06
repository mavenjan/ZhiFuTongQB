package com.nxt.net.zhifutongqb.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nxt.net.zhifutongqb.anim.ZoomTutorial;

import java.util.List;

/**
 * @author:Jack Tony
 * @tips :viewpager的适配器
 * @date :2014-11-12
 */
public class PreviewPictureViewPagerAdapter extends PagerAdapter {

	private List<String> sDrawables;
	private Context mContext;
	private ZoomTutorial mZoomTutorial;

	public PreviewPictureViewPagerAdapter(Context context , List<String> imgPatch, ZoomTutorial zoomTutorial) {
		this.sDrawables = imgPatch;
		this.mContext = context;
		this.mZoomTutorial = zoomTutorial;
	}

	@Override
	public int getCount() {
		return sDrawables.size();
	}

	@Override
	public View instantiateItem(ViewGroup container, final int position) {

		final ImageView imageView = new ImageView(mContext);
		Glide.with(mContext).load(sDrawables.get(position)).into(imageView);
		container.addView(imageView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				mZoomTutorial.closeZoomAnim(position);
			}
		});
		return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

}
