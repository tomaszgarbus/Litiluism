package com.tgarbus.litiluism.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.tgarbus.litiluism.ui.reusables.OrAboutScreen

@Composable
fun PracticeOrAboutScreen(navController: NavController) {
    OrAboutScreen(navController) {
        PracticeScreen(navController)
    }
}