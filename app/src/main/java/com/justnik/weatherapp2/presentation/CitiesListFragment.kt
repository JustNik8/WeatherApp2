package com.justnik.weatherapp2.presentation

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.justnik.weatherapp2.R
import com.justnik.weatherapp2.data.mappers.WeatherMapper
import com.justnik.weatherapp2.data.network.ApiFactory
import com.justnik.weatherapp2.databinding.FragmentCitiesListBinding
import com.justnik.weatherapp2.domain.CityWeather
import com.justnik.weatherapp2.presentation.adapters.citylist.CityListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CitiesListFragment : Fragment() {

    private var _binding: FragmentCitiesListBinding? = null
    private val binding
        get() = _binding!!

    private val scope = CoroutineScope(Dispatchers.Main)
    private val rvAdapter: CityListAdapter by lazy {
        CityListAdapter(requireContext())
    }

    var onCityClickListener: OnCityClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitiesListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupCityRecyclerView()
        setupToolbar()
    }

    private fun setupCityRecyclerView() {
        binding.rvCityList.adapter = rvAdapter

        rvAdapter.onCityItemClickListener = {
            onCityClickListener?.onCityClick(it)
        }
    }

    private fun loadCity(cityName: String) {
        val geocoder = Geocoder(requireContext())
        val addresses = geocoder.getFromLocationName(cityName, 1)

        val lat = addresses.first().latitude
        val lon = addresses.first().longitude

        scope.launch {
            val weatherInfoDto = ApiFactory.apiService.getWeatherByCoordinates(lat, lon)
            val cityWeather = WeatherMapper(requireContext()).mapDtoToEntity(weatherInfoDto)
            rvAdapter.submitList(mutableListOf(cityWeather))

            Log.d("cityWeather", cityWeather.toString())
        }
    }

    private fun setupToolbar(){
        val menuItem = binding.mainToolbar.menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        searchView.queryHint = "Type city here"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let {
                    menuItem.collapseActionView()
                    loadCity(it)
                    return true
                }
                menuItem.collapseActionView()
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
    }

    interface OnCityClickListener{
        fun onCityClick(cityWeather: CityWeather)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(): CitiesListFragment {
            return CitiesListFragment()
        }
    }
}