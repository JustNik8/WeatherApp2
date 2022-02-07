package com.justnik.weatherapp2.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.justnik.weatherapp2.R
import com.justnik.weatherapp2.domain.entities.CityWeather

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launchCitiesListFragment()

    }

    private fun observeCityItemClick(fragment: CitiesListFragment){
        fragment.onCityClickListener = object : CitiesListFragment.OnCityClickListener{
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