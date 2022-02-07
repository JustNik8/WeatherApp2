package com.justnik.weatherapp2.domain.usecases

import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.core.content.contentValuesOf
import com.justnik.weatherapp2.data.database.AppDatabase
import com.justnik.weatherapp2.data.mappers.WeatherMapper
import com.justnik.weatherapp2.data.network.ApiFactory
import com.justnik.weatherapp2.data.network.model.WeatherInfoDto
import com.justnik.weatherapp2.domain.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoadCityUseCase(private val repository: WeatherRepository, private val context: Context) {

    operator fun invoke(cityName: String){
        val scope = CoroutineScope(Dispatchers.Default)

        val geocoder = Geocoder(context)
        val address = geocoder.getFromLocationName(cityName, 1)[0]

        val latitude = address.latitude
        val longitude = address.longitude

        scope.launch {
            val weatherInfoDto = ApiFactory.apiService.getWeatherByCoordinates(latitude, longitude)
            repository.insertCityWeather(weatherInfoDto)
        }
    }

}