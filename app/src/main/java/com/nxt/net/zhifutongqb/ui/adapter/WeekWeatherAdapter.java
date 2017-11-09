package com.nxt.net.zhifutongqb.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nxt.net.zhifutongqb.R;
import com.nxt.net.zhifutongqb.bean.WeatherDetail;
import com.nxt.net.zhifutongqb.utils.WeatherUtil;
import com.nxt.net.zhifutongqb.utils.TimeUtil;

import java.util.List;


/**
 * Created by 张永露 on 2015/8/17 0017.
 */
public class WeekWeatherAdapter extends BaseAdapter {
    private Context context;
    private int layoutid;
    private List<WeatherDetail.ResultsBean.WeatherDataBean> weatherlist;
    int[] imgids;
    Drawable drawable;
    int type;

    public WeekWeatherAdapter(Context context, List<WeatherDetail.ResultsBean.WeatherDataBean> weatherlist) {
        this.context = context;
        this.weatherlist = weatherlist;
    }

    @Override
    public int getCount() {
        return weatherlist.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_week_weather, null);
            holder.weatherview = (TextView) convertView.findViewById(R.id.tv_week_weather);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        WeatherDetail.ResultsBean.WeatherDataBean weather = weatherlist.get(position);
        drawable = context.getResources().getDrawable(WeatherUtil.getImageresource(weatherlist.get(position).getWeather()));
        drawable.setBounds(0, 0, 80, 80);
        holder.weatherview.setText(TimeUtil.getdatalist().get(position) + "\n\n" + weather.getWeather() + "\n\n" + weather.getTemperature());
        holder.weatherview.setCompoundDrawables(null, null, null, drawable);
        return convertView;
    }

    class Holder {
        TextView weatherview;
    }
}
