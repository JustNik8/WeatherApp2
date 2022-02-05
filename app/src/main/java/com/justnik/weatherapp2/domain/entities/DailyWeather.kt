package com.justnik.weatherapp2.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailyWeather (
    val date: String,
    val morningTemp: String,
    val dayTemp: String,
    val eveningTemp: String,
    val nightTemp: String,
    val dailyWeatherIconURL: String
): Parcelable