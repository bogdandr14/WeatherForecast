package com.cm.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public abstract class BaseWeatherActivity extends AppCompatActivity {

    abstract public void setWeatherInfo(JSONObject weatherInfo);
}
