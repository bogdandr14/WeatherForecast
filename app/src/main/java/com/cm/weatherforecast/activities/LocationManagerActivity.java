
package com.cm.weatherforecast.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cm.weatherforecast.R;
import com.cm.weatherforecast.adapters.LocationAdapter;
import com.cm.weatherforecast.modals.LocationModal;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LocationManagerActivity extends AppCompatActivity {

    TextInputEditText citySearchTIET;
    ImageView cityAddIV;
    RecyclerView interestLocationsRV;

    private List<LocationModal> locationsList;
    private LocationAdapter locationAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_manager);
        citySearchTIET = findViewById(R.id.idCitySearchTIET);
        cityAddIV = findViewById(R.id.idCityAddIV);
        interestLocationsRV = findViewById(R.id.idInterestLocationsRV);
        setDummyForLocations();
        setListener();
    }

    private void setDummyForLocations(){
        locationsList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            locationsList.add(new LocationModal("location " + i , "35", "1.232", "32.42"));
        }
        locationAdapter = new LocationAdapter(this, locationsList);
        interestLocationsRV.setAdapter(locationAdapter);
    }

    private void setListener(){
        cityAddIV.setOnClickListener(view -> {
            //TODO ADD CITY TO LIST
            locationsList = locationAdapter.getLocationList();
            locationsList.add(new LocationModal(Objects.requireNonNull(citySearchTIET.getText()).toString(), "35", "1.232", "32.42"));
            locationAdapter = new LocationAdapter(getApplicationContext(), locationsList);
            interestLocationsRV.setAdapter(locationAdapter);
            Toast.makeText(getApplicationContext(), "add location", Toast.LENGTH_SHORT).show();
        });

    }
}