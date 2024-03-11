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
import com.tgarbus.litiluism.data.toDisplayableString
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
    FullScreenPaddedColumn {
        ExerciseHeaderFrame(LocalContext.current.getString(
            R.string.latin_to_rune_header
        ), viewModel.baseRuneRow.toDisplayableString(LocalContext.current), navController)
        if (viewModel.finished.collectAsState().value) {
            PrimaryButton(
                text = LocalContext.current.getString(
                    R.string.complete
                ),
                onClick = {
                    val correct = viewModel.score.value.correct
                    val total = viewModel.score.value.total
                    navController.navigate("afterexercise/${correct}/${total}")
                }
            )
        } else {
            Text(
                "${viewModel.queueSize} ${
                    LocalContext.current.getString(
                        R.string.latin_to_rune_left
                    )
                }",
                fontFamily = sarabunFontFamily,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = colorResource(R.color.dark_grey)
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
            ThreeAnswerButtons(
                answerOptions,
                showAllVariantsPerAnswer = true,
                onCorrectAnswer = { _, corr ->
                    viewModel.onCorrectClick(corr)
                })
        }
    }
}