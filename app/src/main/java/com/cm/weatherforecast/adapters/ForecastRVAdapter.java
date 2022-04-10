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
import com.cm.weatherforecast.modals.DayRVModal;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;

public class ForecastRVAdapter extends RecyclerView.Adapter<ForecastRVAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<DayRVModal> dayRVModalArrayList;

    public ForecastRVAdapter(Context context, ArrayList<DayRVModal> dayRVModalArrayList) {
        this.context = context;
        this.dayRVModalArrayList = dayRVModalArrayList;
    }

    @NonNull
    @Override
    public ForecastRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item_forecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastRVAdapter.ViewHolder holder, int position) {
        DayRVModal modal = dayRVModalArrayList.get(position);
        holder.forecastDayTV.setText(modal.getDate().concat(",\n"+modal.getDescription()));
        RequestCreator v = Picasso.get().load("https://" + modal.getIcon());
        v.into(holder.forecastWeatherIV);
        holder.forecastMinTempTV.setText(modal.getMinTemp());
        holder.forecastMaxTempTV.setText(modal.getMaxTemp());
        holder.forecastWindSpeedTV.setText(modal.getWindSpeed());
        holder.forecastHumidityTV.setText(modal.getHumidity());
        holder.forecastRainfallChanceTV.setText(modal.getRainfallChance());
        holder.forecastRainfallQuantityTV.setText(modal.getRainfallQuantity());
        holder.forecastDawnTV.setText(modal.getDawnTime());
        holder.forecastDuskTV.setText(modal.getDuskTime());
        holder.forecastVisibilityTV.setText(modal.getVisibility());
        holder.forecastMoonPhaseTV.setText(modal.getMoonPhase());
    }

    @Override
    public int getItemCount() {
        return dayRVModalArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView forecastDayTV, forecastMinTempTV, forecastMaxTempTV,
                forecastWindSpeedTV, forecastHumidityTV,
                 forecastRainfallChanceTV, forecastRainfallQuantityTV,
                forecastDawnTV, forecastDuskTV,forecastVisibilityTV, forecastMoonPhaseTV;
        private final ImageView forecastWeatherIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            forecastDayTV = itemView.findViewById(R.id.idForecastDayTV);
            forecastMinTempTV = itemView.findViewById(R.id.idForecastMinTempTV);
            forecastMaxTempTV = itemView.findViewById(R.id.idForecastMaxTempTV);
            forecastWindSpeedTV = itemView.findViewById(R.id.idForecastWindSpeedTV);
            forecastHumidityTV = itemView.findViewById(R.id.idForecastHumidityTV);
            forecastRainfallChanceTV = itemView.findViewById(R.id.idForecastRainfallChanceTV);
            forecastRainfallQuantityTV = itemView.findViewById(R.id.idForecastRainfallQuantityTV);
            forecastDawnTV = itemView.findViewById(R.id.idForecastDawnTV);
            forecastDuskTV = itemView.findViewById(R.id.idForecastDuskTV);
            forecastVisibilityTV = itemView.findViewById(R.id.idForecastVisibilityTV);
            forecastMoonPhaseTV = itemView.findViewById(R.id.idForecastMoonPhaseTV);
            forecastWeatherIV = itemView.findViewById(R.id.idForecastWeatherIV);
        }
    }
}
