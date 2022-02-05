package com.justnik.weatherapp2.data.mappers

import android.content.Context
import android.location.Geocoder
import android.util.Log
import com.justnik.weatherapp2.R
import com.justnik.weatherapp2.data.database.model.CityWeatherDbModel
import com.justnik.weatherapp2.data.database.model.CityWeatherWithDailyWeather
import com.justnik.weatherapp2.data.database.model.DailyWeatherDbModel
import com.justnik.weatherapp2.data.network.model.WeatherInfoDto
import com.justnik.weatherapp2.domain.entities.CityWeather
import com.justnik.weatherapp2.domain.entities.DailyWeather
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class WeatherMapper(private val context: Context) {
    fun mapDtoToEntity(dto: WeatherInfoDto): CityWeather {

        return CityWeather(
            cityName = getCityNameFromCoordinates(dto.lat, dto.lon),
            currentTemp = getFormattedTemp(dto.current.temp),
            currentWeatherDescription = dto.current.weather[0].description.capitalize(),
            currentWeatherIconURL = getIconURLById(dto.current.weather[0].icon),
            dailyWeather = convertDailyDtoToDailyTemp(dto)
        )
    }

    fun mapDtoToCityWeatherWithDailyWeather(dto: WeatherInfoDto) {
        val cityWeatherDbModel = CityWeatherDbModel(
            cityName = getCityNameFromCoordinates(dto.lat, dto.lon),
            currentTemp = dto.current.temp.roundToInt(),
            currentWeatherDescription = dto.current.weather[0].description.capitalize(),
            currentWeatherIconURL = getIconURLById(dto.current.weather[0].icon)
        )
        val dailyWeatherDbModels: List<DailyWeatherDbModel> = mutableListOf()
    }

    private fun getFormattedTemp(temp: Double): String {
        val charDegree = context.resources.getString(R.string.degree)
        val tempInt = temp.roundToInt()

        return if (tempInt > 0) {
            "+${tempInt}${charDegree}C"
        } else {
            "${tempInt}${charDegree}C"
        }
    }

    //https://openweathermap.org/img/wn/{iconId}@2x.png - URL that contains icon
    private fun getIconURLById(iconId: String): String {
        return "https://openweathermap.org/img/wn/${iconId}@2x.png"
    }

    private fun convertDailyDtoToDailyTemp(dto: WeatherInfoDto): List<DailyWeather> {
        val dailyWeatherList = mutableListOf<DailyWeather>()
        val list = dto.daily
        for (i in list.indices) {
            val temp = list[i].temp
            val dailyTemp = DailyWeather(
                date = getFormattedDate(dto.daily[i].dt.toLong() * MILLIS),
                morningTemp = getFormattedTemp(temp.morn),
                dayTemp = getFormattedTemp(temp.day),
                eveningTemp = getFormattedTemp(temp.eve),
                nightTemp = getFormattedTemp(temp.night),
                dailyWeatherIconURL = getIconURLById(dto.daily[i].weather[0].icon)
            )
            dailyWeatherList.add(dailyTemp)
        }

        return dailyWeatherList
    }

    private fun getCityNameFromCoordinates(lat: Double, lon: Double): String {
        val geocoder = Geocoder(context)
        val addresses = geocoder.getFromLocation(lat, lon, 1)
        val cityName = addresses.first().locality
        return cityName
    }

    private fun getFormattedDate(stampInMillis: Long): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = stampInMillis
        val pattern = "EEEE, d MMMM"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        Log.d("getFormattedDate", "${cal.timeInMillis} ${cal.time} ${sdf.format(cal.time).capitalize()}")
        return sdf.format(cal.time).capitalize()
    }

    companion object{
        private const val MILLIS = 1000
    }
}