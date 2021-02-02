package com.example.weather.model


import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("coord")
    var coord: CoordX = CoordX(),
    @SerializedName("country")
    var country: String = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("population")
    var population: Int = 0,
    @SerializedName("sunrise")
    var sunrise: Int = 0,
    @SerializedName("sunset")
    var sunset: Int = 0,
    @SerializedName("timezone")
    var timezone: Int = 0
)