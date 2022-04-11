package com.cm.weatherforecast.activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cm.weatherforecast.BaseWeatherActivity;
import com.cm.weatherforecast.Constants;
import com.cm.weatherforecast.PreferenceManager;
import com.cm.weatherforecast.R;
import com.cm.weatherforecast.WeatherChecker;
import com.cm.weatherforecast.adapters.HourlyWeatherRVAdapter;
import com.cm.weatherforecast.modals.HourlyWeatherRVModal;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseWeatherActivity implements LocationListener {
    private PreferenceManager preferenceManager;
    private WeatherChecker weatherChecker;

    public String cityName;

    private ProgressBar loadingPB;
    private ImageView settingsIV, locationManagerIV;

    private TextView cityNameTV, temperatureNowTV, conditionNowTV, feelsLikeTV, pressureTV, windSpeedTV, windDirectionTV, humidityTV, cloudsTV, visibilityTV, uvTV;
    private ImageView weatherNowIV;
    private RecyclerView hourlyWeatherRV;
    private MaterialButton forecastMB;
    private ArrayList<HourlyWeatherRVModal> hourlyWeatherListRVM;
    private HourlyWeatherRVAdapter weatherRVA;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);
        preferenceManager = new PreferenceManager(getApplicationContext());

        initializeElementsInActivity();
        checkSettingsPreferences();

        hourlyWeatherListRVM = new ArrayList<>();
        weatherRVA = new HourlyWeatherRVAdapter(this, hourlyWeatherListRVM);
        hourlyWeatherRV.setAdapter(weatherRVA);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.PERMISSION_CODE);
        }

        cityName = getSelectedCity(savedInstanceState);
        setListeners();

        weatherChecker = new WeatherChecker(this);
        weatherChecker.execute(cityName, Constants.WEATHER_API_LINK_TODAY);
    }

    private void checkSettingsPreferences() {
        if (preferenceManager.getString(Constants.KEY_TEMP_MEASURE) == null) {
            preferenceManager.putString(Constants.KEY_TEMP_MEASURE, Constants.TEMP_C);
            preferenceManager.putString(Constants.KEY_PRESSURE_MEASURE, Constants.PRESSURE_IN);
            preferenceManager.putString(Constants.KEY_VISIBILITY_MEASURE, Constants.VIS_KM);
            preferenceManager.putString(Constants.KEY_WIND_MEASURE, Constants.WIND_KPH);
            preferenceManager.putString(Constants.KEY_RAINFALL_MEASURE, Constants.PRECIP_MM);
        }
    }

    private String getSelectedCity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                return getCurrentLocation();
            } else {
                return extras.getString(Constants.CITY_NAME_MESSAGE);
            }
        } else {
            return (String) savedInstanceState.getSerializable(Constants.CITY_NAME_MESSAGE);
        }
    }

    private String getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.PERMISSION_CODE);
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location != null && location.getTime() > Calendar.getInstance().getTimeInMillis() - 2 * 60 * 1000) {
            // Do something with the recent location fix
            //  otherwise wait for the update below
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }

        return getLocationCityName(location.getLongitude(), location.getLatitude());
        //return Constants.CITY;
    }

    private void initializeElementsInActivity() {
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
    }

    private String getLocationCityName(double longitude, double latitude) {
        String cityName = getString(R.string.city_not_found);
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(latitude, longitude, 10);
            for (Address adr : addresses) {
                if (adr != null) {
                    String city = adr.getLocality();
                    if (city != null && !city.equals("")) {
                        cityName = city;
                    } else {
                        Log.d("TAG", getString(R.string.city_not_found));
                        Toast.makeText(this, getString(R.string.city_not_found), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.idInterestLocationsI:
                onOpenInterestLocationsActivity();
                return true;
            case R.id.idSettingsI:
                onOpenSettingsActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onOpenForecastActivity() {
        weatherChecker.interruptCheck();
        String message = cityNameTV.getText().toString();
        Intent intent = new Intent(getApplicationContext(), ForecastActivity.class);
        intent.putExtra(Constants.CITY_NAME_MESSAGE, message);
        startActivity(intent);
    }

    private void onOpenSettingsActivity() {
        weatherChecker.interruptCheck();
        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
    }

    private void onOpenInterestLocationsActivity() {
        weatherChecker.interruptCheck();
        startActivity(new Intent(getApplicationContext(), LocationManagerActivity.class));
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                String cityName = data.getStringExtra(Constants.CITY_NAME_MESSAGE);
                cityNameTV.setText(cityName);
                //TODO use api to get info for the current CITY
            } else {
                //TODO use api to get info for the current LOCATION
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.permission_granted), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.provide_permission), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void setWeatherInfo(JSONObject weatherInfo) {
        try {
            cityNameTV.setText(weatherInfo.getJSONObject(Constants.LOCATION).getString(Constants.NAME));
            setWeatherInfoNow(weatherInfo.getJSONObject(Constants.CURRENT));
            setHourlyWeather(weatherInfo.getJSONObject(Constants.FORECAST).getJSONArray(Constants.FORECASTDAY).getJSONObject(0).getJSONArray(Constants.HOUR));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setWeatherInfoNow(@NonNull JSONObject currentWeatherInfo) {
        try {
            String tempMeasureKey = preferenceManager.getString(Constants.KEY_TEMP_MEASURE);
            String tempSymbol = tempMeasureKey.equals(Constants.TEMP_C) ? " °C" : " °F";

            int temperature = currentWeatherInfo.getInt(tempMeasureKey);
            temperatureNowTV.setText(String.valueOf(temperature).concat(tempSymbol));

            String icon = currentWeatherInfo.getJSONObject(Constants.CONDITION).getString(Constants.ICON);
            Picasso.get().load("https:".concat(icon)).into(weatherNowIV);

            String condition = currentWeatherInfo.getJSONObject(Constants.CONDITION).getString(Constants.TEXT);
            conditionNowTV.setText(condition);

            String feelsLikeKey = tempMeasureKey.equals(Constants.TEMP_C) ? Constants.FEELSLIKE_C : Constants.FEELSLIKE_F;
            int feelsLike = currentWeatherInfo.getInt(feelsLikeKey);
            feelsLikeTV.setText(getString(R.string.feels_like).concat(" " + feelsLike).concat(tempSymbol));

            String windMeasureKey = preferenceManager.getString(Constants.KEY_WIND_MEASURE);
            String windUnit = windMeasureKey.equals(Constants.WIND_KPH) ? " km/h" : " mph";
            int windSpeed = currentWeatherInfo.getInt(windMeasureKey);
            windSpeedTV.setText(getString(R.string.wind_speed).concat(" " + windSpeed).concat(windUnit));

            String humidity = currentWeatherInfo.getString(Constants.HUMIDITY);
            humidityTV.setText(getString(R.string.humidity).concat(" " + humidity).concat(" %"));

            String visibilityMeasureKey = preferenceManager.getString(Constants.KEY_VISIBILITY_MEASURE);
            String visibilityUnit = windMeasureKey.equals(Constants.VIS_KM) ? " km" : " miles";
            int visibility = currentWeatherInfo.getInt(visibilityMeasureKey);
            visibilityTV.setText(getString(R.string.visibility).concat(" " + visibility).concat(visibilityUnit));

            String pressureMeasureKey = preferenceManager.getString(Constants.KEY_PRESSURE_MEASURE);
            double pressure;
            switch (pressureMeasureKey) {
                case Constants.PRESSURE_MB:
                    pressure = currentWeatherInfo.getDouble(Constants.PRESSURE_MB);
                    pressureTV.setText(getString(R.string.pressure).concat(" " + pressure).concat(" mb"));
                    break;
                case Constants.PRESSURE_IN:
                    pressure = currentWeatherInfo.getDouble(Constants.PRESSURE_IN);
                    pressure = BigDecimal.valueOf(pressure * 25.4)
                            .setScale(3, RoundingMode.HALF_UP)
                            .doubleValue();
                    pressureTV.setText(getString(R.string.pressure).concat(" " + pressure).concat(" mmHg"));
                    break;
                case Constants.PRESSURE_ATM:
                    pressure = currentWeatherInfo.getDouble(Constants.PRESSURE_MB);
                    pressure = BigDecimal.valueOf(pressure * 0.000987)
                            .setScale(3, RoundingMode.HALF_UP)
                            .doubleValue();
                    pressureTV.setText(getString(R.string.pressure).concat(" " + pressure).concat(" atm"));
                    break;
                case Constants.PRESSURE_PSI:
                    pressure = currentWeatherInfo.getDouble(Constants.PRESSURE_IN);
                    pressure = BigDecimal.valueOf(pressure / 2.036)
                            .setScale(3, RoundingMode.HALF_UP)
                            .doubleValue();
                    pressureTV.setText(getString(R.string.pressure).concat(" " + pressure).concat(" psi"));
                    break;
            }

            String windDirection = currentWeatherInfo.getString(Constants.WIND_DIR);
            windDirectionTV.setText(getString(R.string.wind_direction).concat(" " + windDirection));

            String nebulosity = currentWeatherInfo.getString(Constants.CLOUD);
            cloudsTV.setText(getString(R.string.clouds).concat(" " + nebulosity).concat(" %"));

            int uvIndex = currentWeatherInfo.getInt(Constants.UV);
            uvTV.setText(getString(R.string.uv_index).concat(" " + uvIndex));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private void setHourlyWeather(JSONArray hourlyWeatherInfo) {
        hourlyWeatherListRVM.clear();
        String tempMeasureKey = preferenceManager.getString(Constants.KEY_TEMP_MEASURE);
        String tempSymbol = tempMeasureKey.equals(Constants.TEMP_C) ? "°C" : "°F";
        String windMeasureKey = preferenceManager.getString(Constants.KEY_WIND_MEASURE);
        String windUnit = windMeasureKey.equals(Constants.WIND_KPH) ? "km/h" : "mph";
        try {
            for (int i = 0; i < hourlyWeatherInfo.length(); ++i) {
                JSONObject hourObj = hourlyWeatherInfo.getJSONObject(i);
                String time = hourObj.getString(Constants.TIME);
                String temperature = hourObj.getString(tempMeasureKey).concat(tempSymbol);
                String image = hourObj.getJSONObject(Constants.CONDITION).getString(Constants.ICON);
                String windSpeed = hourObj.getString(windMeasureKey).concat(" " + windUnit);
                hourlyWeatherListRVM.add(new HourlyWeatherRVModal(time, temperature, image, windSpeed));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        weatherRVA.notifyDataSetChanged();
    }

    private void setListeners() {
        forecastMB.setOnClickListener(view -> onOpenForecastActivity());

        settingsIV.setOnClickListener(view -> onOpenSettingsActivity());

        locationManagerIV.setOnClickListener(view -> onOpenInterestLocationsActivity());
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (location != null) {
            locationManager .removeUpdates(this);
        }
    }
}