package com.tgarbus.litiluism.ui.reusables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily

@Composable
fun ExerciseHeaderFrame(
    exerciseType: String,
    title: String,
    navController: NavController) {
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
                .clickable { navController.popBackStack() }
        )
    }
    Text(
        text = exerciseType,
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 16.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.ExtraBold,
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
        fontWeight = FontWeight.ExtraBold,
    )
}