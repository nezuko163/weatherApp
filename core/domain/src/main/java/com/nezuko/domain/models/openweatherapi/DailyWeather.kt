package com.nezuko.domain.models.openweatherapi

data class DailyWeather(
    val dt: Long,
    val temperature: Temperature,
    val weather: List<WeatherDescription>
)