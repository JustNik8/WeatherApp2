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
import androidx.lifecycle.ViewModelProvider
import com.justnik.weatherapp2.R
import com.justnik.weatherapp2.data.mappers.WeatherMapper
import com.justnik.weatherapp2.data.network.ApiFactory
import com.justnik.weatherapp2.databinding.FragmentCitiesListBinding
import com.justnik.weatherapp2.domain.entities.CityWeather
import com.justnik.weatherapp2.domain.entities.DailyWeather
import com.justnik.weatherapp2.presentation.adapters.citylist.CityListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CitiesListFragment : Fragment() {

    private var _binding: FragmentCitiesListBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(this)[WeatherViewModel::class.java]
    }

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
        observeViewModel()
    }

    private fun setupCityRecyclerView() {
        binding.rvCityList.adapter = rvAdapter

        rvAdapter.onCityItemClickListener = {
            onCityClickListener?.onCityClick(it)
        }
            }

    private fun observeViewModel(){
        viewModel.getCityWeatherList().observe(viewLifecycleOwner) {
            rvAdapter.submitList(it)
        }
    }


    private fun setupToolbar(){
        val menuItem = binding.mainToolbar.menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        searchView.queryHint = "Type city here"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let {
                    viewModel.insertWeatherByCityName(p0, rvAdapter.currentList)
                    menuItem.collapseActionView()
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