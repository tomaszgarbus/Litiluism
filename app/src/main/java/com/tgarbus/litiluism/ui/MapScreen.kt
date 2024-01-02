package com.tgarbus.litiluism.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.ui.reusables.Header
import com.tgarbus.litiluism.ui.reusables.MapPreview

@Composable
fun MapScreen(navController: NavController) {
    // TODO: Extract common column to a custom composable
    Column(
        modifier = Modifier
            .fillMaxWidth()
            // TODO: set bg color through theme
            .background(colorResource(R.color.light_bg))
            .padding(vertical = 20.dp)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // TODO: Extract to a common component
            IconButton(onClick = { navController.navigate("practice") }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_backarrow),
                    contentDescription = "back",
                    tint = colorResource(R.color.secondary)
                )
            }
            Header("Pick from map")
        }
        Box(modifier = Modifier.clip(RoundedCornerShape(size = 21.dp))) {
            MapPreview(navController)
        }
    }
}