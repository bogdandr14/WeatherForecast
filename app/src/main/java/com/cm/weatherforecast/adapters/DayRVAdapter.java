package com.cm.weatherforecast.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cm.weatherforecast.R;
import com.cm.weatherforecast.modals.DayRVModal;

import java.util.ArrayList;

public class DayRVAdapter extends RecyclerView.Adapter<DayRVAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DayRVModal> dayRVModalArrayList;

    public DayRVAdapter(Context context, ArrayList<DayRVModal> dayRVModalArrayList) {
        this.context = context;
        this.dayRVModalArrayList = dayRVModalArrayList;
    }

    @NonNull
    @Override
    public DayRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.forecast_card_item, parent, false);
        return new DayRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayRVAdapter.ViewHolder holder, int position) {
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
