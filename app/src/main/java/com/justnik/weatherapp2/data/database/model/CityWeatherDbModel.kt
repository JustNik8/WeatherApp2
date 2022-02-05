package com.justnik.weatherapp2.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_weather")
data class CityWeatherDbModel (
    @PrimaryKey
    @ColumnInfo(name = "city_name")
    val cityName: String,
    @ColumnInfo(name = "current_temp")
    val currentTemp: Int,
    @ColumnInfo(name = "current_weather_description")
    val currentWeatherDescription: String,
    @ColumnInfo(name = "current_weather_icon_url")
    val currentWeatherIconURL: String,

    //val dailyWeather: List<DailyWeatherDbModel>
)