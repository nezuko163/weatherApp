package com.nezuko.data.models.weatherapi

import com.nezuko.domain.models.weatherapi.DailyWeatherStat
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.max
import kotlin.math.min


@Serializable
data class DailyWeatherStatResponse(
    val maxtemp_c: Double,
    val mintemp_c: Double,
    val avgtemp_c: Double,
    val condition: ConditionResponse,
    val totalprecip_mm: Float,

    @SerialName("daily_chance_of_rain")
    val chance_of_rain: Int,

    @SerialName("daily_chance_of_snow")
    val chance_of_snow: Int,

    )


fun DailyWeatherStatResponse.toDailyWeatherStat() = DailyWeatherStat(
    maxtemp_c,
    mintemp_c,
    avgtemp_c,
    condition.toCondition(),
    totalprecip_mm = totalprecip_mm,
    chance_of_rain = chance_of_rain,
    chance_of_snow = chance_of_snow,

    )