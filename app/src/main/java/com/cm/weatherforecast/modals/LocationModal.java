package com.cm.weatherforecast.modals;

import android.content.Context;

public class LocationModal {
    private String locationName;
    private String temperature;

    public LocationModal(String locationName, String temperature) {
        this.locationName = locationName;
        this.temperature = temperature;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
