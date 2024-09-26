package com.nezuko.weatherapp.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition


@Composable
fun WeatherNavHost(
    startDestination: Screen = HomeScreen(),
) {
    Navigator(screen = HomeScreen()) { navigator ->
        SlideTransition(navigator = navigator)
    }

}