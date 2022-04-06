package com.cm.weatherforecast.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private PreferenceManager preferenceManager;


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

        checkSelectedCity(savedInstanceState);
        setListeners();

        //new WeatherChecker(temperatureNowTV).execute(temperatureNowTV.getText().toString());
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

    private void checkSelectedCity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                getCurrentLocation();
            } else {
                cityName = extras.getString(Constants.CITY_NAME_MESSAGE);
                cityNameTV.setText(cityName);
            }
        } else {
            cityName = (String) savedInstanceState.getSerializable(Constants.CITY_NAME_MESSAGE);
            cityNameTV.setText(cityName);
        }
        getWeatherInfo(cityName);
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.PERMISSION_CODE);
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        //cityName = getCityName(location.getLongitude(), location.getLatitude());
        cityName = "Craiova";
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

    private String getCityName(double longitude, double latitude) {
        String cityName = "Not found";
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(latitude, longitude, 10);
            for (Address adr : addresses) {
                if (adr != null) {
                    String city = adr.getLocality();
                    if (city != null && !city.equals("")) {
                        cityName = city;
                    } else {
                        Log.d("TAG", "CITY NOT FOUND");
                        Toast.makeText(this, "User City Not Found...", Toast.LENGTH_SHORT).show();
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
        String message = cityNameTV.getText().toString();
        Intent intent = new Intent(getApplicationContext(), ForecastActivity.class);
        intent.putExtra(Constants.CITY_NAME_MESSAGE, message);
        startActivity(intent);
    }

    private void onOpenSettingsActivity() {
        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
    }

    private void onOpenInterestLocationsActivity() {
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
                Toast.makeText(this, "Permissions granted...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please provide the permissions", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void getWeatherInfo(String cityName) {
        String url = "https://api.weatherapi.com/v1/forecast.json?key=244e95839e83453bb05122307222002&q=" + cityName + "&days=7&aqi=yes&alerts=yes";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            loadingPB.setVisibility(View.GONE);
            try {
                setWeatherInfoNow(response.getJSONObject("current"));
                setHourlyWeather(response.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0).getJSONArray("hour"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(MainActivity.this, "Please enter valid city name", Toast.LENGTH_SHORT).show());
        requestQueue.add(jsonObjectRequest);
    }

    private void setWeatherInfoNow(@NonNull JSONObject currentWeatherInfo) {
        try {
            String tempMeasureKey = preferenceManager.getString(Constants.KEY_TEMP_MEASURE);
            String tempSymbol = tempMeasureKey.equals(Constants.TEMP_C) ? " °C" : " °F";

            int temperature = currentWeatherInfo.getInt(tempMeasureKey);
            temperatureNowTV.setText(String.valueOf(temperature).concat(tempSymbol));

            String icon = currentWeatherInfo.getJSONObject("condition").getString("icon");
            Picasso.get().load("https:".concat(icon)).into(weatherNowIV);

            String condition = currentWeatherInfo.getJSONObject("condition").getString("text");
            conditionNowTV.setText(condition);

            //Bg image 1:02:05
//                    int isDay = response.getJSONObject("current").getInt("id_day");
//                    if (isDay == 1) {
//                        Picasso.get().load("").into(viewSV);
//                    } else {
//                        Picasso.get().load("").into(viewSV);
//                    }

            String feelsLikeKey = tempMeasureKey.equals(Constants.TEMP_C) ? Constants.FEELSLIKE_C : Constants.FEELSLIKE_F;
            int feelsLike = currentWeatherInfo.getInt(feelsLikeKey);
            feelsLikeTV.setText(getString(R.string.feels_like).concat(" " + String.valueOf(feelsLike)).concat(tempSymbol));

            String windMeasureKey = preferenceManager.getString(Constants.KEY_WIND_MEASURE);
            String windUnit = windMeasureKey.equals(Constants.WIND_KPH) ? " km/h" : " mph";
            int windSpeed = currentWeatherInfo.getInt(windMeasureKey);
            windSpeedTV.setText(getString(R.string.wind_speed).concat(" " + String.valueOf(windSpeed)).concat(windUnit));

            String humidity = currentWeatherInfo.getString("humidity");
            humidityTV.setText(getString(R.string.humidity).concat(" " + humidity).concat(" %"));

            String visibilityMeasureKey = preferenceManager.getString(Constants.KEY_VISIBILITY_MEASURE);
            String visibilityUnit = windMeasureKey.equals(Constants.WIND_KPH) ? " km" : " miles";
            int visibility = currentWeatherInfo.getInt(visibilityMeasureKey);
            visibilityTV.setText(getString(R.string.visibility).concat(" " + String.valueOf(visibility)).concat(visibilityUnit));

            String pressureMeasureKey = preferenceManager.getString(Constants.KEY_PRESSURE_MEASURE);
            double pressure;
            switch (pressureMeasureKey) {
                case Constants.PRESSURE_MB:
                    pressure = currentWeatherInfo.getDouble(Constants.PRESSURE_MB);
                    pressureTV.setText(getString(R.string.pressure).concat(" " + Double.toString(pressure)).concat(" mb"));
                    break;
                case Constants.PRESSURE_IN:
                    pressure = currentWeatherInfo.getDouble(Constants.PRESSURE_IN);
                    pressureTV.setText(getString(R.string.pressure).concat(" " + Double.toString(pressure)).concat(" in"));
                    break;
                case Constants.PRESSURE_ATM:
                    pressure = currentWeatherInfo.getDouble(Constants.PRESSURE_MB);
                    pressure = BigDecimal.valueOf(pressure * 0.000987)
                            .setScale(3, RoundingMode.HALF_UP)
                            .doubleValue();
                    pressureTV.setText(getString(R.string.pressure).concat(" " + Double.toString(pressure)).concat(" atm"));
                    break;
                case Constants.PRESSURE_PSI:
                    pressure = currentWeatherInfo.getDouble(Constants.PRESSURE_IN);
                    pressure = BigDecimal.valueOf(pressure / 2.036)
                            .setScale(3, RoundingMode.HALF_UP)
                            .doubleValue();
                    pressureTV.setText(getString(R.string.pressure).concat(" " + Double.toString(pressure)).concat(" psi"));
                    break;
            }

            String windDirection = currentWeatherInfo.getString("wind_dir");
            windDirectionTV.setText(getString(R.string.wind_direction).concat(" " + windDirection));

            String nebulosity = currentWeatherInfo.getString("cloud");
            cloudsTV.setText(getString(R.string.clouds).concat(" " + nebulosity).concat(" %"));

            int uvIndex = currentWeatherInfo.getInt("uv");
            uvTV.setText(getString(R.string.uv_index).concat(" " + String.valueOf(uvIndex)));

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
                String time = hourObj.getString("time");
                String temperature = hourObj.getString(tempMeasureKey).concat(tempSymbol);
                String image = hourObj.getJSONObject("condition").getString("icon");
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
}