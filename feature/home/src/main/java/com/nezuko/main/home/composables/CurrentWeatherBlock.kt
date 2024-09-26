package com.nezuko.main.home.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.nezuko.domain.utils.toCelsius
import com.nezuko.ui.theme.Spacing

@Composable
fun CurrentWeatherBlock(
    modifier: Modifier = Modifier,
    temperature: Int,
    feelsLikeTemperature: Int,
    minTemp: Int,
    maxTemp: Int,
    cloud: Int,
    wind_kph: Float,
    humidity: Int,
    weatherDescription: String,
) {
    Column(modifier = modifier) {

        Spacer(modifier = Modifier.padding(vertical = Spacing.default.medium))

        Text(
            text = temperature.toCelsius(),
            textAlign = TextAlign.Center,
            fontSize = 50.sp,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )

        Spacer(modifier = Modifier.padding(vertical = Spacing.default.tiny))

        Text(
            text = "Ощущается как ${feelsLikeTemperature.toCelsius()}",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )

        Text(
            text = weatherDescription + " ${minTemp.toCelsius()}/${maxTemp.toCelsius()}",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )
        Text(
            text = "Облачность: $cloud",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )

        Text(
            text = "Ветер (км/ч): $wind_kph",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )

        Text(
            text = "Влажность: $humidity",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )
    }
}