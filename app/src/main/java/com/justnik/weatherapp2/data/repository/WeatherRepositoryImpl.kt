package com.justnik.weatherapp2.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.justnik.weatherapp2.data.database.AppDatabase
import com.justnik.weatherapp2.data.mappers.WeatherMapper
import com.justnik.weatherapp2.data.network.model.WeatherInfoDto
import com.justnik.weatherapp2.domain.WeatherRepository
import com.justnik.weatherapp2.domain.entities.CityWeather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class WeatherRepositoryImpl(application: Application) : WeatherRepository {

    private val cityWeatherDao = AppDatabase.getInstance(application).cityWeatherDao()
    private val mapper = WeatherMapper(application)

    override suspend fun insertCityWeather(dto: WeatherInfoDto) {
        val cityWeatherWithDailyWeather = mapper.mapDtoToCityWeatherWithDailyWeather(dto)
        cityWeatherDao.insertCityWeather(cityWeatherWithDailyWeather.cityWeather)
        cityWeatherDao.insertDailyWeather(cityWeatherWithDailyWeather.dailyWeather)
    }

    override fun getCityWeatherList(): LiveData<List<CityWeather>> {
        return Transformations.map(cityWeatherDao.getCityWeatherWithDailyWeatherList()) {
            it.map { elem ->
                mapper.mapCityWeatherWithDailyWeatherToCityWeather(elem)
            }
        }
    }

    override suspend fun deleteCityWeather(cityName: String) {
        cityWeatherDao.deleteCityWeatherByName(cityName)
        cityWeatherDao.deleteDailyWeatherByName(cityName)

    }
}