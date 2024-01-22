package com.tgarbus.litiluism.ui.reusables

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.tgarbus.litiluism.R

@Composable
fun BackButton(navController: NavController) {
    IconButton(onClick = { navController.popBackStack() }) {
        Icon(
            painter = painterResource(id = R.drawable.icon_backarrow),
            contentDescription = "back",
            tint = colorResource(R.color.secondary)
        )
    }
}