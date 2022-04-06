package com.cm.weatherforecast.adapters;

import static com.cm.weatherforecast.activities.MainActivity.CITY_NAME_MESSAGE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cm.weatherforecast.activities.MainActivity;
import com.cm.weatherforecast.databinding.CardItemLocationBinding;
import com.cm.weatherforecast.modals.LocationModal;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private final Context context;
    private final List<LocationModal> locationModalArrayList;

    public LocationAdapter(Context context, List<LocationModal> locationModalArrayList) {

        this.context = context;
        this.locationModalArrayList = locationModalArrayList;
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardItemLocationBinding binding = CardItemLocationBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        return new LocationAdapter.ViewHolder(binding);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {
        LocationModal modal = locationModalArrayList.get(position);
        holder.binding.idTemperatureTV.setText(modal.getTemperature().concat("°C"));
        holder.binding.idLocationTV.setText(modal.getLocationName());
        holder.binding.idDeleteLocationIV.setOnClickListener(view -> {
            locationModalArrayList.remove(modal);
            notifyDataSetChanged();
            Toast.makeText(context, "delete location", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return locationModalArrayList.size();
    }

    public List<LocationModal> getLocationList() {
        return this.locationModalArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final CardItemLocationBinding binding;

        public ViewHolder(CardItemLocationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            view.setBackgroundColor(0xf3342);
            String reply = binding.idLocationTV.getText().toString();
            Intent replyIntent = new Intent(context, MainActivity.class);
            replyIntent.putExtra(CITY_NAME_MESSAGE, reply);
            context.startActivity(replyIntent);
        }
    }
}