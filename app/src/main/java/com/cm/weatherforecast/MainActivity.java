package com.cm.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
//        weatherRV = findViewById(R.id.idWeatherRV);
        hourlyWeatherListRVM = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            hourlyWeatherListRVM.add(new WeatherRVModal(null, null, null, null));
        }
//        weatherRVA = new WeatherRVAdapter(this, hourlyWeatherListRVM);
        materialB = findViewById(R.id.forecastMB);
//        weatherRV.setAdapter(weatherRVA);
        materialB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForecastActivity.class));
            }
        });
    }
}