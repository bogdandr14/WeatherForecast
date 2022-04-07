package com.cm.weatherforecast.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cm.weatherforecast.Constants;
import com.cm.weatherforecast.PreferenceManager;
import com.cm.weatherforecast.activities.MainActivity;
import com.cm.weatherforecast.databinding.CardItemLocationBinding;
import com.cm.weatherforecast.modals.LocationModal;

import java.util.List;
import java.util.Objects;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private final PreferenceManager preferenceManager;
    private final Context context;
    private final List<LocationModal> locationModalArrayList;

    public LocationAdapter(Context context, List<LocationModal> locationModalArrayList, PreferenceManager preferenceManager) {

        this.context = context;
        this.locationModalArrayList = locationModalArrayList;
        this.preferenceManager = preferenceManager;
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
            removeLocation(modal.getLocationName());
            Toast.makeText(context, "delete location: " + modal.getLocationName(), Toast.LENGTH_SHORT).show();
            locationModalArrayList.remove(modal);
            notifyDataSetChanged();
        });
    }

    private void removeLocation(String locationName) {
        String interestLocations = preferenceManager.getString(Constants.KEY_INTEREST_LOCATIONS);
        interestLocations = interestLocations.replace(locationName + ";", "");
        preferenceManager.putString(Constants.KEY_INTEREST_LOCATIONS, interestLocations);
    }

    public void addLocation(String locationName) {
        Toast.makeText(context, "add location: " + locationName, Toast.LENGTH_SHORT).show();

        locationModalArrayList.add(new LocationModal(Objects.requireNonNull(locationName).toString(), "35"));
        notifyDataSetChanged();
        String interestLocations = preferenceManager.getString(Constants.KEY_INTEREST_LOCATIONS);
        if (interestLocations == null) {
            interestLocations = "";
        }
        interestLocations = interestLocations.concat(locationName + ';');
        preferenceManager.putString(Constants.KEY_INTEREST_LOCATIONS, interestLocations);
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
            Toast.makeText(context, "new location: " + reply, Toast.LENGTH_SHORT).show();

            Intent replyIntent = new Intent(context, MainActivity.class);
            replyIntent.putExtra(Constants.CITY_NAME_MESSAGE, reply);
            context.startActivity(replyIntent);
        }
    }
}