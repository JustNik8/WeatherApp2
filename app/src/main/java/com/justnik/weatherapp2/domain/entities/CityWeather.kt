package com.justnik.weatherapp2.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityWeather (
    val cityName: String,

    val currentTemp: String,
    val currentWeatherDescription: String,
    val currentWeatherIconURL: String,

    val dailyWeather: List<DailyWeather>
) : Parcelable