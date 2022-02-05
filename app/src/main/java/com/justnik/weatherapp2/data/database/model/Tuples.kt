package com.justnik.weatherapp2.data.database.model

import androidx.room.ColumnInfo

data class CityWeatherMainInfoTuple(
    @ColumnInfo(name = "city_name")
    val cityName: String,

    @ColumnInfo(name = "current_temp")
    val currentTemp: Int,

    @ColumnInfo(name = "current_weather_icon_url")
    val currentWeatherIconURL: String
)