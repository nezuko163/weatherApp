package com.nezuko.domain.models.openweatherapi

data class CurrentWeather(
    val temp: Float,
    val weather: List<WeatherDescription>
)
