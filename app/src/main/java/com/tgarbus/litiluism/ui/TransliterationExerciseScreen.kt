package com.tgarbus.litiluism.ui

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.tgarbus.litiluism.data.TransliterationExercise
import com.tgarbus.litiluism.data.ExerciseStateViewModel
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.StaticContentRepository
import com.tgarbus.litiluism.generateOptions
import com.tgarbus.litiluism.ui.reusables.PrimaryButton
import kotlin.math.max

fun colorWithAlpha(color: Color, alpha: Float): Color {
    return Color(color.red, color.green, color.blue, alpha);
}

@Composable
fun Buttons(
    options: List<Pair<Char, Boolean>>,
    showFeedback: Boolean,
    onAnswerClick: (Char) -> Unit
) {
    Log.d("recomp", "Recomposition of buttons: $options");
    val sarabunFontFamily = FontFamily(
        Font(R.font.sarabun_regular, FontWeight.Normal),
        Font(R.font.sarabun_bold, FontWeight.Bold),
        Font(R.font.sarabun_thin, FontWeight.Thin),
    )
    val buttonShape = RoundedCornerShape(size = 14.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            val feedbackAlpha: Float by animateFloatAsState(
                if (showFeedback) 1f else 0f,
                label = "animateFeedback"
            )
            Text(
                text = "Click the correct answer to continue",
                fontFamily = sarabunFontFamily,
                fontWeight = FontWeight(700),
                fontSize = 16.sp,
                color = colorResource(R.color.wrong_red),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(feedbackAlpha)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val buttonModifier = Modifier
                    .shadow(elevation = 1.dp, shape = buttonShape)
                    .background(color = colorResource(id = R.color.white))
                    .weight(1f)
                    .aspectRatio(1f / 0.95f)
                val buttonTextColor = colorResource(R.color.dark_grey)
                val correctButtonTextColor =
                    if (showFeedback) colorResource(R.color.correct_green) else buttonTextColor
                val wrongButtonTextcolor =
                    if (showFeedback) colorResource(R.color.wrong_red) else buttonTextColor
                for (i in 0..2) {
                    OutlinedButton(
                        onClick = { onAnswerClick(options[i].first) },
                        modifier = buttonModifier,
                        shape = buttonShape
                    ) {
                        Text(
                            options[i].first.toString(),
                            color = if (options[i].second) correctButtonTextColor else wrongButtonTextcolor,
                            fontWeight = FontWeight(600),
                            fontSize = 20.sp,
                            fontFamily = sarabunFontFamily
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExerciseScreen(
    transliterationExercise: TransliterationExercise,
    viewModel: ExerciseStateViewModel = viewModel(),
    navController: NavController
) {
    viewModel.transliterationExercise = transliterationExercise
    val state = viewModel.state.collectAsState().value
    Log.d("recomp", "Recomposition of all: " + state.toString());
    val position = state.position
    val inputs = state.inputs
    val title = transliterationExercise.title
    val description = transliterationExercise.description
    val runes = transliterationExercise.runes

    val options = rememberSaveable { generateOptions(transliterationExercise) }
    val showFeedback = rememberSaveable { mutableStateOf<Boolean>(false) }

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
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.icon_cross),
                contentDescription = "Close exercise",
                contentScale = ContentScale.None,
                modifier = Modifier
                    .padding(0.dp)
                    .width(21.dp)
                    .height(21.dp)
                    .clickable { navController.navigate("exerciseslist") }
            )
        }
        Text(
            text = "Transliteration exercise",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.sarabun_regular)),
                fontWeight = FontWeight(700),
                color = Color(0xFF9C9C9C),
            )
        )
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxWidth(),
            fontFamily = sarabunFontFamily,
            fontSize = 20.sp,
            fontWeight = FontWeight(700),
        )
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
        if (viewModel.isComplete()) {
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
            Buttons(
                options = options[state.position],
                showFeedback = showFeedback.value,
                onAnswerClick = { c ->
                    run {
                        if (c == transliterationExercise.solution()[state.position]) {
                            Log.d("recomp", "Button click: " + c.toString());
                            viewModel.updateState(c)
                            showFeedback.value = false;
                            Log.d("recomp", "Position: " + state.toString());
                        } else {
                            showFeedback.value = true;
                        }
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