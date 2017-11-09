package com.nxt.net.zhifutongqb.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nxt.net.zhifutongqb.R;
import com.nxt.net.zhifutongqb.bean.DailyBean;

import java.util.List;


/**
 * @author koneloong koneloong@gmail.com
 *         Created on 2015/7/29 14:50.
 */
public class DailyAdapter extends ZBaseAdapter<DailyBean.RowsBean> {
    private static final String TAG = "DailyAdapter";


    public DailyAdapter(Context context, List<DailyBean.RowsBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_sign_daily, parent, false);
            holder = new Holder();
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_content);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        DailyBean.RowsBean article = dataList.get(position);

        if (!TextUtils.isEmpty(article.getJournal()))
            holder.tvTitle.setText(article.getJournal());
        if (!TextUtils.isEmpty(article.getCreatedate().trim())) {
            String str = article.getCreatedate().trim();
            String spStr[] = str.split("T");
            String time = spStr[1].trim();
            String date = spStr[0].trim();
            Log.e(TAG, "getView: time---------->" + time);
            Log.e(TAG, "getView: date---------->" + date);
            String spStr1[] = time.split("\\.");
            String date1 = spStr1[0].trim();
            Log.e(TAG, "getView: time1---------->" + date1);

            String signTime = date + "\t" + date1;

            holder.tvTime.setText(signTime);
        }
        return convertView;
    }

    class Holder {
        TextView tvTitle, tvTime;
    }
}
