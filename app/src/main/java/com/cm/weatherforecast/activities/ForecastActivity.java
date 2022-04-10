package com.cm.weatherforecast.activities;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.cm.weatherforecast.BaseWeatherActivity;
import com.cm.weatherforecast.Constants;
import com.cm.weatherforecast.PreferenceManager;
import com.cm.weatherforecast.R;
import com.cm.weatherforecast.WeatherChecker;
import com.cm.weatherforecast.adapters.ForecastRVAdapter;
import com.cm.weatherforecast.modals.DayRVModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class ForecastActivity extends BaseWeatherActivity {
    private PreferenceManager preferenceManager;
    private WeatherChecker weatherChecker;

    RecyclerView sevenDayForecastRV;
    private ForecastRVAdapter forecastRVA;
    private ArrayList<DayRVModal> forecastDaysListRVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        preferenceManager = new PreferenceManager(getApplicationContext());

        sevenDayForecastRV = findViewById(R.id.id7DayForecastRV);
        Intent intent = getIntent();

        forecastDaysListRVM = new ArrayList<>();
        forecastRVA = new ForecastRVAdapter(this, forecastDaysListRVM);
        sevenDayForecastRV.setAdapter(forecastRVA);

        String cityName = intent.getStringExtra(Constants.CITY_NAME_MESSAGE);

        Toast.makeText(this, cityName, Toast.LENGTH_SHORT).show();
        weatherChecker = new WeatherChecker(this);
        weatherChecker.execute(cityName, Constants.WEATHER_API_LINK_FORECAST);
    }

    @Override
    public void setWeatherInfo(JSONObject weatherInfo) {
        try {
            setForecastWeather(weatherInfo.getJSONObject(Constants.FORECAST).getJSONArray(Constants.FORECASTDAY));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setForecastWeather(JSONArray forecastDaysWeatherInfo) {
        forecastDaysListRVM.clear();
        String tempMeasure = preferenceManager.getString(Constants.KEY_TEMP_MEASURE);
        String tempSymbol = tempMeasure.equals(Constants.TEMP_C) ? "°C" : "°F";
        String windMeasure = preferenceManager.getString(Constants.KEY_WIND_MEASURE);
        String windUnit = windMeasure.equals(Constants.WIND_KPH) ? "km/h" : "mph";
        String rainfallMeasure = preferenceManager.getString(Constants.KEY_RAINFALL_MEASURE);
        String rainfallUnit = rainfallMeasure.equals(Constants.PRECIP_MM) ? "mm" : "in";
        String visibilityMeasure = preferenceManager.getString(Constants.KEY_VISIBILITY_MEASURE);
        String visibilityUnit = visibilityMeasure.equals(Constants.VIS_KM) ? "km" : "miles";

        try {
            for (int i = 0; i < 7; ++i) {
                DayRVModal dayRVModal = new DayRVModal();
                JSONObject forecastDayObj = forecastDaysWeatherInfo.getJSONObject(i);
                JSONObject dayInfoObj = forecastDayObj.getJSONObject(Constants.DAY);
                JSONObject conditionObj = dayInfoObj.getJSONObject(Constants.CONDITION);
                JSONObject astroObj = forecastDayObj.getJSONObject(Constants.ASTRO);

                dayRVModal.setDate(forecastDayObj.getString(Constants.DATE));

                dayRVModal.setIcon(conditionObj.getString(Constants.ICON));

                dayRVModal.setDescription(conditionObj.getString(Constants.TEXT));

                Double minTemp = dayInfoObj.getDouble(tempMeasure.equals(Constants.TEMP_C) ? Constants.MIN_TEMP_C : Constants.MIN_TEMP_F);
                dayRVModal.setMinTemp(getString(R.string.min_temp).concat(" " + minTemp + " " + tempSymbol));

                Double maxTemp = dayInfoObj.getDouble(tempMeasure.equals(Constants.TEMP_C) ? Constants.MAX_TEMP_C : Constants.MAX_TEMP_F);
                dayRVModal.setMaxTemp(getString(R.string.max_temp).concat(" " + maxTemp + " " + tempSymbol));

                String windSpeed = dayInfoObj.getString(windMeasure.equals(Constants.WIND_KPH) ? Constants.MAXWIND_KPH : Constants.MAXWIND_MPH);
                dayRVModal.setWindSpeed(getString(R.string.wind_speed).concat(" " + windSpeed + " " + windUnit));

                String visibility = dayInfoObj.getString(visibilityMeasure.equals(Constants.VIS_KM) ? Constants.AVGVIS_KM : Constants.AVGVIS_MILES);
                dayRVModal.setVisibility(getString(R.string.visibility).concat(" " + visibility + " " + visibilityUnit));

                dayRVModal.setHumidity(getString(R.string.humidity).concat(" " + dayInfoObj.getString(Constants.AVGHUMIDITY) + "%"));

                dayRVModal.setRainfallChance(getString(R.string.rainfall_chance).concat(" " + dayInfoObj.getString(Constants.DAILY_CHANCE_OF_RAIN) + "%"));

                Double rainfallQuantity = dayInfoObj.getDouble(rainfallMeasure.equals(Constants.PRECIP_MM) ? Constants.TOTALPRECIP_MM : Constants.TOTALPRECIP_IN);
                dayRVModal.setRainfallQuantity(getString(R.string.rainfall_quantity).concat(" " + rainfallQuantity + " " + rainfallUnit));

                dayRVModal.setDawnTime(getString(R.string.dawn_time).concat(" " + astroObj.getString(Constants.DAWN_TIME)));

                dayRVModal.setDuskTime(getString(R.string.dusk_time).concat(" " + astroObj.getString(Constants.DUSK_TIME)));

                dayRVModal.setMoonPhase(getString(R.string.moon_phase).concat(" " + astroObj.getString(Constants.MOON_PHASE)));

                forecastDaysListRVM.add(dayRVModal);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        forecastRVA.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed()
    {
        weatherChecker.interruptCheck();
        super.onBackPressed();
    }
}