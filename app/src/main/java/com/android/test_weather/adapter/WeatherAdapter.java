package com.android.test_weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.test_weather.R;
import com.android.test_weather.Weather;

import java.util.List;

public class WeatherAdapter extends ArrayAdapter<Weather> {
    private int resourceId;

    public WeatherAdapter(Context context, int textViewResourceId,
                          List<Weather> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewgroup) {
        Weather weather = getItem(position);
        ViewHolder Holder = null;
        if (convertView == null) {
            Holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
            Holder.tvDayOfWeek = (TextView)
                    convertView.findViewById(R.id.tvDayofWeek);
            Holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            Holder.tvTemperature = (TextView) convertView.findViewById(R.id.tvTemperature);
            Holder.tvWeather = (TextView) convertView.findViewById(R.id.tvWeather);
            Holder.tvWind=(TextView)convertView.findViewById(R.id.tvWind);
            convertView.setTag(Holder);
        } else {
            Holder = (ViewHolder) convertView.getTag();
        }
        Holder.tvDayOfWeek.setText(weather.getDayOfWeek());
        Holder.tvDate.setText(weather.getDate());
        Holder.tvTemperature.setText(weather.getTemperature());
        Holder.tvWeather.setText(weather.getWeather());
        Holder.tvWind.setText(weather.getWind());
        return convertView;
    }

    private class ViewHolder {
        TextView tvDayOfWeek;
        TextView tvDate;
        TextView tvTemperature;
        TextView tvWeather;
        TextView tvWind;
    }

}