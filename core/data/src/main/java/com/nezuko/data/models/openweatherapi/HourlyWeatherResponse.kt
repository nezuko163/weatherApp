package com.nezuko.data.models.openweatherapi

import com.nezuko.domain.models.openweatherapi.HourlyWeather
import kotlinx.serialization.Serializable

@Serializable
data class HourlyWeatherResponse(
    val dt: Long,
    val temp: Float,
    val weather: List<WeatherDescriptionResponse>
)

fun HourlyWeatherResponse.toHourlyWeather() = HourlyWeather(
    dt, temp, weather.map { it -> it.toWeatherDescription() }
)
