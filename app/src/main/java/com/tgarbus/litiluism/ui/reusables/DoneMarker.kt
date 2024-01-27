package com.tgarbus.litiluism.ui.reusables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.ui.Fonts

@Composable
fun DoneMarker() {
    Box(
        Modifier
            .border(
                width = 1.dp,
                color = colorResource(R.color.primary),
                shape = RoundedCornerShape(size = 20.dp)
            )
            .padding(start = 10.dp, end = 10.dp)
    ) {
        Text(
            text = "done",
            fontFamily = Fonts.sarabunFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.primary)
        )
    }
}