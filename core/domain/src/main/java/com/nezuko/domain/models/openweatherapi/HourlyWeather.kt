package com.nezuko.domain.models.openweatherapi

data class HourlyWeather(
    val dt: Long,
    val temp: Float,
    val weather: List<WeatherDescription>
)