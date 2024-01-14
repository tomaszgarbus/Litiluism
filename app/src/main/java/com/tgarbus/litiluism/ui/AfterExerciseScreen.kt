package com.tgarbus.litiluism.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.ExerciseScore
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import com.tgarbus.litiluism.ui.reusables.PrimaryButton
import com.tgarbus.litiluism.viewmodel.AfterExerciseViewModel
import kotlin.math.max

@Composable
fun ScoreDisplay(score: ExerciseScore) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "${score.correct} / ${score.total}",
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            color = Color.White
        )
        val width = 96.dp
        Box(
            modifier = Modifier
                .height(12.dp)
                .width(width)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.White.copy(alpha = 0.5f))
        ) {
            Box(
                modifier = Modifier
                    .height(12.dp)
                    .width(width * (score.correct.toFloat() / max(1, score.total)))
                    .clip(RoundedCornerShape(6.dp))
                    .background(colorResource(R.color.primary))
            )
        }
    }
}

@Composable
fun AfterExerciseScreen(
    navController: NavController,
    viewModel: AfterExerciseViewModel = viewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.background_secondary_gradient_with_runes),
            modifier = Modifier.fillMaxSize(),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Keep it up!",
                fontFamily = sarabunFontFamily,
                fontWeight = FontWeight.ExtraBold,
                color = colorResource(R.color.primary),
                fontSize = 48.sp
            )
            ScoreDisplay(viewModel.score)
            PrimaryButton(
                text = "Back to practice",
                onClick = {
                    navController.popBackStack()
                    navController.popBackStack()
                })
        }
    }
}