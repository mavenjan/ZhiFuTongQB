package com.nxt.net.zhifutongqb.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nxt.net.zhifutongqb.R;
import com.nxt.net.zhifutongqb.ui.activity.PoorDetailActivity;
import com.nxt.net.zhifutongqb.anim.ZoomTutorial;

public class RecycleViewAdapter extends BaseAdapter {

	private Context mContext;

	// 要展示的小图片的url数组
	private String[] mSmallImagePatchs;

	public RecycleViewAdapter(Context c, String[] smallImagePatchs) {
		mContext = c;
		mSmallImagePatchs = smallImagePatchs;
	}

	/**
	 * 要显示的图片的数目，mSmallImagePatchs是小图片的url地址
	 */
	public int getCount() {
		return mSmallImagePatchs.length;
	}

	public Object getItem(int position) {
		return mSmallImagePatchs[position];
	}

	public long getItemId(int position) {
		return position;
	}

	/**
	 * 创建每个item的视图
	 */
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(mContext);
			// 这里的scaleType是CENTER_CROP
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		} else {
			imageView = (ImageView) convertView;
		}

		Glide.with(mContext).load(mSmallImagePatchs[position]).into(imageView);
//		imageView.setTag(mSmallImagePatchs[position]);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//当前drawable的res的id
				setViewPagerAndZoom(imageView, position);
			}
		});

		return imageView;
	}
	
	public void setViewPagerAndZoom(View v , int position) {
		//得到要放大展示的视图界面
		ViewPager expandedView = (ViewPager)((Activity)mContext).findViewById(R.id.detail_view);
		//最外层的容器，用来计算
		View containerView = (CoordinatorLayout)((Activity)mContext).findViewById(R.id.cdt_help_daily);
		//实现放大缩小类，传入当前的容器和要放大展示的对象
		ZoomTutorial mZoomTutorial = new ZoomTutorial(containerView, expandedView);
		
		PreviewPictureViewPagerAdapter adapter = new PreviewPictureViewPagerAdapter(mContext,
				PoorDetailActivity.housePhotoUrlsBig,mZoomTutorial);
		expandedView.setAdapter(adapter);
		expandedView.setCurrentItem(position);
		
		// 通过传入Id来从小图片扩展到大图，开始执行动画
		mZoomTutorial.zoomImageFromThumb(v);
		mZoomTutorial.setOnZoomListener(new ZoomTutorial.OnZoomListener() {
			
			@Override
			public void onThumbed() {
				// TODO 自动生成的方法存根
				AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f,1.0f);
				alphaAnimation.setDuration(300);
				System.out.println("现在是-------------------> 小图状态");
			}
			
			@Override
			public void onExpanded() {
				// TODO 自动生成的方法存根
				AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);
				alphaAnimation.setDuration(300);
				System.out.println("现在是-------------------> 大图状态");
			}
		});
	}
}
