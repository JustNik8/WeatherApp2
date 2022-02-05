package com.justnik.weatherapp2.domain.usecases

import android.content.Context
import android.location.Geocoder
import android.util.Log
import com.justnik.weatherapp2.data.mappers.WeatherMapper
import com.justnik.weatherapp2.data.network.ApiFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoadCityUseCase {
    val scope = CoroutineScope(Dispatchers.Default)
    operator fun invoke(context: Context, cityName: String){
        val geocoder = Geocoder(context)
        val addresses = geocoder.getFromLocationName(cityName, 1)

        val lat = addresses.first().latitude
        val lon = addresses.first().longitude

        scope.launch {
            val weatherInfoDto = ApiFactory.apiService.getWeatherByCoordinates(lat, lon)
            val cityWeather = WeatherMapper(context).mapDtoToEntity(weatherInfoDto)
            Log.d("cityWeather", cityWeather.toString())

            TODO("load city to database")

        }
    }
}