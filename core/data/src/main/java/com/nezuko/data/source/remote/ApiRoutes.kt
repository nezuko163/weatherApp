package com.nezuko.data.source.remote

object ApiRoutes {
    const val BASE = "https://api.openweathermap.org/data"

    const val API_KEY = "c2a177c24c3c8d7320396d029b86d800"

    const val WEATHER = "$BASE/3.0/onecall"
    const val CITY = "$BASE/2.5/weather"
}

object ApiRoutesWeatherApi {
    const val API_KEY = "bf37b2b62e33404caf4132557241109"

    const val BASE = "https://api.weatherapi.com/v1"

    const val WEATHER = "$BASE/forecast.json"
    const val CURRENT_WEATHER = "$BASE/current.json"

}