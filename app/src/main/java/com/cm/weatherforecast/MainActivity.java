package com.cm.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private ProgressBar loadingPB;
    private RelativeLayout homeRL;
    private TextView cityNameTV, temperatureNowTV, conditionNowTV;
    private TextInputEditText citySearchTIET;
    private ImageView searchIV, weatherIV, backgroundIV;
    private RecyclerView weatherRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingPB = findViewById(R.id.idLoadingPB);
        homeRL = findViewById(R.id.idHomeRL);
        cityNameTV = findViewById(R.id.idCityNameTV);
        temperatureNowTV = findViewById(R.id.idTemperatureNowTV);
        conditionNowTV = findViewById(R.id.idConditionNowTV);
        citySearchTIET = findViewById(R.id.idCitySearch);
        searchIV = findViewById(R.id.idImgSearch);
        weatherIV = findViewById(R.id.idWeatherNowIcon);
        backgroundIV = findViewById(R.id.idBackgroundIV);
        weatherRV = findViewById(R.id.idWeatherRV);
    }
}