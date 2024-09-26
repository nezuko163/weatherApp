package com.nezuko.data.models.openweatherapi

import com.nezuko.domain.models.openweatherapi.Weather
import kotlinx.serialization.Serializable

@Serializable
data class WeatherReponse(
    val current: CurrentWeatherResponse,
    val hourly: List<HourlyWeatherResponse>,
    val daily: List<DailyWeatherResponse>
)

fun WeatherReponse.toWeather() = Weather(
    current =  current.toCurrentWeather(),
    hourly = hourly.map { it.toHourlyWeather() },
    daily = daily.map { it.toDailyWeather() }
)