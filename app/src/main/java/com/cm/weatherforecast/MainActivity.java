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

    private ProgressBar progressBar;
    private RelativeLayout layout;
    private ImageView bg;
    private TextView cityName;
    private TextInputEditText citySearch;
    private ImageView searchIcon;
    private TextView temperature;
    private ImageView weatherIcon;
    private TextView description;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.idProgressBar);
        layout = findViewById(R.id.idHome);
        bg = findViewById(R.id.idBg);
        cityName = findViewById(R.id.idCityName);
        citySearch = findViewById(R.id.idCitySearch);
        searchIcon = findViewById(R.id.idImgSearch);
        temperature = findViewById(R.id.idTemperatureNow);
        weatherIcon = findViewById(R.id.idWeatherNowIcon);
        description = findViewById(R.id.idDescription);
        recycler = findViewById(R.id.idRecycler);
    }
}