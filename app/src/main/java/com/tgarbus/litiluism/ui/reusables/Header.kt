package com.tgarbus.litiluism.ui.reusables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.tgarbus.litiluism.R

@Composable
fun Header(text: String, modifier: Modifier = Modifier) {
    val sarabunFontFamily = FontFamily(
        Font(R.font.sarabun_regular, FontWeight.Normal),
        Font(R.font.sarabun_bold, FontWeight.Bold),
        Font(R.font.sarabun_thin, FontWeight.Thin),
    )
    Text(
        text = text,
        textAlign = TextAlign.Justify,
        color = colorResource(R.color.secondary),
        modifier = modifier
            .fillMaxWidth(),
        fontFamily = sarabunFontFamily,
        fontSize = 32.sp,
        fontWeight = FontWeight(700),
        lineHeight = 37.sp,
    )
}