package com.justnik.weatherapp2.data.network

import com.justnik.weatherapp2.API_KEY
import com.justnik.weatherapp2.data.network.model.WeatherInfoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data/2.5/onecall")
    suspend fun getWeatherByCoordinates(
    @Query(QUERY_PARAM_LAT) latitude: Double,
    @Query(QUERY_PARAM_LON) longitude: Double,
    @Query(QUERY_PARAM_EXCLUDE) excludedParams: String = "alerts,minutely,hourly",
    @Query(QUERY_PARAM_UNITS) units: String = "metric",
    @Query(QUERY_PARAM_LANG) language: String = "ru",
    @Query(QUERY_PARAM_APPID) appid: String = API_KEY
    ): WeatherInfoDto

    companion object{
        private const val QUERY_PARAM_LAT = "lat"
        private const val QUERY_PARAM_LON = "lon"
        private const val QUERY_PARAM_APPID = "appid"
        private const val QUERY_PARAM_EXCLUDE = "exclude"
        private const val QUERY_PARAM_UNITS = "units"
        private const val QUERY_PARAM_LANG = "lang"

    }
}