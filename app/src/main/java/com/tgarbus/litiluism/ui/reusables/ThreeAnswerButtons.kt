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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tgarbus.litiluism.R

@Composable
fun ThreeAnswerButtons(
    options: List<Pair<Char, Boolean>>,
    showFeedback: Boolean,
    onAnswerClick: (Char) -> Unit
) {
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
                    ElevatedButton(
                        onClick = { onAnswerClick(options[i].first) },
                        modifier = buttonModifier,
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = colorResource(
                                R.color.white
                            )
                        ),
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