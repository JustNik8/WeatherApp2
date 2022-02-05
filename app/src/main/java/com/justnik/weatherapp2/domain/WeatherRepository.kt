package com.justnik.weatherapp2.domain

import androidx.lifecycle.LiveData
import com.justnik.weatherapp2.data.database.model.CityWeatherWithDailyWeather
import com.justnik.weatherapp2.data.network.model.WeatherInfoDto
import com.justnik.weatherapp2.domain.entities.CityWeather

interface WeatherRepository {

    fun insertCityWeather(dto: WeatherInfoDto)

    fun getCityWeatherList(): LiveData<List<CityWeather>>
}