package com.justnik.weatherapp2.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrentWeatherDto(
    @SerializedName("id")
    @Expose
    var id: Int,

    @SerializedName("icon")
    @Expose
    var icon: String
)