package com.justnik.weatherapp2.presentation.adapters.dailyforecast

import androidx.recyclerview.widget.DiffUtil
import com.justnik.weatherapp2.domain.entities.DailyWeather

object DailyWeatherDiffUtil : DiffUtil.ItemCallback<DailyWeather>() {
    override fun areItemsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean {
        return oldItem == newItem
    }
}