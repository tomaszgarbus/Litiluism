package com.tgarbus.litiluism.ui.reusables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import kotlin.math.min

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier
) {
    val progress = 45f
    val bgColor = colorResource(R.color.dim_grey_text)
    val primaryColor = colorResource(R.color.primary)
    val textMeasurer = rememberTextMeasurer()
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        val radius = min(size.width / 2, size.height)
        inset(size.width / 2 - radius, size.height / 2 - radius) {
            drawArc(
                startAngle = 180f, // 270 is 0 degree
                sweepAngle = 180f,
                useCenter = false,
                color = bgColor,
                style = Stroke(width = 30f, cap = StrokeCap.Round)
            )
            drawArc(
                startAngle = 180f, // 270 is 0 degree
                sweepAngle = progress,
                useCenter = false,
                color = primaryColor,
                style = Stroke(width = 30f, cap = StrokeCap.Round)
            )
            val text = "20%"
            val textStyle = TextStyle(
                fontFamily = sarabunFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            val measurement = textMeasurer.measure(text, textStyle)
            val topLeft = Offset(
                (size.width - measurement.size.width) / 2,
                (size.height - measurement.size.height) / 4,
            )
            drawText(
                textMeasurer = textMeasurer,
                style = textStyle,
                topLeft = topLeft,
                text = text
            )
        }
    }
}