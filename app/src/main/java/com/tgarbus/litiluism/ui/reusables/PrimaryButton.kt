package com.tgarbus.litiluism.ui.reusables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tgarbus.litiluism.R

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        val sarabunFontFamily = FontFamily(
            Font(R.font.sarabun_regular, FontWeight.Normal),
            Font(R.font.sarabun_bold, FontWeight.Bold),
            Font(R.font.sarabun_thin, FontWeight.Thin),
        )

        Text(
            text = text,
            fontSize = 24.sp,
            lineHeight = 28.08.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight(600),
            color = colorResource(R.color.white),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            painterResource(R.drawable.icon_forward),
            "Complete",
            modifier = Modifier.size(20.dp),
        )
    }
}