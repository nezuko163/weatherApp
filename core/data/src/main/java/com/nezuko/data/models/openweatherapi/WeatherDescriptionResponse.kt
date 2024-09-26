package com.nezuko.data.models.openweatherapi

import com.nezuko.domain.models.openweatherapi.WeatherDescription
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDescriptionResponse(
    val description: String
)

public fun WeatherDescriptionResponse.toWeatherDescription() = WeatherDescription(
    description
)