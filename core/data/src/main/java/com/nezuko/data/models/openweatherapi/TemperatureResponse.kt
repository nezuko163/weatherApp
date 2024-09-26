package com.nezuko.data.models.openweatherapi

import com.nezuko.domain.models.openweatherapi.Temperature
import kotlinx.serialization.Serializable

@Serializable
data class TemperatureResponse(
    val day: Float
)

fun TemperatureResponse.toTemperature() = Temperature(day)