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

public class HourlyWeatherRVAdapter extends RecyclerView.Adapter<HourlyWeatherRVAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HourlyWeatherRVModal> hourlyWeatherRVModalArrayList;

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
        holder.tempretureTV.setText(modal.getTemperature()+"°C");
        RequestCreator v = Picasso.get().load("http://"+modal.getIcon());
        v.into(holder.weatherIV);
        holder.windSpeedTV.setText(modal.getWindSpeed()+"Km/h");
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        SimpleDateFormat output = new SimpleDateFormat("hh:mm aa");
    try{
        Date t = input.parse(modal.getTime());
        holder.timeTV.setText(output.format(t));
        }
    catch(ParseException e){
        e.printStackTrace();
    }
    }

    @Override
    public int getItemCount() {

        return hourlyWeatherRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView windSpeedTV, tempretureTV, timeTV;
        private ImageView weatherIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTV = itemView.findViewById(R.id.idHourlyTimeTV);
            tempretureTV = itemView.findViewById(R.id.idHourlyTemperatureTV);
            weatherIV = itemView.findViewById(R.id.idHourlyWeatherIV);
            windSpeedTV = itemView.findViewById(R.id.idHourlyWindSpeedTV);

        }
    }
}
