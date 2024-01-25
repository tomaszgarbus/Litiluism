package com.tgarbus.litiluism.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.tgarbus.litiluism.data.AboutRepository

@Composable
fun HomeOrAboutScreen(navController: NavController) {
    val aboutRepo = AboutRepository()
    val alreadyCompleted = aboutRepo.getCompletedAsFlow(LocalContext.current).collectAsState(true)
    if (alreadyCompleted.value) {
        HomeScreen(navController)
    } else {
        AboutScreen(navController)
    }
}