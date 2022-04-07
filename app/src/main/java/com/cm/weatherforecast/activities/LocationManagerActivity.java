
package com.cm.weatherforecast.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cm.weatherforecast.Constants;
import com.cm.weatherforecast.PreferenceManager;
import com.cm.weatherforecast.R;
import com.cm.weatherforecast.WeatherChecker;
import com.cm.weatherforecast.adapters.LocationAdapter;
import com.cm.weatherforecast.modals.LocationModal;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LocationManagerActivity extends AppCompatActivity {
    private PreferenceManager preferenceManager;
    private WeatherChecker weatherChecker;

    TextInputEditText citySearchTIET;
    ImageView cityAddIV;
    RecyclerView interestLocationsRV;

    private List<LocationModal> locationsList;
    private LocationAdapter locationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_manager);
        preferenceManager = new PreferenceManager(getApplicationContext());

        citySearchTIET = findViewById(R.id.idCitySearchTIET);
        cityAddIV = findViewById(R.id.idCityAddIV);
        interestLocationsRV = findViewById(R.id.idInterestLocationsRV);
        setStoredLocations();
        setListener();
    }


    private void setStoredLocations() {
        locationsList = new ArrayList<>();
        String interestLocations = preferenceManager.getString(Constants.KEY_INTEREST_LOCATIONS);
        if (interestLocations != null) {
            String[] locations = interestLocations.split(";");
            for (String location : locations
            ) {
                locationsList.add(new LocationModal(location, "35"));
            }
        }
        locationAdapter = new LocationAdapter(this, locationsList, preferenceManager);
        interestLocationsRV.setAdapter(locationAdapter);
    }

    private void setListener() {
        cityAddIV.setOnClickListener(view -> {
            //TODO ADD CITY TO LIST
            locationAdapter.addLocation(Objects.requireNonNull(citySearchTIET.getText()).toString());
        });

    }
}