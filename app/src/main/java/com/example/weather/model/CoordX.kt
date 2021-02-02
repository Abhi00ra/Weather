package com.example.weather.model


import com.google.gson.annotations.SerializedName

data class CoordX(
    @SerializedName("lat")
    var lat: Double = 0.0,
    @SerializedName("lon")
    var lon: Double = 0.0
)