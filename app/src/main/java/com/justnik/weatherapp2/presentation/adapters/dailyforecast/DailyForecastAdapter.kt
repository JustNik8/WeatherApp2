package com.justnik.weatherapp2.presentation.adapters.dailyforecast

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.justnik.weatherapp2.R
import com.justnik.weatherapp2.databinding.ItemDailyForecastBinding
import com.justnik.weatherapp2.domain.DailyWeather

class DailyForecastAdapter(private val context: Context) :
    ListAdapter<DailyWeather, DailyForecastViewHolder>(DailyWeatherDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val binding = ItemDailyForecastBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )

        return DailyForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        val dailyWeather = getItem(position)
        val tempTemplate = context.resources.getString(R.string.temp_part_of_day_template)
        with(holder.binding){
            tvDate.text = dailyWeather.date
            tvTempMorning.text = String.format(tempTemplate, "Morning", dailyWeather.morningTemp)
            tvTempDay.text = String.format(tempTemplate, "Day", dailyWeather.dayTemp)
            tvTempEvening.text = String.format(tempTemplate, "Evening", dailyWeather.eveningTemp)
            tvTempNight.text = String.format(tempTemplate, "Night", dailyWeather.nightTemp)
            Glide.with(context).load(dailyWeather.dailyWeatherIconURL).into(ivDailyWeather)
        }
    }
}