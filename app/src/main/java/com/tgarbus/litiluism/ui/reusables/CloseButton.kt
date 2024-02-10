package com.tgarbus.litiluism.ui.reusables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tgarbus.litiluism.R

@Composable
fun CloseButton(description: String, onClose: () -> Unit) {
    Box(
        Modifier
            .clip(CircleShape)
            .clickable { onClose() }
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_cross),
            contentDescription = description,
            contentScale = ContentScale.None,
            modifier = Modifier
                .padding(0.dp)
                .width(21.dp)
                .height(21.dp)
        )
    }
}