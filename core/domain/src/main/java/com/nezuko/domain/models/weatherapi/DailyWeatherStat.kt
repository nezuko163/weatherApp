package com.nezuko.domain.models.weatherapi

data class DailyWeatherStat(
    val maxtemp_c: Double,
    val mintemp_c: Double,
    val avgtemp_c: Double,
    val condition: Condition,
    val totalprecip_mm: Float,

    val chance_of_rain: Int,
    val chance_of_snow: Int,

)
