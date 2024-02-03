package com.tgarbus.litiluism.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.InputMethod
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import com.tgarbus.litiluism.ui.reusables.ExerciseHeaderFrame
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.ManualInput
import com.tgarbus.litiluism.ui.reusables.PrimaryButton
import com.tgarbus.litiluism.ui.reusables.ThreeAnswerButtons
import com.tgarbus.litiluism.viewmodel.RuneToLatinExerciseViewModel

@Composable
fun RuneToLatinExerciseScreen(
    navController: NavController,
    viewModel: RuneToLatinExerciseViewModel = viewModel()
) {
    val inputMethod =
        viewModel.getInputMethodAsFlow(LocalContext.current).collectAsState(InputMethod.VARIANTS)
    FullScreenPaddedColumn() {
        ExerciseHeaderFrame("Rune to latin exercise", viewModel.runeRowName, navController)
        if (viewModel.finished.collectAsState().value) {
            PrimaryButton(
                text = "Complete",
                onClick = {
                    val correct = viewModel.score.value.correct
                    val total = viewModel.score.value.total
                    navController.navigate("afterexercise/${correct}/${total}")
                }
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
            val onCorrectAnswer: (Char, Boolean) -> Unit = { _, corr ->
                viewModel.onCorrectClick(corr)
            }
            when (inputMethod.value) {
                InputMethod.VARIANTS -> ThreeAnswerButtons(answerOptions, onCorrectAnswer)
                InputMethod.KEYBOARD -> ManualInput(answerOptions, onCorrectAnswer)
            }
        }
    }
}