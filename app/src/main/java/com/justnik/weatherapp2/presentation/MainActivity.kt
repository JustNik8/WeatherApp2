package com.justnik.weatherapp2.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.justnik.weatherapp2.R
import com.justnik.weatherapp2.domain.entities.CityWeather

class MainActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(this)[WeatherViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launchCitiesListFragment()
    }

    private fun observeCityItemClick(fragment: CitiesListFragment){
        fragment.onCityItemClickListener = object : CitiesListFragment.OnCityItemClickListener{
            override fun onCityClick(cityWeather: CityWeather) {
                launchCityForecastFragment(cityWeather)
            }
        }
    }

    private fun launchCitiesListFragment(){
        val fragment = CitiesListFragment.newInstance()
        observeCityItemClick(fragment)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in, //enter
                R.anim.fade_out, //exit
                R.anim.fade_in, //popEnter
                R.anim.slide_out //popExit
            )
            .replace(R.id.main_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun launchCityForecastFragment(cityWeather: CityWeather){
        val fragment = CityForecastFragment.newInstance(cityWeather)

        //observe delete city
        fragment.onDeleteCityListener = {
            launchCitiesListFragment()
            viewModel.deleteCityWeatherByName(it)
        }

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in, //enter
                R.anim.fade_out, //exit
                R.anim.fade_in, //popEnter
                R.anim.slide_out //popExit
            )
            .replace(R.id.main_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}