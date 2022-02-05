package com.justnik.weatherapp2.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.justnik.weatherapp2.data.database.AppDatabase
import com.justnik.weatherapp2.data.database.model.CityWeatherWithDailyWeather
import com.justnik.weatherapp2.domain.WeatherRepository

class WeatherRepositoryImpl(
    private val application: Application
) : WeatherRepository {

    private val cityWeatherDao = AppDatabase.getInstance(application).cityWeatherDao()

    override fun insertCityWeatherWithDailyWeather(cityWeatherWithDailyWeather: CityWeatherWithDailyWeather) {
        TODO("Not yet implemented")
    }

    override fun getCityWeatherWithDailyWeatherList(): LiveData<CityWeatherWithDailyWeather> {
        TODO("Not yet implemented")
    }

}