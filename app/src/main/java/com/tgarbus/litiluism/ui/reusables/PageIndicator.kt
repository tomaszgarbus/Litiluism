package com.tgarbus.litiluism.ui.reusables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier = Modifier) {
    Row(
        modifier
            .height(24.dp)
            .padding(start = 4.dp)
            .fillMaxWidth(0.5f),
        horizontalArrangement = Arrangement.Start
    ) {
        repeat(pageCount) { iteration ->
            val color =
                if (currentPage == iteration) Color.White else Color.White.copy(alpha = 0.5f)
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(color)
                    .weight(1f)
                    .height(4.dp)
            )
        }
    }
}