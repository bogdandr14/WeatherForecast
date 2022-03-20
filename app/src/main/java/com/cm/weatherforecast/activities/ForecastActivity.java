package com.cm.weatherforecast.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.cm.weatherforecast.R;
import com.cm.weatherforecast.adapters.ForecastRVAdapter;
import com.cm.weatherforecast.modals.DayRVModal;

import java.util.ArrayList;

public class ForecastActivity extends AppCompatActivity {

    RecyclerView sevenDayForecastRV;
    private ForecastRVAdapter forecastRVA;
    private ArrayList<DayRVModal> forecastDaysListRVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        sevenDayForecastRV = findViewById(R.id.id7DayForecastRV);
        Intent intent = getIntent();
        String cityName = intent.getStringExtra(MainActivity.CITY_NAME_MESSAGE);

        //use cityName to make api call for 7-day forecast
        setDummyDayWeather();
    }

    private void setDummyDayWeather(){
        forecastDaysListRVM = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            forecastDaysListRVM.add(new DayRVModal("2001-01-01 " + (i < 10 ? "0" : "") + i + ":00", "35", "cdn.weatherapi.com/weather/64x64/day/122.png"));
        }
        forecastRVA = new ForecastRVAdapter(this, forecastDaysListRVM);
        sevenDayForecastRV.setAdapter(forecastRVA);
    }
}