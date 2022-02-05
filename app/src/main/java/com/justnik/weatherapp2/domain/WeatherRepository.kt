package com.justnik.weatherapp2.domain

import androidx.lifecycle.LiveData
import com.justnik.weatherapp2.data.database.model.CityWeatherWithDailyWeather

interface WeatherRepository {

    fun insertCityWeatherWithDailyWeather(cityWeatherWithDailyWeather: CityWeatherWithDailyWeather)

    fun getCityWeatherWithDailyWeatherList(): LiveData<CityWeatherWithDailyWeather>
}