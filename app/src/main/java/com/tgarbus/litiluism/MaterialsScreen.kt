package com.tgarbus.litiluism

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.tgarbus.litiluism.ui.reusables.ButtonType
import com.tgarbus.litiluism.ui.reusables.Dock
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.Header

@Composable
fun MaterialsScreen(navController: NavController) {
    FullScreenPaddedColumn {
        Header("Materials")
    }
    Dock(ButtonType.MATERIALS, navController)
}