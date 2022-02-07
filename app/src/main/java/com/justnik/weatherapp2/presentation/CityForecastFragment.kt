package com.justnik.weatherapp2.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.justnik.weatherapp2.R
import com.justnik.weatherapp2.databinding.FragmentCityForecastBinding
import com.justnik.weatherapp2.domain.entities.CityWeather
import com.justnik.weatherapp2.presentation.adapters.dailyforecast.DailyForecastAdapter

class CityForecastFragment : Fragment() {

    private var _binding: FragmentCityForecastBinding? = null
    private val binding
        get() = _binding!!

    var onDeleteCityListener: ((String) -> Unit)? = null

    private lateinit var cityWeather: CityWeather
    private val dailyAdapter: DailyForecastAdapter by lazy {
        DailyForecastAdapter(requireContext())
    }

    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(this)[WeatherViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cityWeather = arguments?.getParcelable(CITY_WEATHER_KEY) ?: throw RuntimeException("null")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityForecastBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupDailyForecastRV()
        setUpToolBar()

        binding.cityWeather = cityWeather
        Glide.with(requireContext()).load(cityWeather.currentWeatherIconURL)
            .into(binding.ivCurrentWeather)
    }

    private fun setupDailyForecastRV() {
        val rvDailyForecast = binding.rvDailyForecast
        rvDailyForecast.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        rvDailyForecast.adapter = dailyAdapter
        dailyAdapter.submitList(cityWeather.dailyWeather)
    }

    private fun setUpToolBar() {
        val deleteCityItem = binding.toolbarForecastCity.menu.findItem(R.id.action_delete_city)
        deleteCityItem.setOnMenuItemClickListener {
            onDeleteCityListener?.invoke(cityWeather.cityName)
            true
        }

        val locateCity = binding.toolbarForecastCity.menu.findItem(R.id.action_locate_city)
        locateCity.setOnMenuItemClickListener {
            val intent = viewModel.locateCityIntent(cityWeather)
            startActivity(intent)

            true
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(cityWeather: CityWeather): CityForecastFragment {
            return CityForecastFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(CITY_WEATHER_KEY, cityWeather)
                }
            }
        }

        private const val CITY_WEATHER_KEY = "city_weather"
    }
}