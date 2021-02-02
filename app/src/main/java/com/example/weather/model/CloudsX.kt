package com.example.weather.model


import com.google.gson.annotations.SerializedName

data class CloudsX(
    @SerializedName("all")
    var all: Int = 0
)