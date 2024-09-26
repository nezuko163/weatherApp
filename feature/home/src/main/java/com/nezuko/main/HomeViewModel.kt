package com.nezuko.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import com.nezuko.domain.models.ResultModel
import com.nezuko.domain.models.weatherapi.WeatherForecast
import com.nezuko.domain.repository.CityRepository
import com.nezuko.domain.repository.LocalStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cityRepository: CityRepository,
    private val localStoreRepository: LocalStoreRepository,
) : ViewModel() {

    private val TAG = "HomeViewModel"

    private val _currentCity = MutableStateFlow("")
    val currentCity: StateFlow<String>
        get() = _currentCity

    fun chooseCity(city: String) {
        _currentCity.value = city
        if (!_citiesWeatherForecast.value.keys.contains(city)) return
        Log.i(TAG, "chooseCity: $city")
        _currentCityWeatherForecast.value = _citiesWeatherForecast.value[city]!!
    }

    private val _cities = MutableStateFlow<ArrayList<String>>(arrayListOf())
    val cities: StateFlow<List<String>>
        get() = _cities

    fun loadCities() {
        val list = localStoreRepository.getCities()
        if (list.isEmpty()) return

        _cities.value.addAll(list)
    }

    fun appendCity(city: String) {
        if (_cities.value.contains(city)) return


        viewModelScope.launch {
            cityRepository.weatherFor7Days(city)
                .flowOn(Dispatchers.IO)
                .collect {
                    if (it.status == ResultModel.Status.SUCCESS && it.data != null) {
                        _citiesWeatherForecast.value = HashMap(_citiesWeatherForecast.value).apply {
                            this[it.data!!.location.name] = it
                        }
                        _currentCityWeatherForecast.value = it
                        Log.i(TAG, "appendCity: $city")
                        _cities.value.add(it.data!!.location.name)
                        localStoreRepository.addCity(it.data!!.location.name)
                    }
                }
        }
    }


    private val _currentCityWeatherForecast =
        MutableStateFlow<ResultModel<WeatherForecast>>(ResultModel.none())
    val currentCityWeatherForecast: StateFlow<ResultModel<WeatherForecast>>
        get() = _currentCityWeatherForecast

    fun loadCurrentCityWeatherForecast() {
        if (currentCity.value.isEmpty()) return

        viewModelScope.launch {
            cityRepository.weatherFor7Days(currentCity.value)
                .flowOn(Dispatchers.IO)
                .collect {
                    Log.i(TAG, "loadCurrentCityWeatherForecast: $it")
                    _currentCityWeatherForecast.value = it
                }
        }
    }

    private val _citiesWeatherForecast =
        MutableStateFlow<HashMap<String, ResultModel<WeatherForecast>>>(hashMapOf())

    val citiesWeatherForecast: StateFlow<HashMap<String, ResultModel<WeatherForecast>>>
        get() = _citiesWeatherForecast

    fun loadCitiesWeatherForecast() {
        _currentCityWeatherForecast.value = ResultModel.loading()
        loadCities()
        if (cities.value.isEmpty()) {
            _currentCityWeatherForecast.value = ResultModel.none()
            return
        }

        val hashMap = HashMap<String, ResultModel<WeatherForecast>>()

        cities.value.forEach { city ->
            viewModelScope.launch {
                cityRepository.weatherFor7Days(city)
                    .flowOn(Dispatchers.IO)
                    .collect {
                        
                        if (hashMap.size == 0 && it.data != null && it.status == ResultModel.Status.SUCCESS) {
                            Log.i(
                                TAG,
                                "loadCitiesWeatherForecast: city = $city status = ${it.status} data = ${it.data}"
                            )
                            _currentCityWeatherForecast.value = it
                        }
                        if (it.data != null && it.status == ResultModel.Status.SUCCESS) {
                            hashMap[city] = it
                            if (hashMap.size == cities.value.size) {
                                _citiesWeatherForecast.value = hashMap
                            }
                        }
                    }
            }
        }
        if (hashMap.size == 0) {
            _currentCityWeatherForecast.value = ResultModel.none()
        }
        Log.i(TAG, "loadCitiesWeatherForecast: $hashMap")
        _citiesWeatherForecast.value = hashMap
    }

    fun removeSity(city: String) {
        if (_cities.value.contains(city)) _cities.value.remove(city)
        if (_citiesWeatherForecast.value.contains(city)) _citiesWeatherForecast.value.remove(city)
        if (_currentCity.value.uppercase() == city.uppercase()) _currentCity.value = ""

        _currentCityWeatherForecast.value.data?.let {
            if (it.location.name.uppercase() == city.uppercase()) {
                _currentCityWeatherForecast.value = ResultModel.none()
            }
        }

        localStoreRepository.clearCity(city)
    }

    fun clearCities() {
        _cities.value = arrayListOf()
        Log.i(TAG, "clearCities: ${citiesWeatherForecast.value.isEmpty()}")
        _citiesWeatherForecast.value = HashMap()
        Log.i(TAG, "clearCities: ${citiesWeatherForecast.value.isEmpty()}")
        _currentCity.value = ""
        _currentCityWeatherForecast.value = ResultModel.none()

        localStoreRepository.clearCities()
    }
    
    suspend fun collect() {
        currentCityWeatherForecast.collect {
            Log.i(TAG, "collect: changed")
        }
    }
}

object StoreOwner : ViewModelStoreOwner {
    private val a = ViewModelStore()
    override val viewModelStore: ViewModelStore
        get() = a

}