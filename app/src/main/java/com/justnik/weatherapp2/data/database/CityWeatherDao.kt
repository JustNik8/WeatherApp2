package com.justnik.weatherapp2.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.justnik.weatherapp2.data.database.model.CityWeatherDbModel
import com.justnik.weatherapp2.data.database.model.CityWeatherWithDailyWeather
import com.justnik.weatherapp2.data.database.model.DailyWeatherDbModel

@Dao
interface CityWeatherDao {
    @Transaction
    @Query("SELECT * FROM city_weather")
    fun getCityWeatherWithDailyWeatherList(): LiveData<List<CityWeatherWithDailyWeather>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCityWeather(CityWeather: CityWeatherDbModel)

    @Insert
    suspend fun insertDailyWeather(dailyWeatherList: List<DailyWeatherDbModel>)

    @Query("DELETE FROM city_weather WHERE city_name = :cityName")
    suspend fun deleteCityWeatherByName(cityName: String)

    @Query("DELETE FROM daily_weather WHERE parent_city_name = :cityName")
    suspend fun deleteDailyWeatherByName(cityName: String)
}