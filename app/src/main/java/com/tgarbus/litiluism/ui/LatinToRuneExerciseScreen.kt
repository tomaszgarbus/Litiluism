package com.tgarbus.litiluism.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.ui.reusables.ExerciseHeaderFrame
import com.tgarbus.litiluism.ui.reusables.PrimaryButton
import com.tgarbus.litiluism.ui.reusables.ThreeAnswerButtons
import com.tgarbus.litiluism.viewmodel.LatinToRuneExerciseViewModel

@Composable
fun LatinToRuneExerciseScreen(
    navController: NavController,
    viewModel: LatinToRuneExerciseViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        val sarabunFontFamily = FontFamily(
            Font(R.font.sarabun_regular, FontWeight.Normal),
            Font(R.font.sarabun_bold, FontWeight.Bold),
            Font(R.font.sarabun_thin, FontWeight.Thin),
        )
        ExerciseHeaderFrame("Rune to latin exercise", viewModel.runeRowName, navController)
        if (viewModel.finished.collectAsState().value) {
            PrimaryButton(
                text = "Complete",
                onClick = { navController.navigate("afterexercise") }
            )
        } else {
            Text(
                "${viewModel.queueSize} left",
                fontFamily = sarabunFontFamily,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                viewModel.questionsFlow.collectAsState().value.toString(),
                fontFamily = sarabunFontFamily,
                textAlign = TextAlign.Center,
                fontSize = 170.sp,
                color = colorResource(R.color.secondary),
                modifier = Modifier.fillMaxWidth()
            )

            val answerOptions = viewModel.optionsFlow.collectAsState().value
            ThreeAnswerButtons(answerOptions, onCorrectAnswerClick = {
                viewModel.onCorrectClick()
            })
        }
    }
}