package com.tgarbus.litiluism.ui.reusables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily

@Composable
fun InputFeedbackText(text: String, showFeedback: Boolean) {
    val feedbackAlpha: Float by animateFloatAsState(
        if (showFeedback) 1f else 0f,
        label = "animateFeedback"
    )
    Text(
        text = text,
        fontFamily = sarabunFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp,
        color = colorResource(R.color.wrong_red),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .alpha(feedbackAlpha)
    )
}