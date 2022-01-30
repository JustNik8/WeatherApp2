package com.justnik.weatherapp2.presentation.adapters.citylist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.justnik.weatherapp2.databinding.ItemCityBinding
import com.justnik.weatherapp2.domain.CityWeather

class CityListAdapter(private val context: Context) :
    ListAdapter<CityWeather, CityViewHolder>(CityWeatherDiffUtil) {

    var onCityItemClickListener: ((CityWeather) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val cityWeather = getItem(position)
        with(holder.binding) {
            tvItemCityName.text = cityWeather.cityName
            tvItemCurrentTemp.text = cityWeather.currentTemp
            Glide.with(context).load(cityWeather.currentWeatherIconURL).into(ivItemCurrentWeather)

            root.setOnClickListener {
                onCityItemClickListener?.invoke(cityWeather)
            }
        }
    }


}