package com.devreport.cloud.activity.main.fragment.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherInterface {
    @GET("data/2.5/weather")
    Call<WeatherAllData> getCurrentWeather(@Query("id") int id, @Query("appid") String appId);
}
