package com.justnik.weatherapp2.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DailyDto (
    @SerializedName("dt")
    @Expose
    var dt: Int,

    @SerializedName("temp")
    @Expose
    var temp: DailyTempDto,

    @SerializedName("weather")
    @Expose
    var weather: List<DailyWeatherDto>
)