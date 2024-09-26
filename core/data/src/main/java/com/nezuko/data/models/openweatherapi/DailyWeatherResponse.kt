package com.nezuko.data.models.openweatherapi

import com.nezuko.domain.models.openweatherapi.DailyWeather
import kotlinx.serialization.Serializable

@Serializable
data class DailyWeatherResponse(
    val dt: Long,
    val temperature: TemperatureResponse,
    val weather: List<WeatherDescriptionResponse>
)

fun DailyWeatherResponse.toDailyWeather() = DailyWeather(
    dt, temperature.toTemperature(), weather.map { it -> it.toWeatherDescription() }
)