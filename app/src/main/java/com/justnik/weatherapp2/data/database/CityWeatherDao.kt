package com.justnik.weatherapp2.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.justnik.weatherapp2.data.database.model.CityWeatherDbModel
import com.justnik.weatherapp2.data.database.model.CityWeatherMainInfoTuple
import com.justnik.weatherapp2.data.database.model.CityWeatherWithDailyWeather
import com.justnik.weatherapp2.data.database.model.DailyWeatherDbModel
import com.justnik.weatherapp2.domain.entities.CityWeather
import com.justnik.weatherapp2.domain.entities.DailyWeather

@Dao
interface CityWeatherDao {
    @Transaction
    @Query("SELECT * FROM city_weather")
    fun getCityWeatherWithDailyWeatherList(): LiveData<List<CityWeatherWithDailyWeather>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCityWeather(CityWeather: CityWeatherDbModel)

    @Insert
    fun insertDailyWeather(dailyWeatherList: List<DailyWeatherDbModel>)

}