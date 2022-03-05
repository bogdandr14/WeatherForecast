package com.cm.weatherforecast.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cm.weatherforecast.R;
import com.cm.weatherforecast.activities.ForecastActivity;
import com.cm.weatherforecast.modals.LocationModal;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private Context context;
    private List<LocationModal> locationModalArrayList;

    public LocationAdapter(Context context, List<LocationModal> locationModalArrayList) {

        this.context = context;
        this.locationModalArrayList = locationModalArrayList;
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.location_card_item, parent, false);
        return new LocationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {
        LocationModal modal = locationModalArrayList.get(position);
        holder.tempretureTV.setText(modal.getTemperature() + "°C");
        holder.cityNameTV.setText(modal.getLocationName());
        holder.deleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "sadsadhjbn", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {

        return locationModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tempretureTV, cityNameTV;
        private ImageView deleteIV;
        private LinearLayout locationsLL;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tempretureTV = itemView.findViewById(R.id.idTemperatureTV);
            cityNameTV = itemView.findViewById(R.id.idLocationTV);
            deleteIV = itemView.findViewById(R.id.idDeleteLocationIV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            view.setBackgroundColor(0xf3342);
            Toast.makeText(context, "na ba coaie", Toast.LENGTH_SHORT).show();
        }
    }
}