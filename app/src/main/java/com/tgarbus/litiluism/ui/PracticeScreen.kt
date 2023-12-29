package com.tgarbus.litiluism.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tgarbus.litiluism.R

@Composable
fun PracticeTypeButton(
    name: String,
    navController: NavController,
    bgResource: Int,
    boxModifier: Modifier = Modifier
) {
    val sarabunFontFamily = FontFamily(
        Font(R.font.sarabun_regular, FontWeight.Normal),
        Font(R.font.sarabun_bold, FontWeight.Bold),
        Font(R.font.sarabun_thin, FontWeight.Thin),
    )
    Box(
        modifier = boxModifier
            .shadow(
                elevation = 1.dp, shape = RoundedCornerShape(size = 21.dp)
            )
            .fillMaxSize()
            .clip(RoundedCornerShape(size = 21.dp))
            .clickable { navController.navigate("exerciseslist") }
    ) {
        Image(
            painter = painterResource(bgResource),
            name,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        // TODO: Make it a custom component
        Box(
            modifier = Modifier
                .padding(10.dp)
                .background(
                    color = colorResource(R.color.primary),
                    shape = RoundedCornerShape(27.dp)
                )
                .padding(start = 10.dp, end = 10.dp, bottom = 5.dp, top = 5.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name.uppercase(),
                    fontFamily = sarabunFontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight(600),
                    color = colorResource(R.color.white),
                )
                Image(
                    painter = painterResource(R.drawable.icon_forward),
                    name,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}

@Composable
fun PracticeScreen(navController: NavController) {
    // TODO: Server font family from one static place in the app
    val sarabunFontFamily = FontFamily(
        Font(R.font.sarabun_regular, FontWeight.Normal),
        Font(R.font.sarabun_bold, FontWeight.Bold),
        Font(R.font.sarabun_thin, FontWeight.Thin),
    )
    val scrollState = rememberScrollState()
    // TODO: Extract common column to a custom composable
    Column(
        modifier = Modifier
            .fillMaxWidth()
            // TODO: set bg color through theme
            .background(colorResource(R.color.light_bg))
            .verticalScroll(scrollState)
            .padding(bottom = 10.dp)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // TODO: Deduplicate into a "Header" composable.
        Text(
            text = "Practice",
            textAlign = TextAlign.Justify,
            color = colorResource(R.color.secondary),
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxWidth(),
            fontFamily = sarabunFontFamily,
            fontSize = 32.sp,
            fontWeight = FontWeight(700),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            PracticeTypeButton(
                "Rune to latin",
                navController,
                R.drawable.button_bg_rune_to_latin,
                Modifier.weight(1f)
            )
            PracticeTypeButton(
                "Latin to rune",
                navController,
                R.drawable.button_bg_latin_to_rune,
                Modifier.weight(1f)
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            PracticeTypeButton(
                "Text transliteration",
                navController,
                R.drawable.button_bg_transliteration_exercises
            )
        }
    }
}