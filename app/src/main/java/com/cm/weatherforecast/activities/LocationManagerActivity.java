
package com.cm.weatherforecast.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cm.weatherforecast.R;
import com.cm.weatherforecast.adapters.LocationAdapter;
import com.cm.weatherforecast.adapters.WeatherRVAdapter;
import com.cm.weatherforecast.modals.LocationModal;
import com.cm.weatherforecast.modals.WeatherRVModal;

import java.util.ArrayList;
import java.util.List;

public class LocationManagerActivity extends AppCompatActivity {

    RecyclerView locationsRV;
    private List<LocationModal> locationsList;
    private LocationAdapter locationAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_manager);
        locationsRV = findViewById(R.id.idLocationsRV);
        setDummyForLocations();
    }

    private void setDummyForLocations(){
        locationsList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            locationsList.add(new LocationModal("location " + i , "35", "1.232", "32.42"));
        }
        locationAdapter = new LocationAdapter(this, locationsList);
        locationsRV.setAdapter(locationAdapter);
    }
}