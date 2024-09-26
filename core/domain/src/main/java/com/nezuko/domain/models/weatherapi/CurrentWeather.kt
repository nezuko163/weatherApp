package com.nezuko.domain.models.weatherapi

data class CurrentWeather(
    val temp_c: Double,
    val feelslike_c: Double,
    val cloud: Int,
    val condition: Condition,

    // влажность
    val humidity: Int,
    val wind_kph: Float
)

data class Condition(
    val text: String,
    val icon: String
)

