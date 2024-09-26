package com.nezuko.domain.models.weatherapi

import java.time.LocalDateTime

data class DailyWeather(
    val date: LocalDateTime,
    val dayStat: DailyWeatherStat,
    val astro: Astro,
    val hourlyWeatherList: List<HourlyWeather>
)
