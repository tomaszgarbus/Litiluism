package com.tgarbus.litiluism.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import com.tgarbus.litiluism.ui.reusables.BalloonsQueue
import com.tgarbus.litiluism.ui.reusables.ButtonType
import com.tgarbus.litiluism.ui.reusables.SemiCircularProgressBar
import com.tgarbus.litiluism.ui.reusables.Dock
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.Header
import com.tgarbus.litiluism.ui.reusables.IntroTooltip
import com.tgarbus.litiluism.ui.reusables.PracticeTypeButton
import com.tgarbus.litiluism.ui.reusables.PracticeTypeButtonText
import com.tgarbus.litiluism.ui.reusables.RuneRowList
import com.tgarbus.litiluism.ui.reusables.StatisticsLineChart

@Composable
fun GreenBanner(navController: NavController) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Image(
            painterResource(R.drawable.banner_home),
            "banner",
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            Text(
                "Hej! How about taking a peek into Historiska Museet in Stockholm?",
                fontFamily = sarabunFontFamily,
                color = colorResource(R.color.primary),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
            )
            PracticeTypeButtonText(
                "Take me there!",
                modifier = Modifier.clickable { navController.navigate("mapscreen/historiska") })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val balloonsQueue = BalloonsQueue()
    FullScreenPaddedColumn {
        Header("Litiluism")
//        GreenBanner(navController)
        IntroTooltip(
            text = "Apply your knowledge here.",
            queue = balloonsQueue
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                PracticeTypeButton(
                    "Practice",
                    R.drawable.button_bg_practice,
                    onClick = { navController.navigate("practice") }
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            IntroTooltip(
                text = "Learn more here.",
                queue = balloonsQueue,
                Modifier.weight(1f),
            ) {
                PracticeTypeButton(
                    "Learn",
                    R.drawable.button_bg_lessons,
                    onClick = { navController.navigate("learning") }
                )
            }
            IntroTooltip(
                text = "Check out the recommended materials section.",
                queue = balloonsQueue,
                Modifier.weight(1f),
            ) {
                PracticeTypeButton(
                    "Materials",
                    R.drawable.button_bg_materials,
                    onClick = { navController.navigate("materials") }
                )
            }
        }
    }
    Dock(ButtonType.HOME, navController)
}