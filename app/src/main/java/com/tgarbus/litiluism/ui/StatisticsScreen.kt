package com.tgarbus.litiluism.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.Header
import com.tgarbus.litiluism.ui.reusables.SemiCircularProgressBar
import com.tgarbus.litiluism.ui.reusables.StatisticsLineChart

@Composable
fun Tile(modifier: Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 1.dp, shape = RoundedCornerShape(size = 21.dp)
            )
            .clip(RoundedCornerShape(size = 21.dp))
            .background(Color.White)
            .padding(10.dp)
    )
    {
        content()
    }
}

@Composable
fun TileWithSemiCircularProgressBar(
    progressPercentage: Int,
    description: String,
    tileModifier: Modifier
) {
    Tile(modifier = tileModifier)
    {
        Column(Modifier.fillMaxSize()) {
            Box(Modifier.weight(2f)) {
                SemiCircularProgressBar(progressPercentage,
                    canvasModifier = Modifier.fillMaxSize())
            }
            Box(Modifier.weight(1f)) {
                Text(
                    description,
                    fontFamily = sarabunFontFamily,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun StatisticsScreen(navController: NavController) {
    FullScreenPaddedColumn {
        Header("Statistics")
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TileWithSemiCircularProgressBar(
                progressPercentage = 69,
                description = "lessons completed",
                tileModifier = Modifier.weight(1f).aspectRatio(1f)
            )
            TileWithSemiCircularProgressBar(
                progressPercentage = 20,
                description = "exercises completed",
                tileModifier = Modifier.weight(1f).aspectRatio(1f)
            )
        }
        StatisticsLineChart("Progress", values = listOf())
    }
}