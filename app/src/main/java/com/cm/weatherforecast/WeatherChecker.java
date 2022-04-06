package com.cm.weatherforecast;

import android.os.AsyncTask;
import android.widget.TextView;


import java.lang.ref.WeakReference;

public class WeatherChecker extends AsyncTask<String, Void, String> {
    private final WeakReference<TextView> temperatureTV;

    public WeatherChecker(TextView tv) {
        temperatureTV = new WeakReference<>(tv);
    }

    @Override
    protected String doInBackground(String... params) {
        int s = 6000;
        boolean runCheck = true;
        String temperature = params[0];

        String[] parts = temperature.split("°");
        int tempValue = 0;
        try{
            tempValue = Integer.parseInt(parts[0]);
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
        while(runCheck) {
            tempValue++;
            temperature = tempValue + "°C";

            temperatureTV.get().setText(temperature);

            try {
                Thread.sleep(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return temperature;
    }

    protected void onPostExecute(String result) {
        temperatureTV.get().setText(result);
    }
}
