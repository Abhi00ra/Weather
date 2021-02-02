package com.example.weather.model


import com.google.gson.annotations.SerializedName

data class SysX(
    @SerializedName("pod")
    var pod: String = ""
)