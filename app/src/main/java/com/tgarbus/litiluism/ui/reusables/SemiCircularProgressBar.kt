package com.tgarbus.litiluism.ui.reusables

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import kotlin.math.min

@Composable
fun SemiCircularProgressBar(
    progressPercentage: Int,
    canvasModifier: Modifier = Modifier
) {
    val bgColor = colorResource(R.color.dim_grey_text)
    val primaryColor = colorResource(R.color.primary)
    val textMeasurer = rememberTextMeasurer()
    Canvas(
        modifier = canvasModifier
    ) {
        Log.d("size", size.toString())
        val radius = min(size.width / 2, size.height)
        // size.width - 2 * (size.width / 2 - radius) = 2 * radius
        // size.height - 2 * (size.height / 2 - radius / 2) = radius
        inset(size.width / 2 - radius, size.height / 2 - radius / 2) {
            drawArc(
                startAngle = 180f, // 270 is 0 degree
                sweepAngle = 180f,
                useCenter = false,
                color = bgColor,
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = 30f, cap = StrokeCap.Round)
            )
            drawArc(
                startAngle = 180f, // 270 is 0 degree
                sweepAngle = progressPercentage * 1.8f,
                useCenter = false,
                color = primaryColor,
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = 30f, cap = StrokeCap.Round)
            )
            val text = "${progressPercentage}%"
            val textStyle = TextStyle(
                fontFamily = sarabunFontFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            val measurement = textMeasurer.measure(text, textStyle)
            val topLeft = Offset(
                (size.width - measurement.size.width) / 2,
                drawContext.size.height - measurement.size.height,
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