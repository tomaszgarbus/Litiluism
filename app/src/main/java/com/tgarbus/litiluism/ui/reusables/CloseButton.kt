package com.tgarbus.litiluism.ui.reusables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tgarbus.litiluism.R

// Returns the size of the button + padding.
@Composable
fun closeButton(description: String, brightTint: Boolean = false, modifier: Modifier = Modifier, onClose: () -> Unit): Dp {
    val padding = 8.dp
    val size = 21.dp
    Box(
        modifier
            .clip(CircleShape)
            .clickable { onClose() }
            .padding(padding)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_cross),
            contentDescription = description,
            tint = if (brightTint) Color.White else colorResource(R.color.dark_grey),
            modifier = Modifier
                .padding(0.dp)
                .width(size)
                .height(size)
        )
    }
    return padding * 2 + size
}