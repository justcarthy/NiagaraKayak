package com.niagarakayak.niagarakayakapp.service.weather;

import com.niagarakayak.niagarakayakapp.model.Weather;

public interface WeatherService {

    void fetchWeather(WeatherServiceRequest weatherServiceRequest);

    interface WeatherCallback {
        void onFailure();
        void onSuccess(Weather weather);
    }

    /**
     * This object is used to make a request to fetch the weather.
     */

    class WeatherServiceRequest {
        private String cityName;
        WeatherCallback callback;

        public WeatherServiceRequest(String cityName, WeatherCallback callback) {
            this.cityName = cityName;
            this.callback = callback;
        }

        public String getCityName() {
            return cityName;
        }

        public WeatherCallback getCallback() {
            return callback;
        }
    }

}
