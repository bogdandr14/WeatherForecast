package com.cm.weatherforecast.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cm.weatherforecast.R;
import com.cm.weatherforecast.adapters.WeatherRVAdapter;
import com.cm.weatherforecast.modals.WeatherRVModal;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ProgressBar loadingPB;
    private RelativeLayout homeRL;
    private TextView cityNameTV, temperatureNowTV, conditionNowTV;
    private TextInputEditText citySearchTIET;
    private ImageView searchIV, weatherIV, backgroundIV;
    private RecyclerView weatherRV;
    private MaterialButton materialB;
    private ArrayList<WeatherRVModal> hourlyWeatherListRVM;
    private WeatherRVAdapter weatherRVA;
    private ImageView settingsIV, locationManagerIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        loadingPB = findViewById(R.id.idLoadingPB);
//        homeRL = findViewById(R.id.idHomeRL);
//        cityNameTV = findViewById(R.id.idCityNameTV);
//        temperatureNowTV = findViewById(R.id.idTemperatureNowTV);
//        conditionNowTV = findViewById(R.id.idConditionNowTV);
//        citySearchTIET = findViewById(R.id.idCitySearch);
//        searchIV = findViewById(R.id.idImgSearch);
//        weatherIV = findViewById(R.id.idWeatherNowIcon);
//        backgroundIV = findViewById(R.id.idBackgroundIV);
        weatherRV = findViewById(R.id.idWeatherRV);
        settingsIV = findViewById(R.id.idSettings);
        locationManagerIV = findViewById(R.id.idLocationManager);
        materialB = findViewById(R.id.forecastMB);

        setDummyHourlyWeather();
        setListeners();

    }

    private void setDummyHourlyWeather(){
        hourlyWeatherListRVM = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            hourlyWeatherListRVM.add(new WeatherRVModal("2001-01-01 " + (i < 10 ? "0" : "") + i + ":00", "35", "cdn.weatherapi.com/weather/64x64/day/122.png", String.valueOf(i)));
        }
        weatherRVA = new WeatherRVAdapter(this, hourlyWeatherListRVM);
        weatherRV.setAdapter(weatherRVA);
    }

    private void setListeners(){
        materialB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForecastActivity.class));
            }
        });

        settingsIV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
            }
        });

        locationManagerIV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(),LocationManagerActivity.class));
            }
        });
    }
}