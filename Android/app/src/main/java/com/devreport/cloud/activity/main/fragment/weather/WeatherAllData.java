package com.devreport.cloud.activity.main.fragment.weather;

import com.google.gson.annotations.SerializedName;

public class WeatherAllData {
    @SerializedName("main")
    private WeatherDetailData detailData;

    @SerializedName("wind")
    private WeatherWindData windData;

    @SerializedName("clouds")
    private WeatherCloudData cloudData;

    public WeatherDetailData getDetailData() {
        return detailData;
    }

    public WeatherWindData getWindData() {
        return windData;
    }

    public WeatherCloudData getCloudData() {
        return cloudData;
    }
}

class WeatherDetailData {
    @SerializedName("temp")
    private double temp;

    @SerializedName("pressure")
    private int pressure;

    @SerializedName("humidity")
    private int humidity;

    public double getTemp() {
        return temp;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }
}

class WeatherWindData {
    @SerializedName("speed")
    private double speed;

    public double getSpeed() {
        return speed;
    }
}

class WeatherCloudData {
    @SerializedName("all")
    private int density;

    public int getDensity() {
        return density;
    }
}