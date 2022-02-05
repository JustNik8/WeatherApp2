package com.justnik.weatherapp2.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.justnik.weatherapp2.data.database.model.CityWeatherWithDailyWeather
import com.justnik.weatherapp2.domain.entities.CityWeather

@Dao
interface CityWeatherDao {
    @Query("SELECT * FROM city_weather")
    fun getCityWeather(): LiveData<CityWeather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCityWeather(cityWeather: CityWeather)
}