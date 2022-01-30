package com.justnik.weatherapp2.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherInfoDto(
    @SerializedName("lat")
    @Expose
    var lat: Double,

    @SerializedName("lon")
    @Expose
    var lon: Double,

    @SerializedName("current")
    @Expose
    var current: CurrentDto,

    @SerializedName("daily")
    @Expose
    var daily: List<DailyDto>
)