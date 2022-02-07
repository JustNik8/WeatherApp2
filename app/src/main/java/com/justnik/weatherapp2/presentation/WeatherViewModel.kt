package com.justnik.weatherapp2.presentation

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import com.justnik.weatherapp2.data.database.model.CityWeatherMainInfoTuple
import com.justnik.weatherapp2.data.repository.WeatherRepositoryImpl
import com.justnik.weatherapp2.domain.entities.CityWeather
import com.justnik.weatherapp2.domain.usecases.CheckInternetAccessUseCase
import com.justnik.weatherapp2.domain.usecases.LoadCityUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = WeatherRepositoryImpl(application)
    private val scope = CoroutineScope(Dispatchers.Default)

    private val loadCityUseCase = LoadCityUseCase(repository, application)
    private val checkInternetAccessUseCase = CheckInternetAccessUseCase(application)

    fun insertWeatherByCityName(cityName: String, currentCityList: List<CityWeather>){
        val isInternetAvailable = checkInternetAccessUseCase.isInternetAvailable()
        if (isInternetAvailable) {
            scope.launch {
                val cityNames = currentCityList.map { it.cityName }
                if (cityNames.contains(cityName)) {
                    repository.deleteCityWeather(cityName)
                }
                loadCityUseCase(cityName)
            }
        }
        else{
            Toast.makeText(getApplication(), "Check your internet access", Toast.LENGTH_SHORT).show()
        }
    }

    fun getCityWeatherList(): LiveData<List<CityWeather>> {
        return repository.getCityWeatherList()
    }

    fun deleteCityWeatherByName(cityName: String) {
        scope.launch {
            repository.deleteCityWeather(cityName)
        }
    }

}