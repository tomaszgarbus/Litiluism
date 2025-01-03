package com.tgarbus.litiluism.ui.reusables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
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
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = exerciseType,
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = sarabunFontFamily,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF9C9C9C),
            )
        )
//        Spacer(modifier = Modifier.weight(1f))
        closeButton(
            description = LocalContext.current.getString(
                R.string.content_description_close_exercise
            ),
            modifier = Modifier.align(Alignment.CenterEnd)
        ) { navController.popBackStack() }
    }
    Text(
        text = title,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth(),
        fontFamily = sarabunFontFamily,
        fontSize = 32.sp,
        lineHeight = 37.sp,
        fontWeight = FontWeight.ExtraBold,
        color = colorResource(R.color.dark_grey),
    )
}