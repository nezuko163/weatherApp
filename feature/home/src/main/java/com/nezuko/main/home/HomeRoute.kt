package com.nezuko.main.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.nezuko.main.HomeViewModel
import com.nezuko.main.StoreOwner

internal const val TAG = "HOME_ROUTE"

@Composable
fun HomeRoute(
    viewModel: HomeViewModel,
    onListCitiesNavigate: () -> Unit = {}
) {

    val currentWeather by viewModel.currentCityWeatherForecast.collectAsState()
    Log.i(TAG, "HomeRoute: ${currentWeather.data?.forecastDaily?.size}")

    var isFirstLoading by rememberSaveable { mutableStateOf(true) }
    
    LaunchedEffect(Unit) {
        if (isFirstLoading) {
//            viewModel.collect()
            viewModel.loadCitiesWeatherForecast()
            Log.i(TAG, "HomeRoute: load data")
            isFirstLoading = false
        }
    }

//    Log.i(TAG, "HomeRoute: ${weather.data}")
//    Log.i(TAG, "HomeRoute: ${weather.message}")

    HomeScreen(
        weatherForecast = currentWeather,
        city = currentWeather.data?.location?.name ?: "Выберите город",
        onListCitiesNavigate = onListCitiesNavigate,
        onSettingsNavigate = { TODO() }
    )
}