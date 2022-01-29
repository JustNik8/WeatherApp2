package com.justnik.weatherapp2.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.justnik.weatherapp2.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launchCitiesListFragment()
    }

    private fun launchCitiesListFragment(){
        val fragment = CitiesListFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }

}