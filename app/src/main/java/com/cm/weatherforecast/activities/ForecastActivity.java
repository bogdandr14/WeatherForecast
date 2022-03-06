package com.cm.weatherforecast.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cm.weatherforecast.R;
import com.cm.weatherforecast.adapters.DayRVAdapter;
import com.cm.weatherforecast.adapters.WeatherRVAdapter;
import com.cm.weatherforecast.modals.DayRVModal;
import com.cm.weatherforecast.modals.WeatherRVModal;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class ForecastActivity extends AppCompatActivity {

    RecyclerView dayRV;
    private DayRVAdapter dayRVA;
    private ArrayList<DayRVModal> sevenDayListRVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        dayRV = findViewById(R.id.id7DayForecastRV);

        setDummyDayWeather();
    }

    private void setDummyDayWeather(){
        sevenDayListRVM = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            sevenDayListRVM.add(new DayRVModal("2001-01-01 " + (i < 10 ? "0" : "") + i + ":00", "35", "cdn.weatherapi.com/weather/64x64/day/122.png"));
        }
        dayRVA = new DayRVAdapter(this, sevenDayListRVM);
        dayRV.setAdapter(dayRVA);
    }
}