package com.tgarbus.litiluism.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import com.tgarbus.litiluism.ui.reusables.BackButton
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.Header
import com.tgarbus.litiluism.ui.reusables.SemiCircularProgressBar
import com.tgarbus.litiluism.ui.reusables.StatisticsLineChart
import com.tgarbus.litiluism.viewmodel.StatisticsViewModel

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
            Box(
                Modifier
                    .weight(2f)
                    .padding(10.dp)
            ) {
                SemiCircularProgressBar(
                    progressPercentage,
                    canvasModifier = Modifier.fillMaxSize()
                )
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
fun StatisticsScreen(navController: NavController, viewModel: StatisticsViewModel = viewModel()) {
    FullScreenPaddedColumn {
        Row(verticalAlignment = Alignment.CenterVertically) {
            BackButton(navController)
            Header("Statistics")
        }
        val context = LocalContext.current
        val lessonsPercentage =
            viewModel.getLessonsCompletedPercentage(context).collectAsState(0)
        val exercisesPercentage =
            viewModel.getExercisesCompletedPercentage(context).collectAsState(0)
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TileWithSemiCircularProgressBar(
                progressPercentage = lessonsPercentage.value,
                description = "lessons completed",
                tileModifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            )
            TileWithSemiCircularProgressBar(
                progressPercentage = exercisesPercentage.value,
                description = "exercises completed",
                tileModifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            )
        }
        Row(Modifier.fillMaxWidth()) {
            Tile(Modifier.weight(1f)) {
                StatisticsLineChart("Progress", values = listOf())
            }
        }
    }
}