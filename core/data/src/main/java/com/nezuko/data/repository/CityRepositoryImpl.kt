package com.nezuko.data.repository


import android.util.Log
import com.nezuko.data.source.remote.ApiRemoteSource
import com.nezuko.domain.models.ResultModel
import com.nezuko.domain.models.weatherapi.CurrentWeather
import com.nezuko.domain.models.weatherapi.WeatherForecast
import com.nezuko.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class CityRepositoryImpl @Inject constructor(
    private val apiRemoteSource: ApiRemoteSource
) : CityRepository {

    init {
        Log.i(TAG, "init ")
    }

    companion object {
        private const val TAG = "CityRepositoryImpl"
    }

//    override fun cityWeatherForecast(name: String): Flow<ResultModel<Weather>> = flow {
//        emit(ResultModel.loading())
//
//        Log.i(TAG, "cityWeatherForecast: start")
//
//        val coord = apiRemoteSource.getCoordinatesCityByName(name)
//
//        Log.i(TAG, "cityWeatherForecast: ауе")
//
//        if (coord.status != ResultModel.Status.SUCCESS || coord.data == null) {
//            Log.i(TAG, "cityWeatherForecast: нихуя")
//            emit(ResultModel.failure("не получилось получить коодинаты"))
//            return@flow
//        }
//
//        val weather = apiRemoteSource.getWeatherForecastByCoordinates(coord.data!!)
//
//        if (weather.status != ResultModel.Status.SUCCESS || weather.data == null) {
//            emit(ResultModel.failure("не получилось загрузить погоду"))
//            Log.i(TAG, "cityWeatherForecast: тут с погодой проёб")
//            return@flow
//        }
//
//        Log.i(TAG, "cityWeatherForecast: ${weather.data}")
//
//        emit(ResultModel.success(weather.data!!))
//    }

    override fun currentWeatherForecast(city: String): Flow<ResultModel<CurrentWeather>> = flow {
        emit(ResultModel.loading())

        Log.i(TAG, "currentWeatherForecast: start")

        try {
            val currentWeather = apiRemoteSource.getCurrentWeatherForecast(city)

            if (currentWeather.status != ResultModel.Status.SUCCESS || currentWeather.data == null) {
                emit(ResultModel.failure("ашипка"))
            } else {
                emit(ResultModel.success(currentWeather.data!!))
            }

        } catch (e: Exception) {
            emit(ResultModel.failure("гавно"))
        }
    }

    override fun weatherFor7Days(city: String): Flow<ResultModel<WeatherForecast>> = flow {
        emit(ResultModel.loading())

        Log.i(TAG, "weatherFor7Days: Start")

        val weatherForecast = apiRemoteSource.getWeatherForecastFor7Days(city)

        if (weatherForecast.status != ResultModel.Status.SUCCESS || weatherForecast.data == null) {
            emit(ResultModel.failure("ашипка"))
        } else {
            emit(ResultModel.success(weatherForecast.data!!))
        }
    }
}