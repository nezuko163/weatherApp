package com.nezuko.main.list_cities

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nezuko.domain.models.ResultModel
import com.nezuko.domain.models.weatherapi.WeatherForecast
import com.nezuko.domain.utils.toCelsius
import com.nezuko.main.R
import com.nezuko.main.list_cities.composables.CityBottomSheet
import com.nezuko.ui.composables.ImageFromInet
import com.nezuko.ui.theme.Spacing
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListCitiesScreen(
    cities: HashMap<String, ResultModel<WeatherForecast>>,
    onNavigateBack: () -> Unit,
    onCityInputEnd: (String) -> Unit,
    onCityChosen: (String) -> Unit,
    onCitiesClearClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Log.i(TAG, "ListCitiesScreen: recomp")

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Управление городами",
                        color = Color.Black
                    )
                },

                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "назад"
                        )
                    }
                },

                actions = {
                    IconButton(
                        onClick = onCitiesClearClick,
                        modifier = Modifier
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "очистить всё"
                        )
                    }
                }

            )
        },

        floatingActionButton = {
            FloatingActionButton(
                containerColor = Color(0xFF00B0FF),
                onClick = { showBottomSheet = true }) {

                Icon(
                    tint = Color.White,

                    imageVector = Icons.Default.Add,
                    contentDescription = "добавить город"
                )
            }
        }
    ) { paddingValues ->
        paddingValues.hashCode()

        if (showBottomSheet) {
            CityBottomSheet(
                sheetState = sheetState,
                coroutineScope = scope,
                onSheetClosed = { showBottomSheet = false },
                onCityInputEnd = onCityInputEnd
            )
        }

        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(vertical = Spacing.default.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            itemsIndexed(
                items = cities.toList(),
                key = { _: Int, pair: Pair<String, ResultModel<WeatherForecast>> ->
                    pair.first
                },
            ) { index, pair ->

                if (pair.second.data == null) return@itemsIndexed
                WeatherCityCard(
                    onCityWeatherNavigate = { onCityChosen(pair.second.data!!.location.name) },
                    city = pair.second.data!!.location.name,
                    temp = pair.second.data!!.currentWeather.temp_c.roundToInt(),
                    icon = pair.second.data!!.currentWeather.condition.icon
                )
            }
        }
    }
}

@Composable
private fun WeatherCityCard(
    modifier: Modifier = Modifier,
    onCityWeatherNavigate: (city: String) -> Unit,
    city: String,
    temp: Int,
    icon: String,
) {
    val gradient =
        Brush.linearGradient(
            colors = listOf(
                Color(0xFF80D8FF),
                Color(0xFF00B0FF)
            )
        )

    ElevatedCard(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(horizontal = Spacing.default.medium, vertical = Spacing.default.small),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = { onCityWeatherNavigate(city) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(horizontal = Spacing.default.medium),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                text = city,
                color = Color.White,
                fontSize = 25.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            ImageFromInet(
                url = icon,
                contentDescription = "облачность",
                errorImageResource = R.drawable.oblako,
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .aspectRatio(1f)
            )

            Spacer(modifier = Modifier.padding(horizontal = Spacing.default.tiny))

            Text(
                text = temp.toCelsius(),
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}