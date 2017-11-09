package com.nxt.net.zhifutongqb.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nxt.net.zhifutongqb.R;
import com.nxt.net.zhifutongqb.anim.ZoomTutorial;
import com.nxt.net.zhifutongqb.utils.DensityUtil;

import java.util.List;

/**
 * @author Jan Maven
 */
public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private ImageClickListener mImageClickListener;

    public interface ImageClickListener {
        public void isBigImage(String string);
    }

    public void setImageClickListener(ImageClickListener imageClickListener) {
        this.mImageClickListener = imageClickListener;
    }


    /** 要展示的小图片的url数组 */
    private List<String> mSmallImagePatchs;

    public GridViewAdapter(Context c, List<String> smallImagePatch) {
        mContext = c;
        mSmallImagePatchs = smallImagePatch;
    }

    /**
     * 要显示的图片的数目，mSmallImagePatchs是小图片的url地址
     */
    @Override
    public int getCount() {
        return mSmallImagePatchs.size();
    }

    @Override
    public Object getItem(int position) {
        return mSmallImagePatchs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 创建每个item的视图
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final SimpleDraweeView imageView;
        if (convertView == null) {
            imageView = new SimpleDraweeView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(DensityUtil.dip2px(mContext, (float) 80.00),
                    DensityUtil.dip2px(mContext, (float) 80.00)));

            // 这里的scaleType是CENTER_CROP
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            imageView = (SimpleDraweeView) convertView;
        }

        imageView.setImageURI(mSmallImagePatchs.get(position));

        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //当前drawable的res的id
                setViewPagerAndZoom(imageView, position);
            }
        });

        return imageView;
    }

    public void setViewPagerAndZoom(View v, int position) {
        //得到要放大展示的视图界面
        ViewPager expandedView = (ViewPager) ((Activity) mContext).findViewById(R.id.detail_view);
        //最外层的容器，用来计算
        View containerView = ((Activity) mContext).findViewById(R.id.cdt_help_daily);
        //实现放大缩小类，传入当前的容器和要放大展示的对象

        ZoomTutorial mZoomTutorial = new ZoomTutorial(containerView, expandedView);

//        PreviewPictureViewPagerAdapter adapter = new PreviewPictureViewPagerAdapter(mContext,
//                HelpDailyActivity.photoUrls, mZoomTutorial);
        PreviewPictureViewPagerAdapter adapter = new PreviewPictureViewPagerAdapter(mContext,
                mSmallImagePatchs, mZoomTutorial);
        expandedView.setAdapter(adapter);
        expandedView.setCurrentItem(position);

        // 通过传入Id来从小图片扩展到大图，开始执行动画
        mZoomTutorial.zoomImageFromThumb(v);
        mZoomTutorial.setOnZoomListener(new ZoomTutorial.OnZoomListener() {

            @Override
            public void onThumbed() {
                // TODO 自动生成的方法存根
                System.out.println("现在是-------------------> 小图状态");
//                mImageClickListener.isBigImage("小图");
            }

            @Override
            public void onExpanded() {
                // TODO 自动生成的方法存根
                System.out.println("现在是-------------------> 大图状态");
//                mImageClickListener.isBigImage("大图");
            }
        });
    }
}
