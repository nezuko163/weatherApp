package com.nezuko.domain.models.weatherapi

data class WeatherForecast(
    val location: Location,
    val currentWeather: CurrentWeather,
    val forecastDaily: List<DailyWeather>
)


