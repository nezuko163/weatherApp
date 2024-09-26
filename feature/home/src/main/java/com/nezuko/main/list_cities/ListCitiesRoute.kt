package com.nezuko.main.list_cities

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.nezuko.domain.models.ResultModel
import com.nezuko.domain.models.weatherapi.WeatherForecast
import com.nezuko.main.HomeViewModel
import com.nezuko.main.StoreOwner
import kotlinx.coroutines.handleCoroutineException

internal const val TAG = "LIST_CITIES_ROUTE"


@Composable
fun ListCitiesRoute(
    viewModel: HomeViewModel,
    onNavigateBack: () -> Unit = {}
) {
    val citiesWeatherForecast by viewModel.citiesWeatherForecast.collectAsState()

    Log.i(TAG, "ListCitiesRoute: $citiesWeatherForecast")

//    Log.i(TAG, "ListCitiesRoute: ${citiesWeatherForecast.keys}")
    ListCitiesScreen(
        onNavigateBack = onNavigateBack,
        onCityInputEnd = {
            Log.i(TAG, "ListCitiesRoute: OnCityInputEnd")
            viewModel.appendCity(it.trim())
        },
        onCityChosen = {
            Log.i(TAG, "ListCitiesRoute: onCityChosen")
            viewModel.chooseCity(it)
            onNavigateBack()
        },
        onCitiesClearClick = {
            viewModel.clearCities()
        },
        cities = citiesWeatherForecast
    )


}

