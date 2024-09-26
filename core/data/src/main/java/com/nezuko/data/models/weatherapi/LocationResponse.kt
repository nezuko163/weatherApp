package com.nezuko.data.models.weatherapi

import com.nezuko.domain.models.weatherapi.Location
import kotlinx.serialization.Serializable


@Serializable
data class LocationResponse(
    val name: String,
    val lat: Double,
    val lon: Double
)


fun LocationResponse.toLocation() = Location(
    name, lat, lon
)