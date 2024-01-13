package com.tgarbus.litiluism.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import com.tgarbus.litiluism.ui.reusables.ExerciseHeaderFrame
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.PrimaryButton
import com.tgarbus.litiluism.ui.reusables.ThreeAnswerButtons
import com.tgarbus.litiluism.viewmodel.LatinToRuneExerciseViewModel

@Composable
fun LatinToRuneExerciseScreen(
    navController: NavController,
    viewModel: LatinToRuneExerciseViewModel = viewModel()
) {
    FullScreenPaddedColumn() {
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