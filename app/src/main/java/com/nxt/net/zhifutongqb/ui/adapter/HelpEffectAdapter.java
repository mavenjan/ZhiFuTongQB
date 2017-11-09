package com.nxt.net.zhifutongqb.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nxt.net.zhifutongqb.R;
import com.nxt.net.zhifutongqb.bean.GalleryModel;

import java.util.List;

public class HelpEffectAdapter extends RecyclerView.Adapter<HelpEffectAdapter.MyViewHolder>
{
	private static final String TAG = "HelpEffectAdapter";

	private List<GalleryModel> mDatas;
	private LayoutInflater mInflater;
	private Context mContext;

	public interface OnItemClickLitener
	{
		void onItemClick(View view, int position);
		void onItemLongClick(View view, int position);
	}

	private OnItemClickLitener mOnItemClickLitener;

	public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
	{
		this.mOnItemClickLitener = mOnItemClickLitener;
	}


	public HelpEffectAdapter(Context context, List<GalleryModel> datas)
	{
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		mDatas = datas;
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		MyViewHolder holder = new MyViewHolder(mInflater.inflate(
				R.layout.item_out_poor, parent, false));
		return holder;
	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, final int position)
	{
		Glide.with(mContext).load(mDatas.get(position).getImgurl())
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.skipMemoryCache(false)
				.into(holder.iv);
		Log.e(TAG, "onBindViewHolder: url--------->" + mDatas.get(position).getImgurl());
		holder.tvName.setText(mDatas.get(position).getTitle());
		holder.tvAddress.setText(mDatas.get(position).getAddress());

		// 如果设置了回调，则设置点击事件
		if (mOnItemClickLitener != null)
		{
			holder.itemView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					int pos = holder.getLayoutPosition();
					mOnItemClickLitener.onItemClick(holder.itemView, pos);
				}
			});

			holder.itemView.setOnLongClickListener(new OnLongClickListener()
			{
				@Override
				public boolean onLongClick(View v)
				{
					int pos = holder.getLayoutPosition();
					mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
					removeData(pos);
					return false;
				}
			});
		}
	}

	@Override
	public int getItemCount()
	{
		return mDatas.size();
	}

	public void addData(int position)
	{
//		mDatas.add(position, "Insert One");
		notifyItemInserted(position);
	}


	public void removeData(int position)
	{
		mDatas.remove(position);
		notifyItemRemoved(position);
	}

	class MyViewHolder extends ViewHolder
	{
		ImageView iv;
		TextView tvName;
		TextView tvAddress;

		public MyViewHolder(View view)
		{
			super(view);
			iv = (ImageView) view.findViewById(R.id.id_index_gallery_item_image);
			tvName = (TextView) view.findViewById(R.id.id_index_gallery_item_text);
			tvAddress = (TextView) view.findViewById(R.id.item_address);
		}
	}
}