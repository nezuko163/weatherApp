package com.nezuko.domain.models.weatherapi

import java.time.LocalDateTime

data class Astro(
    val sunrise: LocalDateTime,
    val sunset: LocalDateTime,
    val moonrise: LocalDateTime,
    val moonset: LocalDateTime,

    )
