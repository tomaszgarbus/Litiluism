package com.tgarbus.litiluism.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.InputMethod
import com.tgarbus.litiluism.data.StaticContentRepository
import com.tgarbus.litiluism.data.TransliterationExercise
import com.tgarbus.litiluism.data.TransliterationExerciseState
import com.tgarbus.litiluism.generateRuneToLatinOptions
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import com.tgarbus.litiluism.ui.reusables.BalloonsQueue
import com.tgarbus.litiluism.ui.reusables.ExerciseHeaderFrame
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.IntroTooltip
import com.tgarbus.litiluism.ui.reusables.ManualInput
import com.tgarbus.litiluism.ui.reusables.PrimaryButton
import com.tgarbus.litiluism.ui.reusables.PrimaryOutlinedButton
import com.tgarbus.litiluism.ui.reusables.ThreeAnswerButtons
import com.tgarbus.litiluism.viewmodel.TransliterationExerciseViewModel
import kotlin.math.max

fun colorWithAlpha(color: Color, alpha: Float): Color {
    return Color(color.red, color.green, color.blue, alpha)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExerciseScreen(
    transliterationExercise: TransliterationExercise,
    viewModel: TransliterationExerciseViewModel = viewModel(),
    navController: NavController
) {
    viewModel.transliterationExercise = transliterationExercise
    val state = viewModel.getState(LocalContext.current)
        .collectAsState(TransliterationExerciseState()).value
    val position = state.position
    val inputs = state.inputs
    val title = transliterationExercise.title
    val description = transliterationExercise.description
    val runes = transliterationExercise.runes
    val scrollState = rememberScrollState()
    val balloonsQueue = BalloonsQueue()
    val inputMethod =
        viewModel.getInputMethodAsFlow(LocalContext.current).collectAsState(InputMethod.VARIANTS)

    FullScreenPaddedColumn(unownedScrollState = scrollState) {
        ExerciseHeaderFrame(
            LocalContext.current.getString(
                R.string.transliteration_exercise_header
            ), title, navController
        )
        Text(
            text = description,
            modifier = Modifier.padding(vertical = 20.dp),
            textAlign = TextAlign.Justify,
            fontFamily = sarabunFontFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = colorResource(R.color.dark_grey)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(
                    id = getImageResourceId(
                        transliterationExercise.imgResourceName,
                        LocalContext.current
                    )
                ),
                contentDescription = transliterationExercise.title,
                modifier = Modifier.clip(AbsoluteRoundedCornerShape(50.dp))
            )
        }
        IntroTooltip(
            id = "transliteration_exercise_flow_row",
            text = LocalContext.current.getString(
                R.string.intro_tooltips_transliteration_exercise_flow
            ),
            queue = balloonsQueue,
            scrollState = scrollState,
        ) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 1.dp, shape = RoundedCornerShape(size = 21.dp))
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(size = 21.dp)
                    )
                    .padding(start = 15.dp, top = 15.dp, end = 15.dp, bottom = 15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                for ((i, r) in runes.withIndex()) {
                    Column {
                        Text(
                            r.toString(),
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                color = if (i == position) colorResource(R.color.primary) else colorResource(
                                    R.color.default_text
                                ),
                                fontSize = 24.sp,
                                fontWeight = if (i == position) FontWeight.ExtraBold else FontWeight.Normal
                            )
                        )
                        var textColor =
                            if (i < position) colorResource(R.color.default_text)
                            else if (i == position) colorResource(
                                R.color.correct_green
                            ) else colorResource(
                                R.color.translation_placeholder
                            )
                        if (i > position) {
                            textColor = colorWithAlpha(textColor, max((position + 4 - i) / 4f, 0f))
                        }
                        // TODO: add inline manual input
                        Text(
                            if (i < position) inputs[i].toString() else "?",
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                color = textColor,
                                fontSize = 20.sp,
                                fontWeight = if (i <= position) FontWeight.ExtraBold else FontWeight.Normal
                            )
                        )
                    }
                }
            }
        }
        if (state.complete) {
            Text(
                text = LocalContext.current.getString(
                    R.string.transliteration_exercise_correct
                ),
                color = colorResource(id = R.color.correct_green),
                fontSize = 24.sp,
                fontFamily = sarabunFontFamily,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 1.dp, shape = RoundedCornerShape(size = 21.dp))
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(size = 21.dp)
                    )
                    .padding(start = 35.dp, top = 30.dp, end = 35.dp, bottom = 30.dp)
            ) {
                Text(
                    text = transliterationExercise.explanation,
                    fontFamily = sarabunFontFamily,
                    fontSize = 18.sp,
                    color = colorResource(R.color.dark_grey)
                )
                if (transliterationExercise.sources.isNotEmpty()) {
                    Text(
                        text = LocalContext.current.getString(
                            R.string.transliteration_exercise_sources
                        ),
                        fontFamily = sarabunFontFamily,
                        fontSize = 12.sp,
                        color = colorResource(R.color.dark_grey),
                    )
                    for (source in transliterationExercise.sources) {
                        Text(
                            text = source,
                            fontFamily = sarabunFontFamily,
                            fontSize = 12.sp,
                            color = colorResource(R.color.dark_grey),
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            PrimaryButton(
                text = LocalContext.current.getString(R.string.complete),
                onClick = {
                    val correct = state.score.correct
                    val total = state.score.total
                    navController.navigate("afterexercise/${correct}/${total}")
                }
            )
        } else {
            val context = LocalContext.current
            val tooltipText = context.getString(
                when (inputMethod.value) {
                    InputMethod.VARIANTS -> R.string.intro_tooltips_transliteration_with_variants
                    InputMethod.KEYBOARD -> R.string.intro_tooltips_transliteration_with_keyboard
                }
            )
            IntroTooltip(
                id = "transliteration_exercise_three_buttons",
                text = tooltipText,
                queue = balloonsQueue,
                scrollState = scrollState,
            ) {
                val options = generateRuneToLatinOptions(
                    transliterationExercise.runeRow,
                    transliterationExercise.runes[position]
                )
                val onCorrectAnswer: (Char, Boolean) -> Unit = { c, corr ->
                    viewModel.onUserInput(c, state.inputs.length, context, corr)
                }
                when (inputMethod.value) {
                    InputMethod.VARIANTS -> ThreeAnswerButtons(
                        options,
                        showAllVariantsPerAnswer = true,
                        onCorrectAnswer
                    )

                    InputMethod.KEYBOARD -> ManualInput(options, onCorrectAnswer)
                }
            }
        }
        val context = LocalContext.current
        AnimatedVisibility(
            visible = state.inputs.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            PrimaryOutlinedButton(
                text = LocalContext.current.getString(
                    R.string.transliteration_exercise_reset_progress
                )
            ) {
                viewModel.resetProgress(context)
            }
        }
    }
}

@Composable
fun ExerciseScreenFromId(exerciseId: String, navController: NavController) {
    ExerciseScreen(
        transliterationExercise = StaticContentRepository.getInstance().exercisesMap[exerciseId]!!,
        navController = navController
    )
}
