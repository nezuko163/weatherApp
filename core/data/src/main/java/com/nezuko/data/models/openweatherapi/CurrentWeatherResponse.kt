package com.nezuko.data.models.openweatherapi

import com.nezuko.domain.models.openweatherapi.CurrentWeather
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherResponse(
    val temp: Float,
    val weather: List<WeatherDescriptionResponse>
)

fun CurrentWeatherResponse.toCurrentWeather() = CurrentWeather(
    temp, weather.map { it.toWeatherDescription() }
)