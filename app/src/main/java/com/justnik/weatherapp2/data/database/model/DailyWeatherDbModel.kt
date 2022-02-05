package com.justnik.weatherapp2.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_weather")
data class DailyWeatherDbModel (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "parent_city_name")
    val parentCityName: String,
    val date: Long,
    @ColumnInfo(name = "morning_tem[")
    val morningTemp: Int,
    @ColumnInfo(name = "day_temp")
    val dayTemp: Int,
    @ColumnInfo(name = "evening_temp")
    val eveningTemp: Int,
    @ColumnInfo(name = "night_temp")
    val nightTemp: Int,
    @ColumnInfo(name = "daily_weather_icon_url")
    val dailyWeatherIconURL: String
)