package com.justnik.weatherapp2.presentation.adapters.citylist

import androidx.recyclerview.widget.DiffUtil
import com.justnik.weatherapp2.domain.CityWeather

object CityWeatherDiffUtil : DiffUtil.ItemCallback<CityWeather>() {
    override fun areItemsTheSame(oldItem: CityWeather, newItem: CityWeather): Boolean {
        return oldItem.cityName == newItem.cityName
    }

    override fun areContentsTheSame(oldItem: CityWeather, newItem: CityWeather): Boolean {
        return oldItem == newItem
    }
}