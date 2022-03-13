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
    public static final String CITY_NAME_MESSAGE = "CITY_NAME_MESSAGE";
    public static final int TEXT_REQUEST = 1;


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

        loadingPB = findViewById(R.id.idLoadingPB);
//        homeRL = findViewById(R.id.idHomeRL);
        cityNameTV = findViewById(R.id.idCityNameTV);
        temperatureNowTV = findViewById(R.id.idTemperatureNowTV);
        conditionNowTV = findViewById(R.id.idConditionNowTV);
        citySearchTIET = findViewById(R.id.idCitySearch);
        searchIV = findViewById(R.id.idImgSearch);
        weatherIV = findViewById(R.id.idWeatherNowIcon);
        weatherRV = findViewById(R.id.idWeatherRV);
        settingsIV = findViewById(R.id.idSettings);
        locationManagerIV = findViewById(R.id.idLocationManager);
        materialB = findViewById(R.id.forecastMB);

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
            hourlyWeatherListRVM.add(new WeatherRVModal("2001-01-01 " + (i < 10 ? "0" : "") + i + ":00", "35", "cdn.weatherapi.com/weather/64x64/day/122.png", String.valueOf(i)));
        }
        weatherRVA = new WeatherRVAdapter(this, hourlyWeatherListRVM);
        weatherRV.setAdapter(weatherRVA);
    }

    private void setListeners(){
        materialB.setOnClickListener(new View.OnClickListener() {
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