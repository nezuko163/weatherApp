package com.nezuko.data.source.remote

import android.util.Log
import com.nezuko.data.models.openweatherapi.CityCoordinatesResponse
import com.nezuko.data.models.openweatherapi.CoordinatesResponse
import com.nezuko.data.models.openweatherapi.WeatherReponse
import com.nezuko.data.models.openweatherapi.toCoordinates
import com.nezuko.data.models.openweatherapi.toWeather
import com.nezuko.data.models.weatherapi.CurrentWeatherResponse
import com.nezuko.data.models.weatherapi.WeatherForecastResponse
import com.nezuko.data.models.weatherapi.toCurrentWeather
import com.nezuko.data.models.weatherapi.toWeatherForecast
import com.nezuko.domain.models.openweatherapi.Coordinates
import com.nezuko.domain.models.ResultModel
import com.nezuko.domain.models.openweatherapi.Weather
import com.nezuko.domain.models.weatherapi.CurrentWeather
import com.nezuko.domain.models.weatherapi.WeatherForecast
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.request

import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class ApiRemoteSource {

    private val TAG = "ApiRemoteSource"
    private val IODispathcer = Dispatchers.IO

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            }
            )
        }

        install(Logging) {
            level = LogLevel.INFO
        }

    }

    suspend fun getCoordinatesCityByName(name: String): ResultModel<Coordinates> =
        withContext(IODispathcer) {
            Log.i(TAG, "getCoordinatesCityByName: start")

            val response = client.get(ApiRoutes.CITY) {
                parameter("appid", ApiRoutes.API_KEY)
                parameter("q", name)
                parameter("lang", "ru")

            }

            Log.i(TAG, "getCoordinatesCityByName: ${response.request.url}")

            Log.i(TAG, "getCoordinatesCityByName: ${response.status}")
            Log.i(TAG, "getCoordinatesCityByName: ${response.body<String>()}")
            Log.i(TAG, "getCoordinatesCityByName: ${response.body<CoordinatesResponse>()}")

            try {
                if (response.status.value in 200..299) {
                    ResultModel.success((response.body<CityCoordinatesResponse>()).coord.toCoordinates())
                } else {
                    ResultModel.failure("гавнооооооооо")
                }
            } catch (e: Exception) {
                ResultModel.failure("гавно")
            }
        }


    suspend fun getWeatherForecastByCoordinates(coord: Coordinates): ResultModel<Weather> =
        withContext(IODispathcer) {
            val response = client.get(ApiRoutes.WEATHER) {
                parameter("lat", coord.lat)
                parameter("lon", coord.lon)
                parameter("lang", "ru")
                parameter("appid", ApiRoutes.API_KEY)
                parameter("exclude", "minute")
                parameter("units", "metric")
            }

            try {
                if (response.status.value in 200..200) {
                    ResultModel.success((response.body() as WeatherReponse).toWeather())
                } else {
                    ResultModel.failure("гавно")
                }
            } catch (e: Exception) {
                ResultModel.failure("тупое гавно")
            }
        }

    suspend fun getCurrentWeatherForecast(
        city: String,
    ): ResultModel<CurrentWeather> {
        val request = client.get(ApiRoutesWeatherApi.CURRENT_WEATHER) {
            parameter("key", ApiRoutesWeatherApi.API_KEY)
            parameter("q", city)
            parameter("lang", "ru")
            header(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 YaBrowser/24.7.0.0 Safari/537.36"
            )
        }

        Log.i(TAG, "getCurrentWeatherForecast: ${request.request.url}")
        return if (request.status.value !in 200..299) {
            ResultModel.failure("гавно")
        } else {
            val response: CurrentWeatherResponse = request.body()
            ResultModel.success(response.toCurrentWeather())
        }
    }

    suspend fun getWeatherForecastFor7Days(city: String) =
        getWeatherForecastForDays(city = city, days = 7)

    suspend fun getWeatherForecastForDays(city: String, days: Int): ResultModel<WeatherForecast> {
        if (city.isEmpty()) return ResultModel.failure("города нет")
        val request = client.get(ApiRoutesWeatherApi.WEATHER) {
            parameter("key", ApiRoutesWeatherApi.API_KEY)
            parameter("q", city)
            parameter("lang", "ru")
            parameter("days", days)
            header(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 YaBrowser/24.7.0.0 Safari/537.36"
            )
        }

        Log.i(TAG, "getWeatherForecastForDays: ${request.request.url}")
        return if (request.status.value !in 200..299) {
            ResultModel.failure("гавно")
        } else {
            val response: WeatherForecastResponse = request.body()
            Log.i(TAG, "getWeatherForecastForDays: ${response.forecast.forecastDaily.size}")
            ResultModel.success(response.toWeatherForecast())
        }
    }
}