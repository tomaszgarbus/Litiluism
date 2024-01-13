package com.tgarbus.litiluism.ui

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.tgarbus.litiluism.R

class Fonts {
    companion object {
        val sarabunFontFamily = FontFamily(
            Font(R.font.sarabun_regular, FontWeight.Normal),
            Font(R.font.sarabun_bold, FontWeight.Bold),
            Font(R.font.sarabun_thin, FontWeight.Thin),
            Font(R.font.sarabun_italic, FontWeight.Normal, FontStyle.Italic),
            Font(R.font.sarabun_bolditalic, FontWeight.Bold, FontStyle.Italic)
        )
    }
}