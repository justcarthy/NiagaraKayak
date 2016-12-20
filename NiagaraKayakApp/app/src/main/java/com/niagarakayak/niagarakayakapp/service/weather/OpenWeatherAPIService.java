package com.niagarakayak.niagarakayakapp.service.weather;

import android.os.AsyncTask;
import com.niagarakayak.niagarakayakapp.model.Weather;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenWeatherAPIService implements WeatherService {

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String BASE_IMG_URL = "http://openweathermap.org/img/w/";
    private String APPID = "&APPID=";

    public OpenWeatherAPIService(String API_KEY) {
        APPID+=API_KEY;
    }

    @Override
    public void fetchWeather(WeatherServiceRequest request) {
        new getWeatherTask().execute(request);
    }

    /**
     * @param location - Will be given in the following format: city_name,country_code
     * @return  String  JSON Response.
     */

    private String getWeatherData(String location) {
        HttpURLConnection con = null ;
        InputStream is = null;

        try {
            con = (HttpURLConnection) (new URL(BASE_URL + location + APPID)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while (  (line = br.readLine()) != null )
                buffer.append(line + "\r\n");

            is.close();
            con.disconnect();
            return buffer.toString();
        } catch(Throwable t) {
            t.printStackTrace();
        }

        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }

    private class getWeatherTask extends AsyncTask<WeatherServiceRequest, Void, Weather> {
        private Exception exception;
        private WeatherCallback callback;

        @Override
        protected Weather doInBackground(WeatherServiceRequest... requests) {
            WeatherServiceRequest mRequest = requests[0];

            String cityName = mRequest.getCityName();
            this.callback = mRequest.getCallback();

            String data = getWeatherData(cityName+",CA");
            Weather weather = null;

            try {
                weather = JSONWeatherParser.getWeather(data);
            } catch (JSONException e) {
                this.exception = e;
            }

            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            if (exception != null || weather == null) {
                callback.onFailure();
            } else {
                callback.onSuccess(weather);
            }
        }
    }
}
