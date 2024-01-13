package com.tgarbus.litiluism.ui.reusables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.ThreeButtonOptions
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily

@Composable
fun ThreeAnswerButtons(
    options: ThreeButtonOptions,
    onCorrectAnswerClick: (Char) -> Unit
) {
    val buttonShape = RoundedCornerShape(size = 14.dp)
    val showFeedback = rememberSaveable { mutableStateOf<Boolean>(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            val feedbackAlpha: Float by animateFloatAsState(
                if (showFeedback.value) 1f else 0f,
                label = "animateFeedback"
            )
            Text(
                text = "Click the correct answer to continue",
                fontFamily = sarabunFontFamily,
                fontWeight = FontWeight.ExtraBold,
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
                    if (showFeedback.value) colorResource(R.color.correct_green) else buttonTextColor
                val wrongButtonTextColor =
                    if (showFeedback.value) colorResource(R.color.wrong_red) else buttonTextColor
                for (i in 0..2) {
                    ElevatedButton(
                        onClick = {
                            if (options[i].second) {
                                onCorrectAnswerClick(options[i].first[0])
                                showFeedback.value = false
                            } else {
                                showFeedback.value = true
                            }
                        },
                        modifier = buttonModifier,
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = colorResource(
                                R.color.white
                            )
                        ),
                        shape = buttonShape
                    ) {
                        Text(
                            buildAnnotatedString {
                                append(options[i].first[0].toString())
                                for (j in 1..<options[i].first.size) {
                                    withStyle(
                                        style = SpanStyle(
                                            fontWeight = FontWeight.Normal,
                                        )
                                    ) {
                                        append("/${options[i].first[j]}")
                                    }
                                }
                            },
                            color = if (options[i].second) correctButtonTextColor else wrongButtonTextColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            fontFamily = sarabunFontFamily
                        )
                    }
                }
            }
        }
    }
}