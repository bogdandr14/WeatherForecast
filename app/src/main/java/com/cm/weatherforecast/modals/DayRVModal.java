package com.cm.weatherforecast.modals;

public class DayRVModal {
    private String dateAndDescription;
    private String icon;
    private String minTemp;
    private String maxTemp;
    private String windSpeed;
    private String windDirection;
    private String humidity;
    private String pressure;
    private String rainfallChance;
    private String rainfallQuantity;
    private String dawnTime;
    private String duskTime;
    private String moonPhase;
    private String airQuality;

    public DayRVModal(){

    }

    public DayRVModal(String dateAndDescription, String icon, String minTemp, String maxTemp,
                      String windSpeed, String windDirection, String humidity, String pressure,
                      String rainfallChance, String rainfallQuantity, String dawnTime,
                      String duskTime, String moonPhase, String airQuality) {
        this.dateAndDescription = dateAndDescription;
        this.icon = icon;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.humidity = humidity;
        this.pressure = pressure;
        this.rainfallChance = rainfallChance;
        this.rainfallQuantity = rainfallQuantity;
        this.dawnTime = dawnTime;
        this.duskTime = duskTime;
        this.moonPhase = moonPhase;
        this.airQuality = airQuality;
    }

    public String getDateAndDescription() {
        return dateAndDescription;
    }

    public void setDateAndDescription(String dateAndDescription) {
        this.dateAndDescription = dateAndDescription;
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

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
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

    public String getMoonPhase() {
        return moonPhase;
    }

    public void setMoonPhase(String moonPhase) {
        this.moonPhase = moonPhase;
    }

    public String getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(String airQuality) {
        this.airQuality = airQuality;
    }
}
