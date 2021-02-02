package com.example.weather.model


import com.google.gson.annotations.SerializedName

data class ForecastReport(
    @SerializedName("city")
    var city: City = City(),
    @SerializedName("cnt")
    var cnt: Int = 0,
    @SerializedName("cod")
    var cod: String = "",
    @SerializedName("list")
    var list: List<Main> = listOf(),
    @SerializedName("message")
    var message: Int = 0
)