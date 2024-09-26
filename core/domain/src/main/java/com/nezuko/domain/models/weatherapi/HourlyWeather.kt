package com.nezuko.domain.models.weatherapi

import java.time.LocalDateTime

data class HourlyWeather(
    val time: LocalDateTime,
    val temp_c: Double,
    val condition: Condition,
    val cloud: Int,
    val feelslike_c: Double,
    val humidity: Int,
    val wind_kph: Float
)
