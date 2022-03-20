package com.cm.weatherforecast.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cm.weatherforecast.R;
import com.cm.weatherforecast.adapters.HourlyWeatherRVAdapter;
import com.cm.weatherforecast.modals.HourlyWeatherRVModal;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String CITY_NAME_MESSAGE = "CITY_NAME_MESSAGE";
    public static final int TEXT_REQUEST = 1;

    private ProgressBar loadingPB;
    private ImageView settingsIV, locationManagerIV;

    private TextView cityNameTV, temperatureNowTV, conditionNowTV;
    private TextView feelsLikeTV, pressureTV, windSpeedTV, windDirectionTV, humidityTV, cloudsTV, visibilityTV, uvTV;
    private ImageView weatherNowIV;
    private RecyclerView hourlyWeatherRV;
    private MaterialButton forecastMB;
    private ArrayList<HourlyWeatherRVModal> hourlyWeatherListRVM;
    private HourlyWeatherRVAdapter weatherRVA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingPB = findViewById(R.id.idLoadingPB);

        //Images for redirecting to other activities
        locationManagerIV = findViewById(R.id.idLocationManagerIV);
        settingsIV = findViewById(R.id.idSettingsIV);

        //Weather now for current selected location
        cityNameTV = findViewById(R.id.idCityNameTV);
        temperatureNowTV = findViewById(R.id.idTemperatureNowTV);
        weatherNowIV = findViewById(R.id.idWeatherNowIV);
        conditionNowTV = findViewById(R.id.idConditionNowTV);

        //Today details
        feelsLikeTV = findViewById(R.id.idFeelsLikeTV);
        pressureTV = findViewById(R.id.idPressureTV);
        windSpeedTV = findViewById(R.id.idWindSpeedTV);
        windDirectionTV = findViewById(R.id.idWindDirectionTV);
        humidityTV = findViewById(R.id.idHumidityTV);
        cloudsTV = findViewById(R.id.idCloudsTV);
        visibilityTV = findViewById(R.id.idVisibilityTV);
        uvTV = findViewById(R.id.idUvTV);

        //Hourly weather and forecast
        hourlyWeatherRV = findViewById(R.id.idHourlyWeatherRV);
        forecastMB = findViewById(R.id.idForecastMB);

        setDummyHourlyWeather();
        setListeners();
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                String cityName = data.getStringExtra(CITY_NAME_MESSAGE);
                cityNameTV.setText(cityName);
                //TODO use api to get info for the current CITY
            }
            else{
                //TODO use api to get info for the current LOCATION
            }
        }
    }

    private void getWeatherInfo(String cityName) {
        String url = "http://api.weatherapi.com/v1/forecast.json?key=244e95839e83453bb05122307222002&q=" + cityName +"&days=7&aqi=yes&alerts=yes";
    }

    private void setDummyHourlyWeather(){
        hourlyWeatherListRVM = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            hourlyWeatherListRVM.add(new HourlyWeatherRVModal("2001-01-01 " + (i < 10 ? "0" : "") + i + ":00", "35", "cdn.weatherapi.com/weather/64x64/day/122.png", String.valueOf(i)));
        }
        weatherRVA = new HourlyWeatherRVAdapter(this, hourlyWeatherListRVM);
        hourlyWeatherRV.setAdapter(weatherRVA);
    }

    private void setListeners(){
        forecastMB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = cityNameTV.getText().toString();
                Intent intent = new Intent(getApplicationContext(), ForecastActivity.class);
                intent.putExtra(CITY_NAME_MESSAGE, message);
                startActivity(intent);
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
                startActivityForResult(new Intent(getApplicationContext(),LocationManagerActivity.class), TEXT_REQUEST);
            }
        });
    }
}