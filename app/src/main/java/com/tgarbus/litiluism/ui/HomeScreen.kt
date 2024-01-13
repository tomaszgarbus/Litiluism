package com.tgarbus.litiluism.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.tgarbus.litiluism.ui.reusables.ButtonType
import com.tgarbus.litiluism.ui.reusables.Dock
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.Header


@Composable
fun HomeScreen(navController: NavController) {
    FullScreenPaddedColumn {
        Header("Litiluism")
    }
    Dock(ButtonType.HOME, navController)
}