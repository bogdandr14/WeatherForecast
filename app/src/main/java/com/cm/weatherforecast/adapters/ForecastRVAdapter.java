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
        holder.forecastDayTV.setText(modal.getDateAndDescription());
        RequestCreator v = Picasso.get().load("https://" + modal.getIcon());
        v.into(holder.forecastWeatherIV);
        holder.forecastMinTempTV.setText(modal.getMinTemp());
        holder.forecastMaxTempTV.setText(modal.getMaxTemp());
        holder.forecastWindSpeedTV.setText(modal.getWindSpeed());
        holder.forecastWindDirectionTV.setText(modal.getWindDirection());
        holder.forecastHumidityTV.setText(modal.getHumidity());
        holder.forecastPressureTV.setText(modal.getPressure());
        holder.forecastRainfallChanceTV.setText(modal.getRainfallChance());
        holder.forecastRainfallQuantityTV.setText(modal.getRainfallQuantity());
        holder.forecastDawnTV.setText(modal.getDawnTime());
        holder.forecastDuskTV.setText(modal.getDuskTime());
        holder.forecastMoonPhaseTV.setText(modal.getMoonPhase());
        holder.forecastUVTV.setText(modal.getAirQuality());
    }

    @Override
    public int getItemCount() {
        return dayRVModalArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView forecastDayTV, forecastMinTempTV, forecastMaxTempTV,
                forecastWindSpeedTV, forecastWindDirectionTV, forecastHumidityTV,
                forecastPressureTV, forecastRainfallChanceTV, forecastRainfallQuantityTV,
                forecastDawnTV, forecastDuskTV, forecastMoonPhaseTV, forecastUVTV;
        private final ImageView forecastWeatherIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            forecastDayTV = itemView.findViewById(R.id.idForecastDayTV);
            forecastMinTempTV = itemView.findViewById(R.id.idForecastMinTempTV);
            forecastMaxTempTV = itemView.findViewById(R.id.idForecastMaxTempTV);
            forecastWindSpeedTV = itemView.findViewById(R.id.idForecastWindSpeedTV);
            forecastWindDirectionTV = itemView.findViewById(R.id.idForecastWindDirectionTV);
            forecastHumidityTV = itemView.findViewById(R.id.idForecastHumidityTV);
            forecastPressureTV = itemView.findViewById(R.id.idForecastPressureTV);
            forecastRainfallChanceTV = itemView.findViewById(R.id.idForecastRainfallChanceTV);
            forecastRainfallQuantityTV = itemView.findViewById(R.id.idForecastRainfallQuantityTV);
            forecastDawnTV = itemView.findViewById(R.id.idForecastDawnTV);
            forecastDuskTV = itemView.findViewById(R.id.idForecastDuskTV);
            forecastMoonPhaseTV = itemView.findViewById(R.id.idForecastMoonPhaseTV);
            forecastUVTV = itemView.findViewById(R.id.idUvTV);
            forecastWeatherIV = itemView.findViewById(R.id.idForecastWeatherIV);
        }
    }
}
