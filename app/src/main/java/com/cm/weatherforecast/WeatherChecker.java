package com.cm.weatherforecast;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class WeatherChecker extends AsyncTask<String, Void, String> {

    private JSONObject weatherResponse;
    private BaseWeatherActivity weatherActivity;
    private boolean runCheck;

    public WeatherChecker(BaseWeatherActivity weatherActivity) {
        this.weatherActivity = weatherActivity;
    }

    @Override
    protected String doInBackground(String... params) {
        int s = 60000;
        runCheck = true;
        String cityName = params[0];
        String endLink = params[1];

        String url = Constants.WEATHER_API_LINK_START + cityName + endLink;
        RequestQueue requestQueue = Volley.newRequestQueue(weatherActivity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            this.weatherResponse = response;
            Log.d("TAG", "CALL COMPLETED");
            if (runCheck) {
                this.weatherActivity.setWeatherInfo(response);
            }
        }, error -> Toast.makeText(weatherActivity, weatherActivity.getString(R.string.enter_valid_city), Toast.LENGTH_SHORT).show());

        while (runCheck) {
            requestQueue.add(jsonObjectRequest);
            try {
                Thread.sleep(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return this.weatherResponse.toString();
    }

    public void interruptCheck() {
        this.runCheck = false;
    }

    protected void onPostExecute(String result) {
    }
}
