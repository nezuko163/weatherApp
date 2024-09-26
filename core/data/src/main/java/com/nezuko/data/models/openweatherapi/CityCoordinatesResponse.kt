package com.nezuko.data.models.openweatherapi

import com.nezuko.domain.models.openweatherapi.Coordinates
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityCoordinatesResponse(val coord: CoordinatesResponse)

@Serializable
data class CoordinatesResponse(
    @SerialName("lon")
    val lon: Float,
    @SerialName("lat")
    val lat: Float
)

fun CoordinatesResponse.toCoordinates() = Coordinates(lat, lon)