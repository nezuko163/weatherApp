package com.nezuko.data.models.weatherapi

import com.nezuko.domain.models.weatherapi.HourlyWeather
import com.nezuko.domain.utils.dayWithTime
import kotlinx.serialization.Serializable

@Serializable
data class HourlyWeatherResponse(
    val time: String,
    val temp_c: Double,
    val condition: ConditionResponse,
    val cloud: Int,
    val feelslike_c: Double,
    val humidity: Int,
    val wind_kph: Float
)

fun HourlyWeatherResponse.toHourlyWeather() = HourlyWeather(
    time = dayWithTime(time),
    temp_c,
    condition.toCondition(),
    cloud,
    feelslike_c,
    humidity,
    wind_kph = wind_kph
)