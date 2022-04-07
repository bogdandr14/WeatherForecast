package com.cm.weatherforecast.activities;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

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

        weatherChecker = new WeatherChecker(this);
        weatherChecker.execute(cityName, Constants.WEATHER_API_LINK_FORECAST);
    }

    private void setDummyDayWeather(){
        for (int i = 0; i < 7; i++) {
            forecastDaysListRVM.add(new DayRVModal("2001-01-01 " + (i < 10 ? "0" : "") + i + ":00", "35", "cdn.weatherapi.com/weather/64x64/day/122.png"));
        }
        forecastRVA = new ForecastRVAdapter(this, forecastDaysListRVM);
        sevenDayForecastRV.setAdapter(forecastRVA);
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

//                double pressure;
//                switch (pressureMeasureKey) {
//                    case Constants.PRESSURE_MB:
//                        pressure = currentWeatherInfo.getDouble(Constants.PRESSURE_MB);
//                        break;
//                    case Constants.PRESSURE_IN:
//                        pressure = currentWeatherInfo.getDouble(Constants.PRESSURE_IN);
//                        pressure = BigDecimal.valueOf(pressure * 25.4)
//                                .setScale(3, RoundingMode.HALF_UP)
//                                .doubleValue();
//                        break;
//                    case Constants.PRESSURE_ATM:
//                        pressure = currentWeatherInfo.getDouble(Constants.PRESSURE_MB);
//                        pressure = BigDecimal.valueOf(pressure * 0.000987)
//                                .setScale(3, RoundingMode.HALF_UP)
//                                .doubleValue();
//                        break;
//                    case Constants.PRESSURE_PSI:
//                        pressure = currentWeatherInfo.getDouble(Constants.PRESSURE_IN);
//                        pressure = BigDecimal.valueOf(pressure / 2.036)
//                                .setScale(3, RoundingMode.HALF_UP)
//                                .doubleValue();
//                        break;
//                }
//                dayRVModal.setPressure(getString(R.string.pressure).concat(" " + pressure).concat(pressureUnit));

                JSONObject hourObj = forecastDaysWeatherInfo.getJSONObject(i);

                forecastDaysListRVM.add(dayRVModal);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        forecastRVA.notifyDataSetChanged();
    }
}