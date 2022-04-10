package com.cm.weatherforecast.modals;

public class DayRVModal {
    private String date;
    private String description;
    private String icon;
    private String minTemp;
    private String maxTemp;
    private String windSpeed;
    private String humidity;
    private String rainfallChance;
    private String rainfallQuantity;
    private String dawnTime;
    private String duskTime;
    private String moonPhase;
    private String visibility;

    public DayRVModal() {

    }

    public DayRVModal(String dateAndDescription, String description, String icon, String minTemp, String maxTemp,
                      String windSpeed, String humidity,
                      String rainfallChance, String rainfallQuantity, String dawnTime,
                      String duskTime, String visibility, String moonPhase) {
        this.date = dateAndDescription;
        this.description = description;
        this.icon = icon;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.rainfallChance = rainfallChance;
        this.rainfallQuantity = rainfallQuantity;
        this.dawnTime = dawnTime;
        this.duskTime = duskTime;
        this.visibility = visibility;
        this.moonPhase = moonPhase;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getRainfallChance() {
        return rainfallChance;
    }

    public void setRainfallChance(String rainfallChance) {
        this.rainfallChance = rainfallChance;
    }

    public String getRainfallQuantity() {
        return rainfallQuantity;
    }

    public void setRainfallQuantity(String rainfallQuantity) {
        this.rainfallQuantity = rainfallQuantity;
    }

    public String getDawnTime() {
        return dawnTime;
    }

    public void setDawnTime(String dawnTime) {
        this.dawnTime = dawnTime;
    }

    public String getDuskTime() {
        return duskTime;
    }

    public void setDuskTime(String duskTime) {
        this.duskTime = duskTime;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getMoonPhase() {
        return moonPhase;
    }

    public void setMoonPhase(String moonPhase) {
        this.moonPhase = moonPhase;
    }

}
