package com.nezuko.main.home.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nezuko.domain.models.weatherapi.DailyWeather
import com.nezuko.domain.models.weatherapi.HourlyWeather
import com.nezuko.domain.utils.toCelsius
import com.nezuko.main.R
import com.nezuko.ui.composables.ImageFromInet
import com.nezuko.ui.theme.Spacing
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.roundToInt

private val TAG = "DailyWeatherBlock"

@Composable
fun HourlyWeatherBlock(
    modifier: Modifier,
    currentDay: DailyWeather
) {

    Log.i(TAG, "DailyWeatherBlock: recomp")

    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF99e6ff))
            .padding(vertical = Spacing.default.small)
            .horizontalScroll(scrollState),
    ) {
        Spacer(modifier = Modifier.padding(horizontal = Spacing.default.small))
        currentDay.hourlyWeatherList.forEach {
            HourBlockView(hourBlock = HourBlock.fromHourlyWeather(hourlyWeather = it))
            Spacer(modifier = Modifier.padding(horizontal = Spacing.default.small))
        }
    }
}

@Composable
private fun HourBlockView(
    modifier: Modifier = Modifier,
    hourBlock: HourBlock
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = hourBlock.time,
            textAlign = TextAlign.Center,
            fontSize = 13.sp
        )
        ImageFromInet(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            url = hourBlock.icon,

            errorImageResource = R.drawable.oblako
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            text = hourBlock.temperature.toCelsius()
        )
    }
}

@Stable
data class HourBlock(
    val time: String,
    val icon: String,
    val temperature: Int
) {
    companion object {
        fun fromHourlyWeather(hourlyWeather: HourlyWeather) = HourBlock(
            time = hourlyWeather.time.format(
                DateTimeFormatter.ofPattern("HH:mm")
            ),
            icon = hourlyWeather.condition.icon,
            temperature = hourlyWeather.temp_c.roundToInt()
        )
    }
}

@Composable
fun DailyWeatherBlock(
    modifier: Modifier = Modifier,
    list: List<DailyWeather>
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF99e6ff))
            .padding(Spacing.default.small)
    ) {
        list.forEach {
            DayBlockView(
                modifier = Modifier.padding(Spacing.default.tiny),
                dayBlock = DayBlock.fromDailyWeather(it)
            )
        }
    }
}

@Composable
fun DayBlockView(
    modifier: Modifier = Modifier,
    dayBlock: DayBlock
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = dayBlock.date,
            modifier = Modifier.width(80.dp),
            textAlign = TextAlign.Start
        )

        Text(
            text = dayBlock.dayOfWeek,
            modifier = Modifier.width(100.dp),
            textAlign = TextAlign.Start
        )


        ImageFromInet(url = dayBlock.icon, errorImageResource = R.drawable.oblako)

        Spacer(modifier = Modifier.weight(1f))

        Text(text = dayBlock.temp)
    }
}

@Stable
data class DayBlock(
    val date: String,
    val dayOfWeek: String,
    val icon: String,
    val temp: String
) {
    companion object {
        fun fromDailyWeather(dailyWeather: DailyWeather) = DayBlock(
            date = dailyWeather.date.format(DateTimeFormatter.ofPattern("dd/MM")),
            dayOfWeek = dailyWeather.date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("ru")),
            icon = dailyWeather.dayStat.condition.icon,
            temp = "${
                dailyWeather.dayStat.mintemp_c.roundToInt().toCelsius()
            }/${dailyWeather.dayStat.maxtemp_c.roundToInt().toCelsius()}"
        )
    }
}