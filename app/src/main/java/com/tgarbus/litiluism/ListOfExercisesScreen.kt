package com.tgarbus.litiluism

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun ListOfExercisesScreen(exercises: List<Exercise>, navController: NavController) {
    val sarabunFontFamily = FontFamily(
        Font(R.font.sarabun_regular, FontWeight.Normal),
        Font(R.font.sarabun_bold, FontWeight.Bold),
        Font(R.font.sarabun_thin, FontWeight.Thin),
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Transliteration exercises",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxWidth(),
            fontFamily = sarabunFontFamily,
            fontSize = 20.sp,
            fontWeight = FontWeight(700),
        )
        for (exercise in exercises) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 1.dp,
                        shape = RoundedCornerShape(size = 21.dp)
                    )
                    .background(
                        color = colorResource(id = R.color.white),
                        shape = RoundedCornerShape(size = 21.dp)
                    )
                    .clickable {
                        navController.navigate("exercise/${exercise.id}")
                    }
                    .padding(vertical = 10.dp, horizontal = 10.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(5f)
                    ) {
                        Text(
                            text = exercise.title,
                            fontFamily = sarabunFontFamily,
                            fontWeight = FontWeight(700),
                            fontSize = 20.sp
                        )
                        Text(
                            text = "Younger Futhark",
                            fontFamily = sarabunFontFamily,
                            fontWeight = FontWeight(400),
                            fontSize = 16.sp
                        )
                        Text(
                            text = exercise.runes,
                            fontFamily = sarabunFontFamily,
                            fontWeight = FontWeight(300),
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Image(
                        painter = painterResource(id = exercise.imgResource),
                        contentDescription = "Image goes brr",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(AbsoluteRoundedCornerShape(21.dp))
                            .weight(2f)
                    )
                }
            }
        }
    }
}