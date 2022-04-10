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
        //setDummyDayWeather();
        Toast.makeText(this, cityName, Toast.LENGTH_SHORT).show();
        weatherChecker = new WeatherChecker(this);
        weatherChecker.execute(cityName, Constants.WEATHER_API_LINK_FORECAST);
    }

//    private void setDummyDayWeather(){
//        for (int i = 0; i < 7; i++) {
//            forecastDaysListRVM.add(new DayRVModal("2001-01-01 " + (i < 10 ? "0" : "") + i + ":00", "35", "cdn.weatherapi.com/weather/64x64/day/122.png"));
//        }
//        forecastRVA = new ForecastRVAdapter(this, forecastDaysListRVM);
//        sevenDayForecastRV.setAdapter(forecastRVA);
//    }

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
        String tempMeasureKey = preferenceManager.getString(Constants.KEY_TEMP_MEASURE);
        String tempSymbol = tempMeasureKey.equals(Constants.TEMP_C) ? "°C" : "°F";
        String windMeasureKey = preferenceManager.getString(Constants.KEY_WIND_MEASURE);
        String windUnit = windMeasureKey.equals(Constants.WIND_KPH) ? "km/h" : "mph";
        String rainfallMeasureKey = preferenceManager.getString(Constants.KEY_RAINFALL_MEASURE);
        String rainfallUnit = rainfallMeasureKey.equals(Constants.PRECIP_MM) ? "mm" : "in";
        String pressureMeasureKey = preferenceManager.getString(Constants.KEY_PRESSURE_MEASURE);
        String pressureUnit = pressureMeasureKey.equals(Constants.WIND_KPH) ? "km/h" : "mph";
        switch (pressureMeasureKey) {
            case Constants.PRESSURE_MB:
                pressureUnit = " mb";
                break;
            case Constants.PRESSURE_IN:
                pressureUnit = " mmHg";
                break;
            case Constants.PRESSURE_ATM:
                pressureUnit = "atm";
                break;
            case Constants.PRESSURE_PSI:
                pressureUnit = "psi";
                break;
        }
        try {
            for (int i = 0; i < forecastDaysWeatherInfo.length(); ++i) {
                DayRVModal dayRVModal = new DayRVModal();
                JSONObject dayObj = forecastDaysWeatherInfo.getJSONObject(i);
                dayRVModal.setDateAndDescription(dayObj.getString(Constants.DATE));
                dayRVModal.setIcon(dayObj.getJSONObject(Constants.CONDITION).getString(Constants.ICON));
                dayRVModal.setMinTemp(getString(R.string.min_temp).concat(dayObj.getString(tempMeasureKey) + " " + tempSymbol));
                dayRVModal.setMaxTemp(getString(R.string.max_temp).concat(dayObj.getString(tempMeasureKey) + " " + tempSymbol));
                dayRVModal.setWindSpeed(getString(R.string.wind_speed).concat(dayObj.getString(windMeasureKey) + " " + windUnit));
                dayRVModal.setWindDirection(getString(R.string.wind_speed).concat(dayObj.getString(Constants.WIND_DIR)));
                dayRVModal.setHumidity(getString(R.string.humidity).concat(dayObj.getString(Constants.HUMIDITY) + "%"));
                double pressure = 0;
                switch (pressureMeasureKey) {
                    case Constants.PRESSURE_MB:
                        pressure = forecastDaysWeatherInfo.getDouble(Integer.parseInt(Constants.PRESSURE_MB));
                        break;
                    case Constants.PRESSURE_IN:
                        pressure = forecastDaysWeatherInfo.getDouble(Integer.parseInt(Constants.PRESSURE_IN));
                        pressure = BigDecimal.valueOf(pressure * 25.4)
                                .setScale(3, RoundingMode.HALF_UP)
                                .doubleValue();
                        break;
                    case Constants.PRESSURE_ATM:
                        pressure = forecastDaysWeatherInfo.getDouble(Integer.parseInt(Constants.PRESSURE_MB));
                        pressure = BigDecimal.valueOf(pressure * 0.000987)
                                .setScale(3, RoundingMode.HALF_UP)
                                .doubleValue();
                        break;
                    case Constants.PRESSURE_PSI:
                        pressure = forecastDaysWeatherInfo.getDouble(Integer.parseInt(Constants.PRESSURE_IN));
                        pressure = BigDecimal.valueOf(pressure / 2.036)
                                .setScale(3, RoundingMode.HALF_UP)
                                .doubleValue();
                        break;
                }
                dayRVModal.setPressure(getString(R.string.pressure).concat(" " + pressure).concat(pressureUnit));
                dayRVModal.setRainfallChance(getString(R.string.rainfall_chance).concat(dayObj.getString(Constants.RAINFALL_CHANCE) + "%"));
                dayRVModal.setRainfallQuantity(getString(R.string.rainfall_quantity).concat(dayObj.getString(rainfallMeasureKey) + " " + rainfallUnit));
                dayRVModal.setDawnTime(getString(R.string.dawn_time).concat(dayObj.getString(Constants.DAWN_TIME)));
                dayRVModal.setDuskTime(getString(R.string.dusk_time).concat(dayObj.getString(Constants.DUSK_TIME)));
                dayRVModal.setMoonPhase(getString(R.string.moon_phase).concat(dayObj.getString(Constants.MOON_PHASE)));
                dayRVModal.setAirQuality(getString(R.string.air_quality).concat(dayObj.getString(Constants.UV)));

                forecastDaysListRVM.add(dayRVModal);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        forecastRVA.notifyDataSetChanged();
    }
}