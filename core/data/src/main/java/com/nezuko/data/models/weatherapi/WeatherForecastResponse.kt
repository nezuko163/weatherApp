package com.nezuko.data.models.weatherapi

import com.nezuko.domain.models.weatherapi.CurrentWeather
import com.nezuko.domain.models.weatherapi.DailyWeather
import com.nezuko.domain.models.weatherapi.Location
import com.nezuko.domain.models.weatherapi.WeatherForecast
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherForecastResponse(
    val location: LocationResponse,
    @SerialName("current")
    val currentWeather: CurrentWeatherResponseData,
    @SerialName("forecast")
    val forecast: ForecastDailyData
)

@Serializable
data class ForecastDailyData(
    @SerialName("forecastday")
    val forecastDaily: List<DailyWeatherResponse>
)

fun WeatherForecastResponse.toWeatherForecast() = WeatherForecast(
    location = location.toLocation(),
    currentWeather = currentWeather.toCurrentWeather(),
    forecastDaily = forecast.forecastDaily.map { it.toDailyWeather() }
)
