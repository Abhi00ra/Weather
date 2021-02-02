package com.example.weather.model


import com.google.gson.annotations.SerializedName

data class MainX(
    @SerializedName("feels_like")
    var feelsLike: Double = 0.0,
    @SerializedName("grnd_level")
    var grndLevel: Int = 0,
    @SerializedName("humidity")
    var humidity: Int = 0,
    @SerializedName("pressure")
    var pressure: Int = 0,
    @SerializedName("sea_level")
    var seaLevel: Int = 0,
    @SerializedName("temp")
    var temp: Double = 0.0,
    @SerializedName("temp_kf")
    var tempKf: Double = 0.0,
    @SerializedName("temp_max")
    var tempMax: Double = 0.0,
    @SerializedName("temp_min")
    var tempMin: Double = 0.0
)