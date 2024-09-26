package com.nezuko.weatherapp.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.nezuko.main.HomeViewModel
import com.nezuko.main.list_cities.ListCitiesRoute
import com.nezuko.main.home.HomeRoute

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val vm: HomeViewModel = getViewModel()
        Log.i("ГАВНО", "Content: recomp1")
        HomeRoute(
            viewModel = vm,
            onListCitiesNavigate = {
                navigator.push(ListCitiesScreen(vm))
            }
        )
    }
}


class ListCitiesScreen(private val viewModel: HomeViewModel) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Log.i("ZALUPA", "Content: recomp")

        ListCitiesRoute(
            viewModel = viewModel,
            onNavigateBack = navigator::pop
        )
    }

}