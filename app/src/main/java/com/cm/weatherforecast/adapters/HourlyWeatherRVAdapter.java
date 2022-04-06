package com.cm.weatherforecast.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cm.weatherforecast.R;
import com.cm.weatherforecast.modals.HourlyWeatherRVModal;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HourlyWeatherRVAdapter extends RecyclerView.Adapter<HourlyWeatherRVAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<HourlyWeatherRVModal> hourlyWeatherRVModalArrayList;

    public HourlyWeatherRVAdapter(Context context, ArrayList<HourlyWeatherRVModal> hourlyWeatherRVModalArrayList) {

        this.context = context;
        this.hourlyWeatherRVModalArrayList = hourlyWeatherRVModalArrayList;
    }

    @NonNull
    @Override
    public HourlyWeatherRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_item_hourly_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyWeatherRVAdapter.ViewHolder holder, int position) {
        HourlyWeatherRVModal modal = hourlyWeatherRVModalArrayList.get(position);
        holder.temperatureTV.setText(modal.getTemperature());
        RequestCreator v = Picasso.get().load("https://" + modal.getIcon());
        v.into(holder.weatherIV);
        holder.windSpeedTV.setText(modal.getWindSpeed());
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat("hh:mm aa",  Locale.ENGLISH);
        try {
            Date t = input.parse(modal.getTime());
            assert t != null;
            holder.timeTV.setText(output.format(t));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {

        return hourlyWeatherRVModalArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView windSpeedTV, temperatureTV, timeTV;
        private final ImageView weatherIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTV = itemView.findViewById(R.id.idHourlyTimeTV);
            temperatureTV = itemView.findViewById(R.id.idHourlyTemperatureTV);
            weatherIV = itemView.findViewById(R.id.idHourlyWeatherIV);
            windSpeedTV = itemView.findViewById(R.id.idHourlyWindSpeedTV);

        }
    }
}
