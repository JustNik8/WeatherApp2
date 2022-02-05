package com.justnik.weatherapp2.data.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class CityWeatherWithDailyWeather (
    @Embedded val cityWeather: CityWeatherDbModel,
    @Relation(
        parentColumn = "city_name",
        entityColumn = "parent_city_name"
    )
    val dailyWeather: List<DailyWeatherDbModel>
)