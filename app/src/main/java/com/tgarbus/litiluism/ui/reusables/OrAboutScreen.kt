package com.tgarbus.litiluism.ui.reusables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.tgarbus.litiluism.data.AboutRepository
import com.tgarbus.litiluism.ui.AboutScreen
import com.tgarbus.litiluism.ui.HomeScreen

@Composable
fun OrAboutScreen(navController: NavController, content: @Composable () -> Unit) {
    val aboutRepo = AboutRepository()
    val alreadyCompleted = aboutRepo.getCompletedAsFlow(LocalContext.current).collectAsState(true)
    if (alreadyCompleted.value) {
        content()
    } else {
        AboutScreen(navController)
    }
}