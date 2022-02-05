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

    fun mapDtoToCityWeatherWithDailyWeather(dto: WeatherInfoDto): CityWeatherWithDailyWeather {
        val cityWeatherDbModel = CityWeatherDbModel(
            cityName = getCityNameFromCoordinates(dto.lat, dto.lon),
            currentTemp = dto.current.temp.roundToInt(),
            currentWeatherDescription = dto.current.weather[0].description.capitalize(),
            currentWeatherIconURL = getIconURLById(dto.current.weather[0].icon)
        )
        val dailyWeatherDbModels: List<DailyWeatherDbModel> =
            convertDailyDtoToDailyWeatherDbModel(dto)

        return CityWeatherWithDailyWeather(
            cityWeather = cityWeatherDbModel,
            dailyWeather = dailyWeatherDbModels
        )
    }

    fun mapCityWeatherWithDailyWeatherToCityWeather(
        cityWeatherWithDailyWeather: CityWeatherWithDailyWeather
    ): CityWeather {
        val cityWeather = cityWeatherWithDailyWeather.cityWeather

        val dailyWeatherList =
            convertDailyWeatherDbModelToDailyWeather(cityWeatherWithDailyWeather.dailyWeather)

        return CityWeather(
            cityName = cityWeather.cityName,
            currentTemp = getFormattedTemp(cityWeather.currentTemp),
            currentWeatherDescription = cityWeather.currentWeatherDescription,
            currentWeatherIconURL = cityWeather.currentWeatherIconURL,
            dailyWeather = dailyWeatherList
        )
    }

    private fun convertDailyDtoToDailyWeatherDbModel(dto: WeatherInfoDto): List<DailyWeatherDbModel> {
        val dailyWeatherDbModels = mutableListOf<DailyWeatherDbModel>()
        val dtoList = dto.daily
        val cityName = getCityNameFromCoordinates(dto.lat, dto.lon)

        for (i in dtoList.indices) {
            val dtoTemp = dtoList[i].temp
            val dailyWeatherDbModel = DailyWeatherDbModel(
                id = 0,
                parentCityName = cityName,
                date = dto.daily[i].dt.toLong() * MILLIS,
                morningTemp = dtoTemp.morn.roundToInt(),
                dayTemp = dtoTemp.day.roundToInt(),
                eveningTemp = dtoTemp.eve.roundToInt(),
                nightTemp = dtoTemp.night.roundToInt(),
                dailyWeatherIconURL = getIconURLById(dto.daily[i].weather[0].icon)
            )
            dailyWeatherDbModels.add(dailyWeatherDbModel)
        }

        return dailyWeatherDbModels
    }


    private fun convertDailyWeatherDbModelToDailyWeather(
        dailyWeatherDbModelList: List<DailyWeatherDbModel>
    ): List<DailyWeather> {

        val dailyWeatherList = mutableListOf<DailyWeather>()

        for (i in dailyWeatherDbModelList.indices) {
            val dbModel = dailyWeatherDbModelList[i]
            val dailyWeather = DailyWeather(
                date = getFormattedDate(dbModel.date),
                morningTemp = getFormattedTemp(dbModel.morningTemp),
                dayTemp = getFormattedTemp(dbModel.dayTemp),
                eveningTemp = getFormattedTemp(dbModel.eveningTemp),
                nightTemp = getFormattedTemp(dbModel.nightTemp),
                dailyWeatherIconURL = getIconURLById(dbModel.dailyWeatherIconURL)
            )
            dailyWeatherList.add(dailyWeather)
        }

        return dailyWeatherList
    }

    private fun getFormattedTemp(temp: Int): String {
        val charDegree = context.resources.getString(R.string.degree)

        return if (temp > 0) {
            "+${temp}${charDegree}C"
        } else {
            "${temp}${charDegree}C"
        }
    }

    //https://openweathermap.org/img/wn/{iconId}@2x.png - URL that contains icon
    private fun getIconURLById(iconId: String): String {
        return "https://openweathermap.org/img/wn/${iconId}@2x.png"
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
        Log.d(
            "getFormattedDate",
            "${cal.timeInMillis} ${cal.time} ${sdf.format(cal.time).capitalize()}"
        )
        return sdf.format(cal.time).capitalize()
    }

    companion object {
        private const val MILLIS = 1000
    }
}