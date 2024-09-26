package com.nezuko.main.home

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.nezuko.domain.models.ResultModel
import com.nezuko.domain.models.weatherapi.DailyWeather
import com.nezuko.domain.models.weatherapi.WeatherForecast
import com.nezuko.main.home.composables.CurrentWeatherBlock
import com.nezuko.main.home.composables.DailyWeatherBlock
import com.nezuko.main.home.composables.HourlyWeatherBlock
import com.nezuko.ui.theme.Spacing
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    weatherForecast: ResultModel<WeatherForecast>,
    city: String,
    onListCitiesNavigate: () -> Unit,
    onSettingsNavigate: () -> Unit,
) {
    Log.i(TAG, "HomeScreen: ${weatherForecast.data}")

    val gradient =
        Brush.linearGradient(
            colors = listOf(
                Color(0xFFa8d8ff),
                Color(0xFF66d9ff)
            )
        )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(horizontal = Spacing.default.medium)
    ) {
//        Image(
//            modifier = Modifier.fillMaxSize(),
//            painter = painterResource(id = R.drawable.sky),
//            contentDescription = null,
//            contentScale = ContentScale.Crop
//        )



        Scaffold(
            modifier = modifier.background(Color.Transparent),
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    modifier = Modifier,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                    ),
                    title = {
                        Text(
                            text = city.ifEmpty { "Поставьте город" },
                            modifier = Modifier.clickable { onListCitiesNavigate() }
                        )
                    },

                    actions = {
                        IconButton(
                            onClick = { onListCitiesNavigate() },
                            modifier = Modifier
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                tint = Color.White,
                                contentDescription = "настройки"
                            )
                        }

                        Spacer(modifier = Modifier.padding(Spacing.default.tiny))

                        IconButton(
                            onClick = { onSettingsNavigate() },
                            modifier = Modifier
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                tint = Color.White,
                                contentDescription = "настройки"
                            )
                        }
                    }

                )
            },

//        topBar = {
//            Row(
//                modifier = Modifier
//                    .padding(horizontal = Spacing.default.medium)
//                    .border(2.dp, Color.Black),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = cityName,
//                    modifier = Modifier
//                        .clickable { onCityNameClick() }
//                        .size(30.dp)
//                )
//
//                Spacer(modifier = Modifier.weight(1f))
//
//                IconButton(
//                    onClick = { onCityControlIconClick() },
//                    modifier = Modifier
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Menu,
//                        contentDescription = "настройки"
//                    )
//                }
//
//                Spacer(modifier = Modifier.padding(Spacing.default.small))
//
//                IconButton(
//                    onClick = { onSettingsIconClick() },
//                    modifier = Modifier
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Settings,
//                        contentDescription = "настройки"
//                    )
//                }
//            }
//        }
        ) { paddingValues ->


            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                ShowFeature(
                    weatherForecast = weatherForecast,
                    city = city,
                    onListCitiesNavigate = onListCitiesNavigate,
                    modifier = Modifier.align(Alignment.Center),
                    cityWeatherModifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}

@Composable
private fun ShowFeature(
    weatherForecast: ResultModel<WeatherForecast>,
    city: String,
    onListCitiesNavigate: () -> Unit,
    modifier: Modifier,
    cityWeatherModifier: Modifier
) {
    Log.i("ShowFeature", "ShowFeature: recomp")
    if (weatherForecast.status == ResultModel.Status.SUCCESS && weatherForecast.data != null ) {
        Log.i(TAG, "ShowFeature: $weatherForecast")
        weatherForecast.data!!.apply {
            CityWeather(
                modifier = cityWeatherModifier,
                temperature = currentWeather.temp_c.roundToInt(),
                feelsLikeTemperature = currentWeather.feelslike_c.roundToInt(),
                cloud = currentWeather.cloud,
                minTemp = forecastDaily.first().dayStat.mintemp_c.roundToInt(),
                maxTemp = forecastDaily.first().dayStat.maxtemp_c.roundToInt(),
                weatherDescription = currentWeather.condition.text,
                wind_kph = currentWeather.wind_kph,
                humidity = currentWeather.humidity,
                currentDay = forecastDaily.first(),
                dailyForecast = forecastDaily
            )
        }
    } else if (weatherForecast.status == ResultModel.Status.LOADING) {
        LoadingCityWeather(
            modifier = modifier,
            city = city,
        )
    } else {
        NoCity(
            modifier = modifier,
            onLabelClick = onListCitiesNavigate
        )
    }
}

@Composable
fun CityWeather(
    modifier: Modifier = Modifier,
    temperature: Int,
    feelsLikeTemperature: Int,
    minTemp: Int,
    maxTemp: Int,
    cloud: Int,
    wind_kph: Float,
    humidity: Int,
    weatherDescription: String,
    currentDay: DailyWeather,
    dailyForecast: List<DailyWeather>
) {
    Log.i("CityWeather", "CityWeather: recomp")
    Column {
        CurrentWeatherBlock(
            modifier = modifier,
            temperature = temperature,
            feelsLikeTemperature = feelsLikeTemperature,
            minTemp = minTemp,
            maxTemp = maxTemp,
            cloud = cloud,
            wind_kph = wind_kph,
            humidity = humidity,
            weatherDescription = weatherDescription,
        )

        Spacer(modifier = Modifier.padding(vertical = Spacing.default.large))

        HourlyWeatherBlock(
            modifier = modifier,
            currentDay = currentDay,
        )

        Spacer(modifier = Modifier.padding(vertical = Spacing.default.medium))


        DailyWeatherBlock(list = dailyForecast)
    }
}


@Composable
fun LoadingCityWeather(
    modifier: Modifier = Modifier,
    city: String
) {
    Column(modifier = modifier) {
        Text(text = "Погода в городе $city загружается")
    }
}

@Composable
fun NoCity(
    modifier: Modifier = Modifier,
    onLabelClick: () -> Unit,
) {
    val pickCity = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.Cyan,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("Выбрать город")
        }
    }

    Column(modifier = modifier) {
        Text(text = "Нет города!!((")
        Spacer(modifier = Modifier.padding(vertical = Spacing.default.tiny))
        Text(
            text = pickCity,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable { onLabelClick() }
        )
    }
}