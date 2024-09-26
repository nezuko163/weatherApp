package com.nezuko.data.models.weatherapi

import com.nezuko.domain.models.weatherapi.Astro
import com.nezuko.domain.utils.time
import kotlinx.serialization.Serializable

@Serializable
data class AstroResponse(
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonset: String,
)


fun AstroResponse.toAstro() = Astro(
    sunrise = time(sunrise),
    sunset = time(sunset),
    moonrise = time(moonrise),
    moonset = time(moonset)
)