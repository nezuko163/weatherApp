package com.nezuko.data.models.weatherapi

import com.nezuko.domain.models.weatherapi.Astro
import com.nezuko.domain.models.weatherapi.DailyWeather
import com.nezuko.domain.utils.day
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyWeatherResponse(
    val date: String,
    @SerialName("day")
    val dayStat: DailyWeatherStatResponse,
    val astro: AstroResponse,

    @SerialName("hour")
    val hourlyWeatherList: List<HourlyWeatherResponse>,
)


fun DailyWeatherResponse.toDailyWeather() = DailyWeather(
    date = day(date),
    dayStat.toDailyWeatherStat(),
    astro.toAstro(),
    hourlyWeatherList.map { it.toHourlyWeather() }
)