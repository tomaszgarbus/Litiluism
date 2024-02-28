package com.tgarbus.litiluism.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.tgarbus.litiluism.data.AboutRepository
import com.tgarbus.litiluism.ui.reusables.OrAboutScreen

@Composable
fun HomeOrAboutScreen(navController: NavController) {
    OrAboutScreen(navController) {
        HomeScreen(navController)
    }
}