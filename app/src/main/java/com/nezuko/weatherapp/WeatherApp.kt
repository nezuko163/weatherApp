package com.nezuko.weatherapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.nezuko.weatherapp.navigation.WeatherNavHost

@Composable
fun WeatherApp(modifier: Modifier = Modifier) {
    WeatherNavHost()
}