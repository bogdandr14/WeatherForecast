
package com.cm.weatherforecast.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        cityAddIV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //TODO ADD CITY TO LIST
                Toast.makeText(getApplicationContext(), "add location", Toast.LENGTH_SHORT).show();
            }
        });

    }
}