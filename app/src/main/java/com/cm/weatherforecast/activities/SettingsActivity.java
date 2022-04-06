package com.cm.weatherforecast.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cm.weatherforecast.Constants;
import com.cm.weatherforecast.PreferenceManager;
import com.cm.weatherforecast.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;
    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());


        setPreferenceValues();
        setListeners();
    }

    private void setPreferenceValues() {
        String tempMeasurement = preferenceManager.getString(Constants.KEY_TEMP_MEASURE);
        binding.idCelsiusRB.setChecked(tempMeasurement.equals(Constants.TEMP_C));
        binding.idFahrenheitRB.setChecked(tempMeasurement.equals(Constants.TEMP_F));

        String pressMeasurement = preferenceManager.getString(Constants.KEY_PRESSURE_MEASURE);
        binding.idMillibarsRB.setChecked(pressMeasurement.equals(Constants.PRESSURE_MB));
        binding.idMmHgRB.setChecked(pressMeasurement.equals(Constants.PRESSURE_IN));
        binding.idPsiRB.setChecked(pressMeasurement.equals(Constants.PRESSURE_PSI));
        binding.idAtmRB.setChecked(pressMeasurement.equals(Constants.PRESSURE_ATM));

        String visibilityMeasurement = preferenceManager.getString(Constants.KEY_VISIBILITY_MEASURE);
        binding.idVisibilityInKmRB.setChecked(visibilityMeasurement.equals(Constants.VIS_KM));
        binding.idVisibilityInMilesRB.setChecked(visibilityMeasurement.equals(Constants.VIS_MILES));

        String windMeasurement = preferenceManager.getString(Constants.KEY_WIND_MEASURE);
        binding.idWindInKmRB.setChecked(windMeasurement.equals(Constants.WIND_KPH));
        binding.idWindInMilesRB.setChecked(windMeasurement.equals(Constants.WIND_MPH));

        String rainfallMeasurement = preferenceManager.getString(Constants.KEY_RAINFALL_MEASURE);
        binding.idMillimetersRB.setChecked(rainfallMeasurement.equals(Constants.PRECIP_MM));
        binding.idInchRB.setChecked(rainfallMeasurement.equals(Constants.PRECIP_IN));
    }

    private void setListeners() {
        binding.idApplyChangesMB.setOnClickListener(view -> {
            if(binding.idCelsiusRB.isChecked()){
                preferenceManager.putString(Constants.KEY_TEMP_MEASURE,Constants.TEMP_C);
            } else if(binding.idFahrenheitRB.isChecked()){
                preferenceManager.putString(Constants.KEY_TEMP_MEASURE,Constants.TEMP_F);
            }

            if(binding.idMillibarsRB.isChecked()){
                preferenceManager.putString(Constants.KEY_PRESSURE_MEASURE,Constants.PRESSURE_MB);
            } else if(binding.idMmHgRB.isChecked()){
                preferenceManager.putString(Constants.KEY_PRESSURE_MEASURE,Constants.PRESSURE_IN);
            } else if(binding.idPsiRB.isChecked()){
                preferenceManager.putString(Constants.KEY_PRESSURE_MEASURE,Constants.PRESSURE_PSI);
            } else if(binding.idAtmRB.isChecked()){
                preferenceManager.putString(Constants.KEY_PRESSURE_MEASURE,Constants.PRESSURE_ATM);
            }

            if(binding.idVisibilityInKmRB.isChecked()){
                preferenceManager.putString(Constants.KEY_VISIBILITY_MEASURE,Constants.VIS_KM);
            } else if(binding.idVisibilityInMilesRB.isChecked()){
                preferenceManager.putString(Constants.KEY_VISIBILITY_MEASURE,Constants.VIS_MILES);
            }

            if(binding.idWindInKmRB.isChecked()){
                preferenceManager.putString(Constants.KEY_WIND_MEASURE,Constants.WIND_KPH);
            } else if(binding.idWindInMilesRB.isChecked()){
                preferenceManager.putString(Constants.KEY_WIND_MEASURE,Constants.WIND_MPH);
            }

            if(binding.idMillimetersRB.isChecked()){
                preferenceManager.putString(Constants.KEY_RAINFALL_MEASURE,Constants.PRECIP_MM);
            } else if(binding.idInchRB.isChecked()){
                preferenceManager.putString(Constants.KEY_RAINFALL_MEASURE,Constants.PRECIP_IN);
            }

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });


    }
}