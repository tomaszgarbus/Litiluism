package com.tgarbus.litiluism.ui

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
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.StaticContentRepository
import com.tgarbus.litiluism.data.TransliterationExercise
import com.tgarbus.litiluism.data.TransliterationExerciseState
import com.tgarbus.litiluism.generateRuneToLatinOptions
import com.tgarbus.litiluism.ui.reusables.ExerciseHeaderFrame
import com.tgarbus.litiluism.ui.reusables.PrimaryButton
import com.tgarbus.litiluism.ui.reusables.ThreeAnswerButtons
import com.tgarbus.litiluism.viewmodel.TransliterationExerciseViewModel
import kotlin.math.max

fun colorWithAlpha(color: Color, alpha: Float): Color {
    return Color(color.red, color.green, color.blue, alpha);
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
    val sarabunFontFamily = FontFamily(
        Font(R.font.sarabun_regular, FontWeight.Normal),
        Font(R.font.sarabun_bold, FontWeight.Bold),
        Font(R.font.sarabun_thin, FontWeight.Thin),
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        ExerciseHeaderFrame("Transliteration exercise", title, navController)
        Text(
            text = description,
            modifier = Modifier.padding(vertical = 20.dp),
            textAlign = TextAlign.Justify,
            fontFamily = sarabunFontFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight(400)
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
                contentDescription = "Image goes brr",
                modifier = Modifier.clip(AbsoluteRoundedCornerShape(50.dp))
            )
        }
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 1.dp, shape = RoundedCornerShape(size = 21.dp))
                .background(
                    color = colorResource(R.color.white),
                    shape = RoundedCornerShape(size = 21.dp)
                )
                .padding(start = 15.dp, top = 15.dp, end = 15.dp, bottom = 15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            for ((i, r) in runes.withIndex()) {
                Column() {
                    Text(
                        r.toString(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = if (i == position) colorResource(R.color.primary) else colorResource(
                                R.color.default_text
                            ),
                            fontSize = 24.sp,
                            fontWeight = if (i == position) FontWeight(800) else FontWeight(400)
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
                    Text(
                        if (i < position) inputs[i].toString() else "?",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = textColor,
                            fontSize = 20.sp,
                            fontWeight = if (i <= position) FontWeight(700) else FontWeight(400)
                        )
                    )
                }
            }
        }
        if (state.complete) {
            Text(
                text = "Correct!",
                color = colorResource(id = R.color.correct_green),
                fontSize = 24.sp,
                fontFamily = sarabunFontFamily,
                fontWeight = FontWeight(700),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 1.dp, shape = RoundedCornerShape(size = 21.dp))
                    .background(
                        color = colorResource(R.color.white),
                        shape = RoundedCornerShape(size = 21.dp)
                    )
                    .padding(start = 35.dp, top = 30.dp, end = 35.dp, bottom = 30.dp)
            ) {
                Text(
                    text = transliterationExercise.explanation,
                    fontFamily = sarabunFontFamily,
                    fontSize = 18.sp,
                )
                if (transliterationExercise.sources.isNotEmpty()) {
                    Text(
                        text = "Sources:",
                        fontFamily = sarabunFontFamily,
                        fontSize = 12.sp,
                    )
                    for (source in transliterationExercise.sources) {
                        Text(
                            text = source,
                            fontFamily = sarabunFontFamily,
                            fontSize = 12.sp,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            PrimaryButton(
                text = "Complete",
                onClick = { navController.navigate("afterexercise") }
            )
        } else {
            val context = LocalContext.current
            ThreeAnswerButtons(
                options = generateRuneToLatinOptions(
                    transliterationExercise.runeRow,
                    transliterationExercise.runes[position]
                ),
                onCorrectAnswerClick = { c ->
                    run {
                        viewModel.updateState(c, context)
                    }
                })
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
