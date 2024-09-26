package com.nezuko.data.models.weatherapi

import com.nezuko.domain.models.weatherapi.Condition
import com.nezuko.domain.models.weatherapi.CurrentWeather
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherResponse(
    val current: CurrentWeatherResponseData
)

@Serializable
data class CurrentWeatherResponseData(
    val temp_c: Double,
    val feelslike_c: Double,
    val cloud: Int,
    val condition: ConditionResponse,
    val humidity: Int,
    val wind_kph: Float
)

@Serializable
data class ConditionResponse(
    val text: String,
    val icon: String
)

fun CurrentWeatherResponse.toCurrentWeather() = CurrentWeather(
    temp_c = current.temp_c,
    feelslike_c = current.feelslike_c,
    cloud = current.cloud,
    condition = current.condition.toCondition(),
    humidity = current.humidity,
    wind_kph = current.wind_kph
)

fun CurrentWeatherResponseData.toCurrentWeather() = CurrentWeather(
    temp_c = temp_c,
    feelslike_c = feelslike_c,
    cloud = cloud,
    condition = condition.toCondition(),
    humidity = humidity,
    wind_kph = wind_kph
)

fun ConditionResponse.toCondition() = Condition(
    text = text,
    icon = icon
)