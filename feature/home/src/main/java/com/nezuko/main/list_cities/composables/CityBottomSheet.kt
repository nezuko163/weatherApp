package com.nezuko.main.list_cities.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityBottomSheet(
    onCityInputEnd: (String) -> Unit = {},
    sheetState: SheetState,
    coroutineScope: CoroutineScope,
    onSheetClosed: () -> Unit,
) {

    var text by remember { mutableStateOf("") }

    ModalBottomSheet(
        tonalElevation = 20.dp,
        onDismissRequest = {
            coroutineScope.launch {
                sheetState.hide()
            }.invokeOnCompletion { onSheetClosed() }
        },

        sheetState = sheetState
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            textStyle = TextStyle(
                color = Color.Black
            )

        )
        IconButton(
            onClick = {
                onCityInputEnd(text)
                coroutineScope.launch {
                    sheetState.hide()
                }.invokeOnCompletion { onSheetClosed() }
            }) {
            Icon(imageVector = Icons.Default.Done, contentDescription = "")
        }
    }
}