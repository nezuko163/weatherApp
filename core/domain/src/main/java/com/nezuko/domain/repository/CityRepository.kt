package com.nezuko.domain.repository

import com.nezuko.domain.models.ResultModel
import com.nezuko.domain.models.weatherapi.CurrentWeather
import com.nezuko.domain.models.weatherapi.WeatherForecast
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton


@Singleton
interface CityRepository {

//    fun cityWeatherForecast(name: String): Flow<ResultModel<Weather>>

    fun currentWeatherForecast(city: String): Flow<ResultModel<CurrentWeather>>

    fun weatherFor7Days(city: String): Flow<ResultModel<WeatherForecast>>
}