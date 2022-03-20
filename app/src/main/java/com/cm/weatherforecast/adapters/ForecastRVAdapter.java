package com.cm.weatherforecast.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cm.weatherforecast.R;
import com.cm.weatherforecast.modals.DayRVModal;

import java.util.ArrayList;

public class ForecastRVAdapter extends RecyclerView.Adapter<ForecastRVAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DayRVModal> dayRVModalArrayList;

    public ForecastRVAdapter(Context context, ArrayList<DayRVModal> dayRVModalArrayList) {
        this.context = context;
        this.dayRVModalArrayList = dayRVModalArrayList;
    }

    @NonNull
    @Override
    public ForecastRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item_forecast, parent, false);
        return new ForecastRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastRVAdapter.ViewHolder holder, int position) {
        DayRVModal modal = dayRVModalArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return dayRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
