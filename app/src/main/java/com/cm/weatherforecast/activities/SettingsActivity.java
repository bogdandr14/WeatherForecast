package com.cm.weatherforecast.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cm.weatherforecast.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListeners();
    }

    private void setListeners() {
        binding.idSettingsBackMB.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));

        binding.idCelsiusRB.setOnClickListener(view -> {

        });
    }
}