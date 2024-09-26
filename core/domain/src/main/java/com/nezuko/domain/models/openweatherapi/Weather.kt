package com.nezuko.domain.models.openweatherapi


data class Weather(
    val current: CurrentWeather,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>
)