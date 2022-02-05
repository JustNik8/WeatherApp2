package com.justnik.weatherapp2.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.justnik.weatherapp2.data.network.model.WeatherInfoDto
import com.justnik.weatherapp2.data.repository.WeatherRepositoryImpl
import com.justnik.weatherapp2.domain.entities.CityWeather
import com.justnik.weatherapp2.domain.usecases.LoadCityUseCase

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = WeatherRepositoryImpl(application)
    private val loadCityUseCase = LoadCityUseCase(repository, application)

    fun insertWeatherByCityName(cityName: String){
        loadCityUseCase(cityName)
    }

    fun getCityWeatherList(): LiveData<List<CityWeather>> {
        return repository.getCityWeatherList()
    }
}